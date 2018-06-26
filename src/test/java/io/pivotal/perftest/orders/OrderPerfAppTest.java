package io.pivotal.perftest.orders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.apache.geode.cache.Region;
import org.junit.Test;

import io.pivotal.perftest.orders.benchmark.OrderBenchMark;
import io.pivotal.perftest.orders.model.OrderStatView;
import io.pivotal.perftest.orders.model.PerfCriteria;

public class OrderPerfAppTest
{

	@SuppressWarnings("unchecked")
	@Test
	public void testRunTest()
	{
		OrderLongRunningPerfApp app = mockApp();
		
		assertTrue(!app.isWarm());
		
		assertNull(app.exeTest(null));
		
		PerfCriteria criteria = new PerfCriteria();
		criteria.setOrderCount(10);
		
		OrderStatView view = app.runTest(criteria);
		
		assertTrue(app.isWarm());
		
		view.setExecutionPut90_00(-1);
		view.setExecutionPut99_00(-1);
		view.setExecutionPut99_99(-1);
		view.setExecutionPutAvg(-1);
		view.setExecutionPutMin(-1);
		view.setExecutionPutMax(-1);
		
		  view.setQfMessagePut90_00(-1);
		  view.setQfMessagePut99_00(-1);
		  view.setQfMessagePut99_99(-1);
		  view.setQfMessagePutAvg(-1);
		  view.setQfMessagePutMin(-1);
		  view.setQfMessagePutMax(-1);
		  
		  view.setOrderDetailPut90_00(-1);
		  view.setOrderDetailPut99_00(-1);
		  view.setOrderDetailPut99_99(-1);
		  view.setOrderDetailPutAvg(-1);
		  view.setOrderDetailPutMin(-1);
		  view.setOrderDetailPutMax(-1);


		  view.setOutMsgPut90_00(-1);
		  view.setOutMsgPut99_00(-1);
		  view.setOutMsgPut99_99(-1);
		  view.setOutMsgPutAvg(-1);
		  view.setOutMsgPutMin(-1);
		  view.setOutMsgPutMax(-1);
		  
		  
		  view.setInMsgPut90_00(-1);
		  view.setInMsgPut99_00(-1);
		  view.setInMsgPut99_99(-1);
		  view.setInMsgPutAvg(-1);
		  view.setInMsgPutMin(-1);
		  view.setInMsgPutMax(-1);
		
		
		view = app.runTest(criteria);

		assertEquals(view.getInMsgPutAvg(), 0,0);
		assertEquals(view.getOrderDetailPutAvg(), 0,0);
		assertEquals(view.getExecutionPutAvg(), 0,0);
		assertEquals(view.getOutMsgPutAvg(), 0,0);
		assertEquals(view.getQfMessagePutAvg(), 0,0);
		assertEquals(view.getOrderDetailGetAvg(), 0,0);
		
		verifyStats(view);
		

		
	}//------------------------------------------------
	
	/**
	 * Test whether the application is warm or not
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testWarm()
	{
		OrderLongRunningPerfApp app = mockApp();
		
		assertTrue(!app.isWarm());
		
		app.warm();
		
		assertTrue(app.isWarm());
		
	}//------------------------------------------------
	private OrderLongRunningPerfApp mockApp()
	{
		OrderLongRunningPerfApp app = new OrderLongRunningPerfApp();
		OrderBenchMark orderBenchMark = mock(OrderBenchMark.class);
		app.orderRegion = mock(Region.class);
		app.inMsgRegion = mock(Region.class);
		app.outMsgRegion = mock(Region.class);
		app.executionRegion = mock(Region.class);
		app.qfMessageRegion = mock(Region.class);
		app.benchMark = orderBenchMark;
		return app;
	}
	private void verifyStats(OrderStatView view)
	{
		assertNotNull(view);
	    assertTrue(view.getInMsgPutAvg() >= view.getInMsgPutMin());
		assertTrue(view.getInMsgPutAvg() <= view.getInMsgPutMax());
		assertTrue(view.getExecutionPutAvg() >= view.getExecutionPutMin());
	    assertTrue(view.getOrderDetailGetAvg() >= view.getOrderDetailGetMin());
		assertTrue(view.getOrderDetailGetAvg() <= view.getOrderDetailGetMax());
	    assertTrue(view.getOutMsgPutAvg() >= view.getOutMsgPutMin());
	    
	    assertTrue(view.getQfMessagePutMin() <= view.getQfMessagePutMax());
	    assertTrue(view.getQfMessagePutAvg() >= view.getQfMessagePutMin());
	    assertTrue(view.getQfMessagePutAvg() <= view.getExecutionPut90_00());
	    assertTrue(view.getQfMessagePutAvg() <= view.getExecutionPut99_00());	    
	    assertTrue(view.getQfMessagePut99_00() <= view.getExecutionPut99_90());
	    assertTrue(view.getQfMessagePut99_90() <= view.getExecutionPut99_99());
	    assertTrue(view.getQfMessagePutAvg() <= view.getExecutionPut99_90());
	    
	    assertTrue(view.getQfMessagePut99_00() >= 0);
	    assertTrue(view.getQfMessagePut99_90() >= 0);
	    assertTrue(view.getQfMessagePut99_90() >= 0);
	    assertTrue(view.getQfMessagePutAvg() >= 0);
	    
	}
	

}
