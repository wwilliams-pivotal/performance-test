package io.pivotal.perftest.orders.domain;

public class Execution
{

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Execution [fixString=").append(fixString).append("]");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fixString == null) ? 0 : fixString.hashCode());
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
		Execution other = (Execution) obj;
		if (fixString == null)
		{
			if (other.fixString != null)
				return false;
		}
		else if (!fixString.equals(other.fixString))
			return false;
		return true;
	}

	/**
	 * @return the fixString
	 */
	public String getFixString()
	{
		return fixString;
	}

	/**
	 * @param fixString the fixString to set
	 */
	public void setFixString(String fixString)
	{
		this.fixString = fixString;
	}

	private String fixString = "11:pvt.17535.7, 55:ZVZZT, 34:46, 56:PVT1, 35:8, 14:0, 37:O1515082068949, \n" + 
	"		49:FIXIMULATOR, 38:100, 17:E1515082068949, 39:0, 150:0, " + 
	"		151:100, 6:0, 8:FIX.4.2, 9:174, 52:20180104-16:07:48.942, 20:0, " + 
	"		31:0, 32:0, 54:1, 10:003  ";
	
}
