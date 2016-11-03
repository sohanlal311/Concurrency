package com.sohan.concurrent;

public class DeadLock {

	static class Friend {
		private final String name;

		Friend(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public synchronized void bow(Friend bower) {
			System.out.format("%s: %s has bowed me.%n", this.name,
					bower.getName());
			bower.bowBack(this);
		}

		public synchronized void bowBack(Friend bower) {
			System.out.format("%s: %s has bowed back me.%n", this.name,
					bower.getName());
		}
	}

	public static void main(String[] args) {
		final Friend alphonse = new Friend("Alphonse");
		final Friend gatson = new Friend("Gatson");

		new Thread(new Runnable() {
			public void run() {
				alphonse.bow(gatson);
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				gatson.bow(alphonse);
			}
		}).start();
	}

}