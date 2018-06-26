package io.pivotal.perfest.throttle;

public class RateThrottler {

	/*   assume 10 threads and an order rate of 200/ sec   */
	private ThrottleStats throttleStats;
	
	public RateThrottler(int numberOfThreads, int numberOrdersSecOverall) {	
		this.throttleStats = new ThrottleStats(numberOfThreads, numberOrdersSecOverall);
	}
	
	public void throttle(int orderCtr) {
		
		long pauseMillis = throttleStats.pauseMillis(orderCtr);
//		System.out.println(orderCtr + ": pauseMillis=" + pauseMillis + " millis");

		if (pauseMillis > 0) {
			try {
//				System.out.println("Waiting " + pauseMillis + " millis");
				Thread.sleep(pauseMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int ordersPerSecPerThreadPerIteration() {
		return throttleStats.ordersPerSecPerThreadPerIteration();
	}
}
