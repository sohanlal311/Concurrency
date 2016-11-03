package com.sohan.threadlocalstorage;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalStorageHolderNonStatic {

	private ThreadLocal<Integer> value = new ThreadLocal<Integer>() {

		private Random rand = new Random(System.currentTimeMillis());

		protected synchronized Integer initialValue() {
			return rand.nextInt(10000);
		}

	};

	public Integer get() {
		return value.get();
	}

	public void increment() {
		value.set(get() + 1);
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadLocalStorageHolderNonStatic localStorage = new ThreadLocalStorageHolderNonStatic();
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++)
			exec.execute(new AccessorNonStatic(i, localStorage));
		System.out.println("main: " + localStorage.get());
		localStorage.increment();
		System.out.println("main: " + localStorage.get());
		TimeUnit.MILLISECONDS.sleep(3);
		exec.shutdownNow();
	}

}

class AccessorNonStatic implements Runnable {

	private final int id;

	private ThreadLocalStorageHolderNonStatic localHolder;

	AccessorNonStatic(int iden, ThreadLocalStorageHolderNonStatic localStorage) {
		id = iden;
		localHolder = localStorage;
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			localHolder.increment();
			System.out.println(this);
			Thread.yield();
		}
	}

	public String toString() {
		return "#" + id + ": " + localHolder.get();
	}

}
