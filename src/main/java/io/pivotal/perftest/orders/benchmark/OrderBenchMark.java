package io.pivotal.perftest.orders.benchmark;

import org.apache.geode.cache.Region;
import nyla.solutions.core.util.Config;


public class OrderBenchMark
{
	public static final int sleepTime = Config.getPropertyInteger("SLEEP_TIME",2);
	
	
	public <T> long constructGetMeasurement(Region<String,T> region, T value,String id)
	{
		return constructMeasurement(false,region,value,id);
	}//------------------------------------------------
	public <T> long constructPutMeasurement(Region<String,T> region, T value,String id)
	{
		return constructMeasurement(true,region,value,id);
	}//------------------------------------------------
	/**
	 * Construct timing 
	 * @param <T> the time the is being built
	 * @param isPut put of get
	 * @param region the region
	 * @param value the value to put
	 * @param id the id to get
	 * @return time in milliseconds
	 */
	private <T> long constructMeasurement(boolean isPut, Region<String,T> region, T value,String id)
	{
		long start, end;
		if(isPut)
		{
				
				//time put
				start = System.currentTimeMillis();
				region.put(id, value);
				end = System.currentTimeMillis();
		}
		else
		{
				//time get
			start = System.currentTimeMillis();
			region.get(id);
			end = System.currentTimeMillis();
				
		}
			
		return end - start;

	}//------------------------------------------------

	

}
