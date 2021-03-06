package com.sohan.concurrent;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeLock {

	static class Friend {
		private String name;
		private Lock lock = new ReentrantLock();

		Friend(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		private boolean impedingBow(Friend bower) {
			boolean myLock = false;
			boolean yourLock = false;
			try {
				myLock = lock.tryLock();
				yourLock = bower.lock.tryLock();
			} finally {
				if (!(myLock && yourLock)) {
					if (myLock) {
						lock.unlock();
					}
					if (yourLock) {
						bower.lock.unlock();
					}
				}
			}

			return myLock && yourLock;
		}

		public void bow(Friend bower) {
			if (impedingBow(bower)) {
				try {
					System.out.format("%s: %s has bowed to me%n", this.name,
							bower.getName());
					bower.bowBack(this);
				} finally {
					lock.unlock();
					bower.lock.unlock();
				}
			} else {
				System.out.format("%s: %s started to bow me, but saw that, "
						+ " I was already bowing to him%n", this.name,
						bower.getName());
			}
		}

		public void bowBack(Friend bower) {
			System.out.format("%s: %s has bowed back to me!%n", this.name,
					bower.getName());
		}
	}

	static class BowerLoop implements Runnable {
		private Friend bower;
		private Friend bowee;

		BowerLoop(Friend bower, Friend bowee) {
			this.bower = bower;
			this.bowee = bowee;
		}

		public void run() {
			Random rand = new Random(10);
			for (;;) {
				try {
					Thread.sleep(rand.nextInt(10));
				} catch (InterruptedException e) {

				}
				bowee.bow(bower);
			}
		}
	}

	public static void main(String[] args) {
		Friend alphonse = new Friend("Alphonse");
		Friend gaston = new Friend("Gaston");
		new Thread(new BowerLoop(alphonse, gaston)).start();
	}

}
