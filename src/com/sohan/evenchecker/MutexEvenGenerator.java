package com.sohan.evenchecker;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator {

	private int currentEvenNum = 0;
	private Lock lock = new ReentrantLock();

	public int next() {
		lock.lock();
		try {
			++currentEvenNum;
			Thread.yield(); // Cause failure faster
			++currentEvenNum;
			return currentEvenNum;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		EvenChecker.test(new MutexEvenGenerator());
	}

}
