package io.pivotal.perftest.orders.domain;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import nyla.solutions.core.io.IO;
import nyla.solutions.global.json.JacksonJSON;

public class InMsgTest
{

	@Test
	public void test()
	throws Exception
	{
		
		InMsg obj = new InMsg("1");
		assertEquals(obj.getMsgId(),"1");

		
		String json = IO.readClassPath("InMsg.json");
		
		obj = JacksonJSON.fromJson(new StringReader(json), InMsg.class);
		
		assertTrue(obj.getPayload() != null && obj.getPayload().getFixString() != null 
		&& obj.getPayload().getFixString().length() > 0);
		
	}

}
