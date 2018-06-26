package io.pivotal.perftest.orders.creational.pdx;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.PdxWriter;
import io.pivotal.perftest.orders.domain.Execution;

public class ExecutionPdxSerializer implements PdxSerializer
{

	@Override
	public boolean toData(Object obj, PdxWriter pdxWriter)
	{
		Execution execution = (Execution)obj;
		pdxWriter.writeString("fixString",execution.getFixString());
		return true;
	}

	@Override
	public Object fromData(Class<?> class1, PdxReader pdxReader)
	{
		Execution execution = new Execution();
		
		execution.setFixString(pdxReader.readString("fixString"));
		return execution;
	}

}
