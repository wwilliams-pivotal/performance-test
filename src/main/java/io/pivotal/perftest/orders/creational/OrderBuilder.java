package io.pivotal.perftest.orders.creational;


import java.util.ArrayList;
import java.util.Collection;

import io.pivotal.perftest.orders.domain.Order;

public class OrderBuilder extends OrderCreator implements Builder<Order>
{
	
	public OrderBuilder(String nodeId) {
		super(nodeId);
	}
	

	public void build()
	{
		
	
		Order orderDetail = create();
		
		orderDetailList.add(orderDetail);
		
		orderIdCounter++;
		
	}
	
	/**
	 * @return the orderDetailList
	 */
	public Collection<Order> getOutput()
	{
		orderDetailList.trimToSize();
		
		return orderDetailList;
	}

	@Override
	public void cleanup()
	{
		this.orderDetailList.clear();
		
	}

	@Override
	public int buildCount()
	{
		return this.orderIdCounter;
	}

	private int orderIdCounter= 0;
	private ArrayList<Order> orderDetailList = new ArrayList<Order>(100);

}
