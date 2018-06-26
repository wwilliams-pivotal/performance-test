package io.pivotal.perftest.orders.creational;


import java.util.ArrayList;
import java.util.Collection;
import io.pivotal.perftest.orders.domain.OutMsg;

public class OutMsgBuilder extends OutMsgCreator implements Builder<OutMsg>
{
	
	public OutMsgBuilder(String nodeId) {
		super(nodeId);
	}
	
	public void build()
	{
		OutMsg outMsg = create();
		
		this.outMsgList.add(outMsg);
		
		outMsgCounter++;
	}
	
	/**
	 * @return the outMsgList
	 */
	public Collection<OutMsg> getOutput()
	{
		outMsgList.trimToSize();
		
		return outMsgList;
	}

	@Override
	public void cleanup()
	{
		this.outMsgList.clear();
	}

	@Override
	public int buildCount()
	{
		return outMsgCounter;
	}
	
	private int outMsgCounter = 0;
	private ArrayList<OutMsg> outMsgList = new ArrayList<OutMsg>(100);
	

}
