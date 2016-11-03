package com.sohan.mutexblocked;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Interrupting2 {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Blocking2());
		t.start();
		TimeUnit.SECONDS.sleep(5);
		System.out.println("Issuing interrupt...");
		t.interrupt();
	}

}

class Blocking2 implements Runnable {

	private BlockedMutex blocked = new BlockedMutex();

	public void run() {
		System.out.println("Waiting for f()...");
		blocked.f();
		System.out.println("Broken out of blocked call...");
	}

}

class BlockedMutex {

	private Lock lock = new ReentrantLock();

	BlockedMutex() {
		lock.lock();
	}

	public void f() {
		try {
			lock.lockInterruptibly();
			System.out.println("lock acquired in f()");
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
	}

}