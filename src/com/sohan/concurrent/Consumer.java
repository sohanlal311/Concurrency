package com.sohan.concurrent;

import java.util.Random;

public class Consumer implements Runnable {
	private Drop drop;

	Consumer(Drop drop) {
		this.drop = drop;
	}

	@Override
	public void run() {
		Random rand = new Random(47);
		for (String message = drop.get(); !message.equals("DONE"); message = drop
				.get()) {
			System.out.format("MESSAGE RECEIVED: %s%n", message);
			try {
				Thread.sleep(rand.nextInt(5000));
			} catch (InterruptedException e) {
			}
		}
		System.out.format("MESSAGE RECEIVED: DONE");
	}
}
