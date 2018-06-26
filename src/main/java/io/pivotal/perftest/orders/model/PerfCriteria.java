package io.pivotal.perftest.orders.model;

public class PerfCriteria
{



	/**
	 * @return the orderCount
	 */
	public int getOrderCount()
	{
		return orderCount;
	}
	/**
	 * @param orderCount the orderCount to set
	 */
	public void setOrderCount(int orderCount)
	{
		this.orderCount = orderCount;
	}

	private int orderCount = 0;
}
