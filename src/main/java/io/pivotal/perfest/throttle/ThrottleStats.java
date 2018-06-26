package io.pivotal.perfest.throttle;

public class ThrottleStats {

	private long startMillis = 0;
	private int numberOrdersSecPerThreadOverall;
	private int numberOrders50MsOverall;
	private int numberOrders50Ms;
	
	public ThrottleStats(int numberOfThreads, int numberOrdersSecOverall) {	
		this.numberOrdersSecPerThreadOverall = numberOrdersSecOverall / numberOfThreads;
		this.numberOrders50MsOverall = numberOrdersSecOverall / 20;
		this.numberOrders50Ms = numberOrders50MsOverall / numberOfThreads;
		startMillis = System.currentTimeMillis();
	}
	
	public long pauseMillis(int orderCtr) {
		long elapsedMillis = System.currentTimeMillis() - startMillis;
		long elapsedDesired = calculateDesiredRate(orderCtr);
//		if (orderCtr >= 80 && orderCtr <= 120)
//			System.out.println("orderCtr=" + orderCtr + ", elapsedMillis=" + elapsedMillis + ", elapsedDesired=" + elapsedDesired);
		return elapsedDesired - elapsedMillis;
	}
	
	private long calculateDesiredRate(int orderCtr) {
		int seconds = orderCtr / numberOrdersSecPerThreadOverall;
		double subSecCtr = orderCtr % numberOrdersSecPerThreadOverall;
//		if (orderCtr >= 80 && orderCtr <= 120)
//			System.out.println("orderCtr=" + orderCtr + ", seconds=" + seconds + ", subSecCtr=" + subSecCtr + ", numberOrders50Ms=" + numberOrders50Ms + ", numberOrders50MsOverall=" + numberOrders50MsOverall);
		if (subSecCtr == 0) {
			return seconds * 1000;
		}
		int elapsedDesired = seconds * 1000 + ((int) (1000 / (numberOrdersSecPerThreadOverall / subSecCtr)));
		return elapsedDesired;
	}
	
	public int ordersPerSecPerThreadPerIteration() {
		return numberOrders50Ms;
	}
}
