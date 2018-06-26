package io.pivotal.perftest.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class PerformanceExeption extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8002002110832707303L;

	public PerformanceExeption()
	{
	}

	public PerformanceExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PerformanceExeption(String message, Throwable cause)
	{
		super(message, cause);
	}

	public PerformanceExeption(String message)
	{
		super(message);
	}

	public PerformanceExeption(Throwable cause)
	{
		super(cause);
	}
	

}
