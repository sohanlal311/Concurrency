package com.sohan.concurrent;

public class SimpleThreads {

	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

	private static class MessageLoops implements Runnable {
		public void run() {
			String[] importantInfo = { "Sohan", "Shonu", "Pankaj", "Krishna" };
			for (String s : importantInfo) {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					threadMessage("I wasn't done.");
					return;
				}
				threadMessage(s);
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		long patience = 9 * 1000;

		threadMessage("Starting MessageLoops thread");
		long startTime = System.currentTimeMillis();
		Thread t = new Thread(new MessageLoops());
		t.start();

		while (t.isAlive()) {
			threadMessage("Still waiting!");
			t.join(1000);
			if (System.currentTimeMillis() - startTime > patience
					&& t.isAlive()) {
				threadMessage("Tired of waiting!");
				t.interrupt();
				t.join();
			}
		}

		threadMessage("Finally!");
	}
}
