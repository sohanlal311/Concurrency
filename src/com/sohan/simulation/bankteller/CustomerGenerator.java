package com.sohan.simulation.bankteller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CustomerGenerator implements Runnable {

	private final CustomerLine customers;
	private static Random rand = new Random(311);

	CustomerGenerator(CustomerLine customers) {
		this.customers = customers;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
				customers.put(new Customer(rand.nextInt(1000)));
			}
		} catch (InterruptedException e) {
			System.out.println("CustomerGenerator interrupted.");
		}
	}

}
