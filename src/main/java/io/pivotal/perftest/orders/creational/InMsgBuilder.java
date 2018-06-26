package io.pivotal.perftest.orders.creational;


import java.util.ArrayList;
import java.util.Collection;
import io.pivotal.perftest.orders.domain.InMsg;

public class InMsgBuilder extends InMsgCreator implements Builder<InMsg>
{
	public InMsgBuilder(String nodeId) {
		super(nodeId);
	}
	
	public void build()
	{
		InMsg inMsg = create();
		
		this.inMsgList.add(inMsg);
		
		
		inMsgCounter++;
	}
	
	/**
	 * @return the inMsgList
	 */
	public Collection<InMsg> getOutput()
	{
		inMsgList.trimToSize();
		
		return inMsgList;
	}
	@Override
	public void cleanup()
	{
		this.inMsgList.clear();
		
	}

	@Override
	public int buildCount()
	{
	
		return inMsgCounter;
	}
	
	private int inMsgCounter = 0;
	private ArrayList<InMsg> inMsgList = new ArrayList<InMsg>(100);


}
