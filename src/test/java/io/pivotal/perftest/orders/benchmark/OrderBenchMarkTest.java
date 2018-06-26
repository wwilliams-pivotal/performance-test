package io.pivotal.perftest.orders.benchmark;

import static org.junit.Assert.*;

import org.apache.geode.cache.Region;
import org.junit.Test;
import static org.mockito.Mockito.*;
import io.pivotal.perftest.orders.creational.InMsgCreator;
import io.pivotal.perftest.orders.domain.InMsg;

/**
 * The Order bench mark test results
 * @author Gregory Green
 *
 */
public class OrderBenchMarkTest
{
	@SuppressWarnings("unchecked")
	@Test
	public void testConstructGetMeasurement()
	{
		OrderBenchMark benchMark = new OrderBenchMark();
				
		Region<String,InMsg> inMsgRegion = mock(Region.class);
		
		InMsg inMsg = new InMsgCreator("33").create();
		
		long timeMs = benchMark.constructGetMeasurement(inMsgRegion,inMsg , inMsg.getMsgId());
		
	
		assertTrue(timeMs >= 0);
	}//------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Test
	public void testConstructGPutMeasurement()
	{
		OrderBenchMark benchMark = new OrderBenchMark();
				
		Region<String,InMsg> inMsgRegion = mock(Region.class);
		
		InMsg inMsg = new InMsgCreator("33").create();
		
		long timeMs = benchMark.constructPutMeasurement(inMsgRegion,inMsg , inMsg.getMsgId());
		assertTrue(timeMs >= 0);
	}//------------------------------------------------
}
