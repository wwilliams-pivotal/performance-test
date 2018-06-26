package io.pivotal.perftest.orders.creational.pdx;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.PdxWriter;

import io.pivotal.perftest.orders.domain.FixMap;
import io.pivotal.perftest.orders.domain.OutMsg;

public class OutMsgPdxSerializer implements PdxSerializer
{

	@Override
	public boolean toData(Object obj, PdxWriter pdxWriter)
	{
		OutMsg outMsg = (OutMsg)obj;
		pdxWriter.writeString("destSession",outMsg.getDestSession());
		pdxWriter.writeString("itemFormat",outMsg.getItemFormat());
		pdxWriter.writeString("itemType",outMsg.getItemType());
		pdxWriter.writeString("msgId",outMsg.getMsgId());
		pdxWriter.writeString("msgType",outMsg.getMsgType());

		FixMap fixMap = outMsg.getPayload();
		if(fixMap == null)
			pdxWriter.writeString("fixMap.payload","" );
		else
			pdxWriter.writeString("fixMap.payload","" );
		
		pdxWriter.writeString("srcSession",outMsg.getSrcSession());
		pdxWriter.writeString("timeStamp",outMsg.getTimeStamp());
		return true;
	}

	@Override
	public Object fromData(Class<?> class1, PdxReader pdxReader)
	{
		OutMsg outMsg = new OutMsg();
		
		outMsg.setDestSession(pdxReader.readString("destSession"));
		outMsg.setItemFormat(pdxReader.readString("itemFormat"));
		outMsg.setItemType(pdxReader.readString("itemType"));
		outMsg.setMsgId(pdxReader.readString("msgId"));
		outMsg.setMsgType(pdxReader.readString("msgType"));
		String payload = pdxReader.readString("fixMap.payload");
		outMsg.setPayload(new FixMap(payload));
		outMsg.setSrcSession(pdxReader.readString("srcSession"));
		outMsg.setTimeStamp(pdxReader.readString("timeStamp"));
		return outMsg;
	}

}
