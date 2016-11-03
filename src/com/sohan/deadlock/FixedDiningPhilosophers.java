package com.sohan.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedDiningPhilosophers {
	public static void main(String[] args) throws InterruptedException {
		int size = 5;
		int ponderFactor = 5;

		Chopstick[] sticks = new Chopstick[size];
		for (int i = 0; i < size; i++) {
			sticks[i] = new Chopstick(i);
		}

		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < size; i++) {
			if (i < size - 1) {
				exec.execute(new Philosopher(i, ponderFactor, sticks[i],
						sticks[(i + 1) % size]));
			} else {
				exec.execute(new Philosopher(i, ponderFactor, sticks[(i + 1)
						% size], sticks[i]));
			}
		}

		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}
