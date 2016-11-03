package com.sohan.newlibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

	private static final int SIZE = 25;

	public static void main(String[] args) throws Exception {
		final Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < SIZE; i++) {
			exec.execute(new CheckoutTask<Fat>(pool));
		}
		System.out.println("All CheckoutTasks are created.");

		List<Fat> list = new ArrayList<Fat>();
		for (int i = 0; i < SIZE; i++) {
			Fat item = pool.checkout();
			System.out.println("main checked out " + item);
			list.add(item);
		}

		Future<?> blocked = exec.submit(new Runnable() {
			@Override
			public void run() {
				try {
					// this call will be blocked because Semaphore prevents
					// additional checkouts
					pool.checkout();
				} catch (InterruptedException e) {
					System.out.println("Blocked interrupted");
				}
			}
		});

		TimeUnit.SECONDS.sleep(1);
		blocked.cancel(true);

		System.out.println("Checking in objects in list " + list);
		for (Fat item : list) {
			 pool.checkin(item);
		}

		System.out.println("Again checking in objects in list " + list);
		for (Fat item : list) {
			pool.checkin(item);
		}

		exec.shutdown();
	}
}

class Pool<T> {
	private final int size;
	private final boolean[] checkedout;
	private final Semaphore semaphore;
	private List<T> items = new ArrayList<T>();

	Pool(Class<T> classObject, int size) throws InstantiationException,
			IllegalAccessException {
		this.size = size;
		this.checkedout = new boolean[size];
		this.semaphore = new Semaphore(size, true);
		for (int i = 0; i < size; i++) {
			items.add(classObject.newInstance());
		}
	}

	public T checkout() throws InterruptedException {
		semaphore.acquire();
		return getItem();
	}

	public void checkin(T t) {
		if (releaseItem(t))
			semaphore.release();
	}

	private T getItem() {
		for (int i = 0; i < this.size; i++) {
			if (!checkedout[i]) {
				checkedout[i] = true;
				return items.get(i);
			}
		}
		return null; // Semaphore prevents reaching here
	}

	private boolean releaseItem(T t) {
		int index = items.indexOf(t);
		if (index == -1)
			return false;
		if (checkedout[index]) {
			checkedout[index] = false;
			return true;
		}
		return false;
	}
}

class Fat {
	private static int count = 0;
	private final int id = count++;
	private volatile double d = 0.0;

	Fat() {
		for (int i = 0; i < 10000; i++) {
			d += (Math.PI + Math.E) / d;
		}
	}

	public String toString() {
		return "Fat " + id;
	}

}

class CheckoutTask<T> implements Runnable {
	private final Pool<T> pool;
	private static int count = 0;
	private final int id = count++;

	CheckoutTask(Pool<T> pool) {
		this.pool = pool;
	}

	public void run() {
		try {
			T t = pool.checkout();
			System.out.println(this + " checked out " + t);
			TimeUnit.SECONDS.sleep(1);
			System.out.println(this + " checking in " + t);
			pool.checkin(t);
		} catch (InterruptedException e) {
			System.out.println("CheckoutTask interrupted.");
		}
	}

	public String toString() {
		return "CheckoutTask " + id;
	}
}