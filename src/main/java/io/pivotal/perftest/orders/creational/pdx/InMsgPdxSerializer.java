package io.pivotal.perftest.orders.creational.pdx;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.PdxWriter;

import io.pivotal.perftest.orders.domain.FixMap;
import io.pivotal.perftest.orders.domain.InMsg;

public class InMsgPdxSerializer implements PdxSerializer
{

	@Override
	public boolean toData(Object obj, PdxWriter pdxWriter)
	{
		InMsg inMsg = (InMsg)obj;
		pdxWriter.writeString("destSession",inMsg.getDestSession());
		pdxWriter.writeString("itemFormat",inMsg.getItemFormat());
		pdxWriter.writeString("itemType",inMsg.getItemType());
		pdxWriter.writeString("msgId",inMsg.getMsgId());
		pdxWriter.writeString("msgType",inMsg.getMsgType());

		FixMap fixMap = inMsg.getPayload();
		if(fixMap == null)
			pdxWriter.writeString("fixMap.payload","" );
		else
			pdxWriter.writeString("fixMap.payload","" );
		
		pdxWriter.writeString("srcSession",inMsg.getSrcSession());
		pdxWriter.writeString("timeStamp",inMsg.getTimeStamp());
		return true;
	}

	@Override
	public Object fromData(Class<?> class1, PdxReader pdxReader)
	{
		InMsg inMsg = new InMsg();
		
		inMsg.setDestSession(pdxReader.readString("destSession"));
		inMsg.setItemFormat(pdxReader.readString("itemFormat"));
		inMsg.setItemType(pdxReader.readString("itemType"));
		inMsg.setMsgId(pdxReader.readString("msgId"));
		inMsg.setMsgType(pdxReader.readString("msgType"));
		String payload = pdxReader.readString("fixMap.payload");
		inMsg.setPayload(new FixMap(payload));
		inMsg.setSrcSession(pdxReader.readString("srcSession"));
		inMsg.setTimeStamp(pdxReader.readString("timeStamp"));
		return inMsg;
	}

}
