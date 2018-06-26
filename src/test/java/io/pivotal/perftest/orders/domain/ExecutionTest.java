package io.pivotal.perftest.orders.domain;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import nyla.solutions.core.io.IO;
import nyla.solutions.global.json.JacksonJSON;

public class ExecutionTest
{

	@Test
	public void test()
	throws Exception
	{
		Execution exe = new Execution();
		
		assertTrue(exe.getFixString() != null && exe.getFixString().length() > 0 );
		
		String json = IO.readClassPath("Execution.json");
		
		exe.setFixString(null);
		
		exe = JacksonJSON.fromJson(new StringReader(json), Execution.class);
		
		assertTrue(exe.getFixString() != null && exe.getFixString().length() > 0 );
	}

}
