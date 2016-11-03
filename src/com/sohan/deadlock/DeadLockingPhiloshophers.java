package com.sohan.deadlock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeadLockingPhiloshophers {
	public static void main(String[] args) throws InterruptedException {
		int size = 5;
		int ponderFactor = 5;

		Chopstick[] sticks = new Chopstick[size];
		for (int i = 0; i < size; i++) {
			sticks[i] = new Chopstick(i);
		}

		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < size; i++) {
			exec.execute(new Philosopher(i, ponderFactor, sticks[i],
					sticks[(i + 1) % size]));
		}

		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}

class Chopstick {
	private final int id;
	private boolean taken = false;

	Chopstick(int id) {
		this.id = id;
	}

	public String toString() {
		return "Chopstick " + id;
	}

	public synchronized void take() throws InterruptedException {
		while (taken)
			wait();
		taken = true;
	}

	public synchronized void drop() {
		taken = false;
		notifyAll();
	}

}

class Philosopher implements Runnable {
	private final int id;
	private final int ponderFactor;
	private final Random rand = new Random(311);
	private Chopstick right;
	private Chopstick left;

	Philosopher(int id, int ponderFactor, Chopstick left, Chopstick right) {
		this.id = id;
		this.ponderFactor = ponderFactor;
		this.left = left;
		this.right = right;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println(this + " thinking");
				pause();
				System.out.println(this + " grabbing right " + right);
				right.take();
				System.out.println(this + " grabbing left " + left);
				left.take();
				System.out.println(this + " eating food");
				pause();
				System.out.println(this + " dropping right " + right);
				right.drop();
				System.out.println(this + " dropping left " + left);
				left.drop();
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted.");
		}
	}

	private void pause() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
	}

	public String toString() {
		return "Philosopher " + id;
	}
}