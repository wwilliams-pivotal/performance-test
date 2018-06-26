package io.pivotal.perftest.orders.creational;

import io.pivotal.perftest.orders.domain.FixMap;
import io.pivotal.perftest.orders.domain.OutMsg;

public class OutMsgCreator implements Creator<OutMsg>
{
	
	private String nodeId;
	
	public OutMsgCreator(String nodeId) {
		this.nodeId = nodeId;
	}

	public OutMsg create()
	{
		OutMsg outMsg = new OutMsg();
		outMsg.setDestSession("FBSI1");
		outMsg.setItemFormat("fix");
		outMsg.setItemType("outfix");
		outMsg.setMsgId(nodeId + "pvt.17535."+(outMsgCounter+1));
		outMsg.setMsgType("8");
		outMsg.setPayload(new FixMap());
		
		String fixString = "8\\u003dFIX.4.2\\u00019\\u003d225\\u000135\\u003d8\\u000134\\u003d15\\u000149\\u003dpvt1\\u000152\\u003d20180104-16:07:49.777\\u000156\\u003dFBSI1\\u00011\\u003dX31639780\\u00016\\u003d0.0\\u000111\\u003dfix_0104_160148237_04077\\u000114\\u003d0.0\\u000117\\u003dpvt.17535.10013\\u000120\\u003d0\\u000131\\u003d0.0\\u000132\\u003d0.0\\u000137\\u003dpvt.17535.13\\u000138\\u003d300.0\\u000139\\u003d0\\u000154\\u003d2\\u000155\\u003dGOOG\\u000159\\u003d0\\u0001150\\u003d0\\u0001151\\u003d300.0\\u0001376\\u003dpvt.17535.13\\u000110\\u003d176\\u0001";
		
		outMsg.getPayload().setFixString(fixString);
		outMsg.setSrcSession("");
		outMsg.setTimeStamp("2018-01-04 16:07:49.778");

		
		outMsgCounter++;
		return outMsg;
	}

	
	private int outMsgCounter = 0;
	

}
