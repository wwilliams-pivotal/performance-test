package io.pivotal.perftest.orders.creational;


import java.util.ArrayList;
import java.util.Collection;

import io.pivotal.perftest.orders.domain.Execution;
public class ExecutionBuilder extends ExecutionCreator implements Builder<Execution>
{
	
	public ExecutionBuilder(String nodeId) {
		super(nodeId);
	}
	
	public void build()
	{
		Execution execution = create();
		
		this.executionList.add(execution);
		executionIdCounter++;
	}//------------------------------------------------

	/**
	 * @return the executionList
	 */
	public Collection<Execution> getOutput()
	{
		executionList.trimToSize();
		
		return executionList;
	}
	@Override
	public int buildCount()
	{
		return this.executionIdCounter;
	}

	@Override
	public void cleanup()
	{
		this.executionList.clear();
	}
	private int executionIdCounter = 0;
	private ArrayList<Execution> executionList = new ArrayList<Execution>(100);


}
