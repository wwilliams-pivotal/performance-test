package io.pivotal.perftest.orders.creational.pdx;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.PdxWriter;

import io.pivotal.perftest.orders.domain.FixMap;
import io.pivotal.perftest.orders.domain.QFMessage;

public class QFMEessagePdxSerializer implements PdxSerializer
{

	@Override
	public boolean toData(Object obj, PdxWriter pdxWriter)
	{
		QFMessage qfMessage = (QFMessage)obj;
		pdxWriter.writeString("destSession",qfMessage.getDestSession());
		pdxWriter.writeString("itemFormat",qfMessage.getItemFormat());
		pdxWriter.writeString("itemType",qfMessage.getItemType());
		pdxWriter.writeString("msgId",qfMessage.getMsgId());
		pdxWriter.writeString("msgType",qfMessage.getMsgType());

		FixMap fixMap = qfMessage.getPayload();
		if(fixMap == null)
			pdxWriter.writeString("fixMap.payload","" );
		else
			pdxWriter.writeString("fixMap.payload","" );
		
		pdxWriter.writeString("srcSession",qfMessage.getSrcSession());
		pdxWriter.writeString("timeStamp",qfMessage.getTimeStamp());
		return true;
	}

	@Override
	public Object fromData(Class<?> class1, PdxReader pdxReader)
	{
		QFMessage qfMessage = new QFMessage();
		
		qfMessage.setDestSession(pdxReader.readString("destSession"));
		qfMessage.setItemFormat(pdxReader.readString("itemFormat"));
		qfMessage.setItemType(pdxReader.readString("itemType"));
		qfMessage.setMsgId(pdxReader.readString("msgId"));
		qfMessage.setMsgType(pdxReader.readString("msgType"));
		String payload = pdxReader.readString("fixMap.payload");
		qfMessage.setPayload(new FixMap(payload));
		qfMessage.setSrcSession(pdxReader.readString("srcSession"));
		qfMessage.setTimeStamp(pdxReader.readString("timeStamp"));
		return qfMessage;
	}

}
