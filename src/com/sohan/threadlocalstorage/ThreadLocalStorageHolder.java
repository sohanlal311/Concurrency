package com.sohan.threadlocalstorage;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalStorageHolder {

	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {

		private Random rand = new Random(System.currentTimeMillis());

		protected synchronized Integer initialValue() {
			return rand.nextInt(10000);
		}

	};

	public static Integer get() {
		return value.get();
	}

	public static void increment() {
		value.set(get() + 1);
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++)
			exec.execute(new Accessor(i));
		TimeUnit.MILLISECONDS.sleep(3);
		exec.shutdownNow();
	}

}

class Accessor implements Runnable {

	private final int id;

	Accessor(int iden) {
		id = iden;
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			ThreadLocalStorageHolder.increment();
			System.out.println(this);
			Thread.yield();
		}
	}

	public String toString() {
		return "#" + id + ": " + ThreadLocalStorageHolder.get();
	}

}
