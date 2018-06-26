package io.pivotal.perftest.orders.creational.pdx;

import java.util.HashMap;
import java.util.Map;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.PdxWriter;

import io.pivotal.perftest.orders.domain.Execution;
import io.pivotal.perftest.orders.domain.InMsg;
import io.pivotal.perftest.orders.domain.Order;
import io.pivotal.perftest.orders.domain.OutMsg;
import io.pivotal.perftest.orders.domain.QFMessage;

public class PdxSerializers implements PdxSerializer
{
	public PdxSerializers(String[] classpatterns)
	{
		this();
	}
	public PdxSerializers()
	{
		map.put(Execution.class.getName(), new ExecutionPdxSerializer());
		map.put(InMsg.class.getName(), new InMsgPdxSerializer());
		map.put(OutMsg.class.getName(), new OutMsgPdxSerializer());
		map.put(Order.class.getName(), new OrderDetailPdxSerializer());
		map.put(QFMessage.class.getName(), new QFMEessagePdxSerializer());
		
	}

	@Override
	public Object fromData(Class<?> clz, PdxReader pdxReader)
	{
		return map.get(clz.getName()).fromData(clz, pdxReader);
	}

	@Override
	public boolean toData(Object obj, PdxWriter pdxWriter)
	{
		 return map.get(obj.getClass().getName()).toData(obj,pdxWriter);
	}

	
	private Map<String, PdxSerializer> map = new HashMap<String,PdxSerializer>(5);
}
