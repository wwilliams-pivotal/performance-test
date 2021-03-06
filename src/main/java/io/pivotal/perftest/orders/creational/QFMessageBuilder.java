package io.pivotal.perftest.orders.creational;


import java.util.ArrayList;
import java.util.Collection;
import io.pivotal.perftest.orders.domain.FixMap;
import io.pivotal.perftest.orders.domain.QFMessage;

public class QFMessageBuilder implements Builder<QFMessage>
{
	
	
	public void build()
	{
		QFMessage qfMessage = new QFMessage();
		qfMessage.setDestSession("");	
		qfMessage.setItemFormat("fix");
		qfMessage.setItemType("infix");
		qfMessage.setMsgId("pvt.17535."+qfMessageCounter+1);
		qfMessage.setMsgType("8");
		qfMessage.setPayload(new FixMap());
		String fixString = "8u003dFIX.4.2u00019u003d174u000135u003d8u000134u003d52u000149u003dFIXIMULATORu000152u003d20180104-16:07:49.361u000156u003dpvt1u00016u003d0u000111u003dpvt.17535.13u000114u003d0u000117u003dE1515082069374u000120u003d0u000131u003d0u000132u003d0u000137u003dO1515082069374u000138u003d300u000139u003d0u000154u003d2u000155u003dGOOGu0001150u003d0u0001151u003d300u000110u003d148u0001";
		qfMessage.getPayload().setFixString(fixString);
		qfMessage.setSrcSession("FIXIMULATOR");
		qfMessage.setTimeStamp("2018-01-04 16:07:49.373");
		
		this.qfMessageList.add(qfMessage);
		
		qfMessageCounter++;
		
	}
	


	/**
	 * @return the qfMessageList
	 */
	public Collection<QFMessage> getOutput()
	{
		qfMessageList.trimToSize();
		return qfMessageList;
	}//------------------------------------------------
	@Override
	public void cleanup()
	{
		this.qfMessageList.clear();
	}



	@Override
	public int buildCount()
	{
		return qfMessageCounter;
	}

	private int qfMessageCounter = 0;
	private ArrayList<QFMessage> qfMessageList = new ArrayList<QFMessage>(100);

}
