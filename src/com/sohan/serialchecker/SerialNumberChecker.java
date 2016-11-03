package com.sohan.serialchecker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SerialNumberChecker {
	private static final int SIZE = 10;
	private static CircularSet serials = new CircularSet(1000);
	private static ExecutorService exec = Executors.newCachedThreadPool();

	private static class SerialChecker implements Runnable {

		@Override
		public void run() {
			while (true) {
				int serial = SerialNumberGenerator.nextSerialNumber();
				if (serials.contains(serial)) {
					System.out.println("Duplicate: " + serial);
					System.exit(0);
				}
				serials.add(serial);
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < SIZE; i++) {
			exec.execute(new SerialChecker());
		}

		TimeUnit.SECONDS.sleep(10);
		System.out.println("No duplicates detected in 10 seconds.");
		System.exit(0);
	}
}
