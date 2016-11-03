package com.sohan.evenchecker;

public class SynchronizedEvenGenerator extends IntGenerator {
	private int currentEvenNum = 0;

	public synchronized int next() {
		++currentEvenNum;
		Thread.yield(); // Cause failure faster
		++currentEvenNum;
		return currentEvenNum;
	}

	public static void main(String[] args) {
		EvenChecker.test(new SynchronizedEvenGenerator());
	}

}
