package io.pivotal.perftest.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.geode.cache.Region;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.pivotal.perfest.throttle.RateThrottler;
import io.pivotal.perftest.orders.benchmark.OrderBenchMark;
import io.pivotal.perftest.orders.creational.ExecutionCreator;
import io.pivotal.perftest.orders.creational.InMsgCreator;
import io.pivotal.perftest.orders.creational.OrderCreator;
import io.pivotal.perftest.orders.creational.OutMsgCreator;
import io.pivotal.perftest.orders.creational.QFMessageCreator;
import io.pivotal.perftest.orders.domain.Execution;
import io.pivotal.perftest.orders.domain.InMsg;
import io.pivotal.perftest.orders.domain.Order;
import io.pivotal.perftest.orders.domain.OutMsg;
import io.pivotal.perftest.orders.domain.QFMessage;
import io.pivotal.perftest.orders.exception.PerformanceExeption;
import io.pivotal.perftest.orders.model.OrderStatView;
import io.pivotal.perftest.orders.model.PerfCriteria;
import nyla.solutions.core.util.Config;

@Controller
@SpringBootApplication
public class OrderLongRunningPerfApp implements ApplicationContextAware {

	private static final int MAX_WINDOW_SIZE = Config.getPropertyInteger("MAX_WINDOW_SIZE", 800 * 2).intValue();
	/**
	 * WARM_CNT = Config.getPropertyInteger("WARMUP_CNT",400)
	 */
	private static final int WARM_CNT = Config.getPropertyInteger("WARMUP_CNT", 400);
	private static Random r = new Random();
	private static final String NODE_ID = Config.getProperty("NODE_ID", Integer.toString(r.nextInt(60000)));
	
	/* this drives the number of minutes that this job will run after the warmup period */
	private static final int RUN_MINUTES = Config.getPropertyInteger("RUN_MINUTES", 1);
	
	/* this throttles the number of message/sec that the client will issue */
	private static final int MESSAGES_SEC = Config.getPropertyInteger("MESSAGES_SEC", 200);
	
	private boolean warm = false;

	private static long secondsInMilli = 1000;
	private static long minutesInMilli = secondsInMilli * 60;
	private static long hoursInMilli = minutesInMilli * 60;

	private static final int runTimeHoursInMillis = ((int) (RUN_MINUTES * minutesInMilli));
//	private static final int runTimeHoursInMillis = ((int) (minutesInMilli));

	@Autowired
	OrderBenchMark benchMark;

	@Resource
	Region<String, InMsg> inMsgRegion;

	@Resource
	Region<String, OutMsg> outMsgRegion;

	@Autowired
	@Qualifier("orderRegion")
	Region<String, Order> orderRegion;

	@Resource
	Region<String, Execution> executionRegion;

	@Resource
	Region<String, QFMessage> qfMessageRegion;

	@RequestMapping("/run")
	@ResponseBody
	public OrderStatView runTest(@RequestBody PerfCriteria criteria) {

		if (!this.isWarm())
			warm();

		if (criteria == null)
			criteria = new PerfCriteria();

		//  repurpose the order count to be orders/ sec
		criteria.setOrderCount(MESSAGES_SEC);

		OrderStatView view = exeTest(criteria);
		System.out.println("ORDER COUNT:" + criteria.getOrderCount() + " RESULTS:" + view);

		return view;
	}// ------------------------------------------------

	public OrderStatView exeTest(@RequestBody PerfCriteria criteria) {

		if (criteria == null) {
			System.out.println("Returning null");
			return null;
		}
		
		List<OrderStatView> orderStats = new ArrayList<>();

		/* set up for multi-threading */
		Callable<OrderStatView> exeTestThread = () -> {
			return exeTestThread(criteria);
		};
		ExecutorService executor = Executors.newFixedThreadPool(3);
		Future<OrderStatView> futureStats1 = executor.submit(exeTestThread);

		try {
			orderStats.add(futureStats1.get());
		} catch (InterruptedException | ExecutionException e) {
			throw new PerformanceExeption(e);
		}
		
		executor.shutdown();

		for (OrderStatView orderStat : orderStats) {
			System.out.println(orderStat);
		}
		return orderStats.get(0);

	}// ------------------------------------------------

	public OrderStatView exeTestThread(PerfCriteria criteria) {
		try {
			int orderCntSec = criteria.getOrderCount();
			if (orderCntSec <= 0)
				throw new IllegalArgumentException("orderCntSec:" + orderCntSec + "  must be greater than zero");
			long twentyPercent = Math.round(0.2 * orderCntSec);

			InMsgCreator inMsgCreator = new InMsgCreator(NODE_ID);
			OrderCreator orderCreator = new OrderCreator(NODE_ID);
			OutMsgCreator outMsgCreator = new OutMsgCreator(NODE_ID);
			QFMessageCreator qfMessageCreator = new QFMessageCreator(NODE_ID);
			ExecutionCreator executionCreator = new ExecutionCreator(NODE_ID);

			InMsg im = null;
			Order o = null;
			OutMsg om = null;
			QFMessage qfMessage = null;
			Execution ex = null;
			im = inMsgCreator.create();
			o = orderCreator.create();
			om = outMsgCreator.create();

			OrderStatView view = new OrderStatView();

			DescriptiveStatistics inMsgPutStats = new DescriptiveStatistics(MAX_WINDOW_SIZE);

			DescriptiveStatistics orderDetailPutStats = new DescriptiveStatistics(MAX_WINDOW_SIZE);
			DescriptiveStatistics orderDetailGetStats = new DescriptiveStatistics(MAX_WINDOW_SIZE);

			DescriptiveStatistics outMsgPutStats = new DescriptiveStatistics(MAX_WINDOW_SIZE);
			DescriptiveStatistics executionPutStats = new DescriptiveStatistics(MAX_WINDOW_SIZE);
			DescriptiveStatistics qfMessagePutStats = new DescriptiveStatistics(MAX_WINDOW_SIZE);

			long timeMs;

			/*  generate a few messages to coerce a GC. We see large timings at the beginning of a run */
			this.benchMark.constructPutMeasurement(this.inMsgRegion, im, im.getMsgId());
			this.benchMark.constructPutMeasurement(this.orderRegion, o, o.getOrderId());
			this.benchMark.constructPutMeasurement(this.outMsgRegion, om, om.getMsgId());
			qfMessage = qfMessageCreator.create();
			this.benchMark.constructPutMeasurement(this.qfMessageRegion, qfMessage,
					qfMessage.getMsgId());

			try {
				System.gc();   // pause in case a client GC was issued
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}


			/* assume 1 thread at MESSAGES_SEC msgs/sec */
			RateThrottler rateThrottler = new RateThrottler(1, orderCntSec);
			long endTimeInMillis = System.currentTimeMillis() + runTimeHoursInMillis;
			int orderCntPerIteration = rateThrottler.ordersPerSecPerThreadPerIteration();
			int orderCtr = 0;
			long hourStopWatch = System.currentTimeMillis() + hoursInMilli;

			// run until time exceeded
			while (System.currentTimeMillis() < endTimeInMillis) {

				for (int i = 0; i < orderCntPerIteration; i++) {

					timeMs = this.benchMark.constructPutMeasurement(this.inMsgRegion, im, im.getMsgId());
					inMsgPutStats.addValue(timeMs);

					qfMessage = qfMessageCreator.create();
					timeMs = this.benchMark.constructPutMeasurement(this.qfMessageRegion, qfMessage,
							qfMessage.getMsgId());
					qfMessagePutStats.addValue(timeMs);

					timeMs = this.benchMark.constructPutMeasurement(this.orderRegion, o, o.getOrderId());
					orderDetailPutStats.addValue(timeMs);

					timeMs = this.benchMark.constructPutMeasurement(this.outMsgRegion, om, om.getMsgId());
					outMsgPutStats.addValue(timeMs);

					qfMessage = qfMessageCreator.create();
					timeMs = this.benchMark.constructPutMeasurement(this.qfMessageRegion, qfMessage,
							qfMessage.getMsgId());
					qfMessagePutStats.addValue(timeMs);

					// an in message insert
					im = inMsgCreator.create();
					timeMs = this.benchMark.constructPutMeasurement(this.inMsgRegion, im, im.getMsgId());
					inMsgPutStats.addValue(timeMs);

					// insert qfMessage
					qfMessage = qfMessageCreator.create();
					timeMs = this.benchMark.constructPutMeasurement(this.qfMessageRegion, qfMessage,
							qfMessage.getMsgId());
					qfMessagePutStats.addValue(timeMs);

					// order region get
					o = orderCreator.create();
					timeMs = this.benchMark.constructGetMeasurement(this.orderRegion, o, o.getOrderId());
					orderDetailGetStats.addValue(timeMs);

					// order region put
					timeMs = this.benchMark.constructPutMeasurement(this.orderRegion, o, o.getOrderId());
					orderDetailPutStats.addValue(timeMs);

					// an execution insert
					ex = executionCreator.create();
					timeMs = this.benchMark.constructPutMeasurement(this.executionRegion, ex,
							String.valueOf(ex.hashCode()));
					executionPutStats.addValue(timeMs);

					// insert an execution 20% of the time
					if (i % 5 < 1) {
						ex = executionCreator.create();
						timeMs = this.benchMark.constructPutMeasurement(this.executionRegion, ex,
								String.valueOf(ex.hashCode()));
						executionPutStats.addValue(timeMs);
					}

					// out MSG insert
					om = outMsgCreator.create();
					timeMs = this.benchMark.constructPutMeasurement(this.outMsgRegion, om, om.getMsgId());
					outMsgPutStats.addValue(timeMs);

					// insert qfMessage
					qfMessage = qfMessageCreator.create();
					timeMs = this.benchMark.constructPutMeasurement(this.qfMessageRegion, qfMessage,
							qfMessage.getMsgId());
					qfMessagePutStats.addValue(timeMs);

					orderCtr++;
				}

				// wait a bit if we're writing faster than the expected rate
				// System.out.println("Calling throttler with " + orderCtr);
				rateThrottler.throttle(orderCtr);
				
				if (System.currentTimeMillis() > hourStopWatch) {
					
					System.out.println("SUBTOTALS orders=" + orderCtr * 2 + 
							", inMsgs=" + orderCtr * 2 + 
							", outMsgs=" + orderCtr * 2 + 
							", qfMsgs=" + orderCtr * 4 + 
							", executions=" + orderCtr / 2 * 1.2 );

					view.setInMsgPutAvg(inMsgPutStats.getMean());
					view.setInMsgPutMax(inMsgPutStats.getMax());
					view.setInMsgPutMin(inMsgPutStats.getMin());
					view.setInMsgPut90_00(inMsgPutStats.getPercentile(90.00));
					view.setInMsgPut99_00(inMsgPutStats.getPercentile(99.00));
					view.setInMsgPut99_90(inMsgPutStats.getPercentile(99.90));
					view.setInMsgPut99_99(inMsgPutStats.getPercentile(99.99));

					view.setOrderDetailPutAvg(orderDetailPutStats.getMean());
					view.setOrderDetailPutMax(orderDetailPutStats.getMax());
					view.setOrderDetailPutMin(orderDetailPutStats.getMin());
					view.setOrderDetailPut90_00(orderDetailPutStats.getPercentile(90.00));
					view.setOrderDetailPut99_00(orderDetailPutStats.getPercentile(99.00));
					view.setOrderDetailPut99_90(orderDetailPutStats.getPercentile(99.90));
					view.setOrderDetailPut99_99(orderDetailPutStats.getPercentile(99.99));

					view.setOrderDetailGetAvg(orderDetailGetStats.getMean());
					view.setOrderDetailGetMax(orderDetailGetStats.getMax());
					view.setOrderDetailGetMin(orderDetailGetStats.getMin());
					view.setOrderDetailGet90_00(orderDetailGetStats.getPercentile(90.00));
					view.setOrderDetailGet99_00(orderDetailGetStats.getPercentile(99.00));
					view.setOrderDetailGet99_90(orderDetailGetStats.getPercentile(99.90));
					view.setOrderDetailGet99_99(orderDetailGetStats.getPercentile(99.99));

					view.setExecutionPutAvg(executionPutStats.getMean());
					view.setExecutionPutMax(executionPutStats.getMax());
					view.setExecutionPutMin(executionPutStats.getMin());
					view.setExecutionPut90_00(executionPutStats.getPercentile(90.00));
					view.setExecutionPut99_00(executionPutStats.getPercentile(99.00));
					view.setExecutionPut99_90(executionPutStats.getPercentile(99.90));
					view.setExecutionPut99_99(executionPutStats.getPercentile(99.99));

					view.setOutMsgPutAvg(outMsgPutStats.getMean());
					view.setOutMsgPutMax(outMsgPutStats.getMax());
					view.setOutMsgPutMin(outMsgPutStats.getMin());
					view.setOutMsgPut90_00(outMsgPutStats.getPercentile(90.00));
					view.setOutMsgPut99_00(outMsgPutStats.getPercentile(99.00));
					view.setOutMsgPut99_90(outMsgPutStats.getPercentile(99.90));
					view.setOutMsgPut99_99(outMsgPutStats.getPercentile(99.99));

					view.setQfMessagePutAvg(outMsgPutStats.getMean());
					view.setQfMessagePutMax(outMsgPutStats.getMax());
					view.setQfMessagePutMin(outMsgPutStats.getMin());
					view.setQfMessagePut90_00(outMsgPutStats.getPercentile(90.00));
					view.setQfMessagePut99_00(outMsgPutStats.getPercentile(99.00));
					view.setQfMessagePut99_90(outMsgPutStats.getPercentile(99.90));
					view.setQfMessagePut99_99(outMsgPutStats.getPercentile(99.99));

					System.out.println(view.toString());
					hourStopWatch += hoursInMilli;
				}
			}

			System.out.println("TOTALS orders=" + orderCtr * 2 + 
					", inMsgs=" + orderCtr * 2 + 
					", outMsgs=" + orderCtr * 2 + 
					", qfMsgs=" + orderCtr * 4 + 
					", executions=" + orderCtr / 2 * 1.2 );

			view.setInMsgPutAvg(inMsgPutStats.getMean());
			view.setInMsgPutMax(inMsgPutStats.getMax());
			view.setInMsgPutMin(inMsgPutStats.getMin());
			view.setInMsgPut90_00(inMsgPutStats.getPercentile(90.00));
			view.setInMsgPut99_00(inMsgPutStats.getPercentile(99.00));
			view.setInMsgPut99_90(inMsgPutStats.getPercentile(99.90));
			view.setInMsgPut99_99(inMsgPutStats.getPercentile(99.99));

			view.setOrderDetailPutAvg(orderDetailPutStats.getMean());
			view.setOrderDetailPutMax(orderDetailPutStats.getMax());
			view.setOrderDetailPutMin(orderDetailPutStats.getMin());
			view.setOrderDetailPut90_00(orderDetailPutStats.getPercentile(90.00));
			view.setOrderDetailPut99_00(orderDetailPutStats.getPercentile(99.00));
			view.setOrderDetailPut99_90(orderDetailPutStats.getPercentile(99.90));
			view.setOrderDetailPut99_99(orderDetailPutStats.getPercentile(99.99));

			view.setOrderDetailGetAvg(orderDetailGetStats.getMean());
			view.setOrderDetailGetMax(orderDetailGetStats.getMax());
			view.setOrderDetailGetMin(orderDetailGetStats.getMin());
			view.setOrderDetailGet90_00(orderDetailGetStats.getPercentile(90.00));
			view.setOrderDetailGet99_00(orderDetailGetStats.getPercentile(99.00));
			view.setOrderDetailGet99_90(orderDetailGetStats.getPercentile(99.90));
			view.setOrderDetailGet99_99(orderDetailGetStats.getPercentile(99.99));

			view.setExecutionPutAvg(executionPutStats.getMean());
			view.setExecutionPutMax(executionPutStats.getMax());
			view.setExecutionPutMin(executionPutStats.getMin());
			view.setExecutionPut90_00(executionPutStats.getPercentile(90.00));
			view.setExecutionPut99_00(executionPutStats.getPercentile(99.00));
			view.setExecutionPut99_90(executionPutStats.getPercentile(99.90));
			view.setExecutionPut99_99(executionPutStats.getPercentile(99.99));

			view.setOutMsgPutAvg(outMsgPutStats.getMean());
			view.setOutMsgPutMax(outMsgPutStats.getMax());
			view.setOutMsgPutMin(outMsgPutStats.getMin());
			view.setOutMsgPut90_00(outMsgPutStats.getPercentile(90.00));
			view.setOutMsgPut99_00(outMsgPutStats.getPercentile(99.00));
			view.setOutMsgPut99_90(outMsgPutStats.getPercentile(99.90));
			view.setOutMsgPut99_99(outMsgPutStats.getPercentile(99.99));

			view.setQfMessagePutAvg(outMsgPutStats.getMean());
			view.setQfMessagePutMax(outMsgPutStats.getMax());
			view.setQfMessagePutMin(outMsgPutStats.getMin());
			view.setQfMessagePut90_00(outMsgPutStats.getPercentile(90.00));
			view.setQfMessagePut99_00(outMsgPutStats.getPercentile(99.00));
			view.setQfMessagePut99_90(outMsgPutStats.getPercentile(99.90));
			view.setQfMessagePut99_99(outMsgPutStats.getPercentile(99.99));

			return view;
		} catch (Exception e) {
			e.printStackTrace();

			throw new PerformanceExeption(e);
		}
	}

	public synchronized void warm() {

		System.out.println("STARTING WARMUP for JVM with counts:" + WARM_CNT);
		OrderCreator creator = new OrderCreator(NODE_ID);
		QFMessageCreator qfMessageCreator = new QFMessageCreator(NODE_ID);
		Order o = null;
		QFMessage qfMessage = null;
		InMsgCreator inMsgCreator = new InMsgCreator(NODE_ID);
		InMsg inMsg = null;

		OutMsgCreator outMsgCreator = new OutMsgCreator(NODE_ID);
		OutMsg outMsg = null;

		Execution execution = null;
		ExecutionCreator executionCreator = new ExecutionCreator(NODE_ID);

		for (int i = 0; i < WARM_CNT; i++) {
			o = creator.create();

			this.orderRegion.put(o.getOrderId(), o);
			this.orderRegion.get(o.getOrderId());

			inMsg = inMsgCreator.create();
			this.inMsgRegion.put(inMsg.getMsgId(), inMsg);

			execution = executionCreator.create();
			this.executionRegion.put(String.valueOf(execution.hashCode()), execution);

			outMsg = outMsgCreator.create();
			this.outMsgRegion.put(outMsg.getMsgId(), outMsg);

			qfMessage = qfMessageCreator.create();
			this.qfMessageRegion.put(qfMessage.getMsgId(), qfMessage);

			if (i % 100 == 0)
				System.out.println("warm count:+" + i);
		}

		this.warm = true;

		try {
			System.gc();   // ?? discuss this more
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("END WARMUP :" + warm);

	}

	public synchronized boolean isWarm() {
		return warm;
	}// ------------------------------------------------

	public static void main(String[] args) {
		SpringApplication.run(OrderLongRunningPerfApp.class, args);
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		System.out.println("I am in application context");
		// if (!this.isWarm())
		// this.warm();
		
		/* run in separate thread so that PCF won't do health checks and potentially stop the job */
		Runnable runner = () -> {
			runTest(null);
		};
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(runner);
		executor.shutdown();
		
		System.out.println("End  in application context");

	}
}
