package io.pivotal.perftest.orders.domain;

public class Payload
{

	/**
	 * @return the map
	 */
	public FixMap getMap()
	{
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(FixMap map)
	{
		this.map = map;
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payload other = (Payload) obj;
		if (map == null)
		{
			if (other.map != null)
				return false;
		}
		else if (!map.equals(other.map))
			return false;
		return true;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Payload [map=").append(map).append("]");
		return builder.toString();
	}




	private FixMap map;
}
