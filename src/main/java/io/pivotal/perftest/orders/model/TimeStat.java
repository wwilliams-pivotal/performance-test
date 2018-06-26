package io.pivotal.perftest.orders.model;

public class TimeStat
{
	
	

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @return the avg
	 */
	public double getAvg()
	{
		return avg;
	}
	/**
	 * @return the min
	 */
	public double getMin()
	{
		return min;
	}
	/**
	 * @return the max
	 */
	public double getMax()
	{
		return max;
	}
	/**
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @param avg the avg to set
	 */
	public void setAvg(double avg)
	{
		this.avg = avg;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(double min)
	{
		this.min = min;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(double max)
	{
		this.max = max;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time)
	{
		this.time = time;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	private String name;
	private double avg;
	private double min;
	private double max;
	private String time;
	private String id;
}
