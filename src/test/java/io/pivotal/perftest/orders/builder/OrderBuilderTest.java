package io.pivotal.perftest.orders.builder;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import io.pivotal.perftest.orders.creational.ExecutionBuilder;
import io.pivotal.perftest.orders.creational.Builder;
import io.pivotal.perftest.orders.creational.InMsgBuilder;
import io.pivotal.perftest.orders.creational.OutMsgBuilder;
import io.pivotal.perftest.orders.creational.QFMessageBuilder;


public class OrderBuilderTest
{
	
	@Test
	public void testBuildInMsg()
	{
		this.verifyBuild(new InMsgBuilder("33"));		
	}
	
	@Test
	public void testExecutions()
	{
		this.verifyBuild(new ExecutionBuilder("33"));		

	}
	
	
	@Test
	public void testBuildOutMsg()
	{
		this.verifyBuild(new OutMsgBuilder("33"));
	}
	
	@Test
	public void testBuildQFMessage()
	{
		this.verifyBuild(new QFMessageBuilder());
	
	}
	
	public <T> void verifyBuild(Builder<T> builder)
	{		
		assertTrue(builder.getOutput().size() == 0);
		
		for(int i=1;i<= 10;i++)
		{
			builder.build();
			
			assertTrue(builder.getOutput().size() == i);
		}
		
		verifyUnique(builder.getOutput());
		
	}

	
	void verifyUnique(Collection<?> collection)
	{
		assertTrue(collection != null && !collection.isEmpty());
		Set<Object> prev = new HashSet<Object>();
		
		for (Object object : collection)
		{
			if(!prev.isEmpty())
				assertTrue(!prev.contains(object));
			
			prev.add(object);
		}
		
	}

}
