package com.sohan.ornamentalgarden;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrnamentalGarden {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new Entrance(i));
		}
		TimeUnit.SECONDS.sleep(1);
		Entrance.cancel();
		exec.shutdown();

		if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS)) {
			System.out.println("Some tasks were not interrupted.");
		}

		System.out.println("Total count: " + Entrance.getTotalCount());
		System.out.println("Sum of Entrances: " + Entrance.sumEntrance());
	}

}
