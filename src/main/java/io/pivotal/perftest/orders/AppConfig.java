package io.pivotal.perftest.orders;
import org.apache.geode.cache.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gedi.solutions.geode.client.GeodeClient;
import io.pivotal.perftest.orders.benchmark.OrderBenchMark;
import io.pivotal.perftest.orders.domain.Execution;
import io.pivotal.perftest.orders.domain.InMsg;
import io.pivotal.perftest.orders.domain.Order;
import io.pivotal.perftest.orders.domain.OutMsg;
import io.pivotal.perftest.orders.domain.QFMessage;

@Configuration
public class AppConfig
{
	@Bean
	public OrderBenchMark orderBenchMark()
	{
		return new OrderBenchMark();
	}//------------------------------------------------
	@Bean
	public Region<String,InMsg> inMsgRegion()
	{
		return GeodeClient.connect().getRegion("InMsg");
	}//------------------------------------------------
	@Bean
	public Region<String,OutMsg> outMsgRegion()
	{
		return GeodeClient.connect().getRegion("OutMsg");
	}//------------------------------------------------
	@Bean
	public Region<String,Order> orderRegion()
	{
		return GeodeClient.connect().getRegion("Order");
	}//------------------------------------------------
	@Bean
	public Region<String,Execution> executionRegion()
	{
		return GeodeClient.connect().getRegion("Execution");
	}//------------------------------------------------
	@Bean
	public Region<String,QFMessage> qfMessageMRegion()
	{
		return GeodeClient.connect().getRegion("QFMessage");
	}//------------------------------------------------
	
	
	
}
