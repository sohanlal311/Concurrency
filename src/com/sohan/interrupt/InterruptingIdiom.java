package com.sohan.interrupt;

import java.util.concurrent.TimeUnit;

public class InterruptingIdiom {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Blocking3());
		t.start();
		TimeUnit.MILLISECONDS.sleep(1000);
		t.interrupt();
	}

}

class Blocking3 implements Runnable {

	private volatile double d = 0.0;

	public void run() {
		try {
			while (!Thread.interrupted()) {
				NeedsCleanup n1 = new NeedsCleanup(1);
				try {
					System.out.println("Sleeping");
					TimeUnit.SECONDS.sleep(1);
					NeedsCleanup n2 = new NeedsCleanup(2);
					try {
						System.out.println("Calculating");
						for (int i = 1; i < 2500000; i++)
							d += (Math.PI + Math.E) / d;
						System.out.println("Finished time consuming operation");
					} finally {
						n2.cleanup();
					}
				} finally {
					n1.cleanup();
				}
			}
			System.out.println("Exiting via while() test");
		} catch (InterruptedException e) {
			System.out.println("Exiting via InterruptedException");
		}
	}

}

class NeedsCleanup {

	private final int id;

	NeedsCleanup(int iden) {
		id = iden;
		System.out.println("Needs cleanup " + id);
	}

	public void cleanup() {
		System.out.println("Cleaning up " + id);
	}

}