package com.sohan.concurrent;

import java.util.Random;

public class Producer implements Runnable {
	private Drop drop;

	Producer(Drop drop) {
		this.drop = drop;
	}

	@Override
	public void run() {
		String[] importantInfo = { "Sohan", "Mohan", "Shonu", "Monu" };
		Random rand = new Random(47);

		for (String s : importantInfo) {
			drop.put(s);
			try {
				Thread.sleep(rand.nextInt(5000));
			} catch (InterruptedException e) {
			}
		}

		drop.put("DONE");
	}
}
