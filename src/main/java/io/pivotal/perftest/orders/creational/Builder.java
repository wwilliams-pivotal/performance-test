package io.pivotal.perftest.orders.creational;

import java.util.Collection;

public interface Builder<T>
{

	void build();
	
	public Collection<T> getOutput();
	
	void cleanup();
	
	int buildCount();
}
