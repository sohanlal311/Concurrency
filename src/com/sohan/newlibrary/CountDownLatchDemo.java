package com.sohan.newlibrary;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
	private static final int SIZE = 100;

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(SIZE);
		ExecutorService exec = Executors.newCachedThreadPool();

		for (int i = 0; i < 5; i++)
			exec.execute(new WaitingTask(latch));

		for (int i = 0; i < SIZE; i++)
			exec.execute(new TaskPortion(latch));

		System.out.println("Lauched all tasks!");
		exec.shutdown();
	}
}

class TaskPortion implements Runnable {
	private static int count = 0;
	private final int id = count++;
	private static Random rand = new Random(123);
	private final CountDownLatch latch;

	TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}

	public String toString() {
		return String.format("%1$-3d ", id);
	}

	public void run() {
		try {
			doWork();
			latch.countDown();
		} catch (InterruptedException e) {
			// acceptable way to exit
		}
	}

	private void doWork() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
		System.out.println(this + " completed");
	}
}

class WaitingTask implements Runnable {
	private static int count = 0;
	private final int id = count++;
	private final CountDownLatch latch;

	WaitingTask(CountDownLatch latch) {
		this.latch = latch;
	}

	public String toString() {
		return String.format("Waiting task %1$-3d ", id);
	}

	public void run() {
		try {
			latch.await();
			System.out.println("Latch barrier passed for " + this);
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
	}
}