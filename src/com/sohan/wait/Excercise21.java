package com.sohan.wait;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Excercise21 {
	public static void main(String[] args) throws InterruptedException {
		Runnable r1 = new WaitRunnable();
		Runnable r2 = new NotifyRunnable(r1);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(r1);
		exec.execute(r2);
		TimeUnit.SECONDS.sleep(3);
		exec.shutdownNow();
	}
}

class WaitRunnable implements Runnable {

	public void run() {
		System.out.println("WaitRunnable is waiting");
		try {
			synchronized (this) {
				wait();
			}
			System.out.println("WaitRunnable comes out of wait.");
		} catch (InterruptedException e) {
			System.out.println("WaitRunnable is interrupted");
		}
	}

}

class NotifyRunnable implements Runnable {

	private final Runnable runnable;

	public NotifyRunnable(Runnable r) {
		runnable = r;
	}

	public void run() {
		System.out.println("NotifyRunnable is sleeping");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			System.out.println("NotifyRunnable is interrupted");
		}
		synchronized (runnable) {
			runnable.notifyAll();
		}
	}

}