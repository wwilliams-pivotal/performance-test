package io.pivotal.perftest.orders.domain;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import nyla.solutions.core.io.IO;
import nyla.solutions.global.json.JacksonJSON;

public class OrderTest
{

	@Test
	public void test()
	throws Exception
	{
		Order obj = new Order("1");
		assertEquals(obj.getOrderId(),"1");

		
		String json = IO.readClassPath("OrderDetail.json");
		
		obj = JacksonJSON.fromJson(new StringReader(json), Order.class);
		
		assertTrue(obj.getOrderString() != null && obj.getOrderString().length() > 0);
	}

}
