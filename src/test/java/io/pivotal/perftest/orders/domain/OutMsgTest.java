package io.pivotal.perftest.orders.domain;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import nyla.solutions.core.io.IO;
import nyla.solutions.global.json.JacksonJSON;

public class OutMsgTest
{

	@Test
	public void test()
	throws Exception
	{
		
		OutMsg obj = new OutMsg("1");
		assertEquals(obj.getMsgId(),"1");

		
		String json = IO.readClassPath("OutMsg.json");
		
		obj = JacksonJSON.fromJson(new StringReader(json), OutMsg.class);
		
		assertTrue(obj.getPayload() != null && obj.getPayload().getFixString() != null 
		&& obj.getPayload().getFixString().length() > 0);
		
	}

}
