package io.pivotal.perftest.orders.creational;

import io.pivotal.perftest.orders.domain.FixMap;
import io.pivotal.perftest.orders.domain.InMsg;

public class InMsgCreator implements Creator<InMsg>
{
	
	private String nodeId;
	
	public InMsgCreator(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public InMsg create()
	{
		InMsg inMsg = new InMsg();
		inMsg.setDestSession("");
		inMsg.setItemFormat("fix");
		inMsg.setItemType("infix");
		inMsg.setMsgId(nodeId + "pvt.17535."+(inMsgCounter+1));
		inMsg.setPayload(new FixMap());
		
		String fixString = "\"8u003dFIX.4.2u00019u003d174u000135u003d8u000134u003d52u000149u003dFIXIMULATORu000152u003d20180104-16:07:49.361u000156u003dpvt1u00016u003d0u000111u003dpvt.17535.13u000114u003d0u000117u003dE1515082069374u000120u003d0u000131u003d0u000132u003d0u000137u003dO1515082069374u000138u003d300u000139u003d0u000154u003d2u000155u003dGOOGu0001150u003d0u0001151u003d300u000110u003d148u0001";
		
		inMsg.getPayload().setFixString(fixString);
		
		inMsg.setSrcSession("FIXIMULATOR");
		inMsg.setTimeStamp("2018-01-04 16:07:49.373");
		
		
		inMsgCounter++;
		
		return inMsg;
	}
	

	
	private int inMsgCounter = 0;


}
