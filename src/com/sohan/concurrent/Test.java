package com.sohan.concurrent;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		Thread t3 = new Thread(new MyInterruptedThread());
		t3.start();
		t3.join();

		Thread t4 = new Thread(new MyInterruptedThread2());
		t4.start();
		t4.join();

		Thread t1 = new Thread(new MyRunnable());
		t1.start();
		t1.join();

		Thread t2 = new SubClassThread();
		t2.start();
		t2.join();

		t3.interrupt();
		t4.interrupt();
		System.out.println(Thread.currentThread());
	}

}

class MyRunnable implements Runnable {
	public void run() {
		System.out.println("MyRunnable");
	}
}

class SubClassThread extends Thread {
	public void run() {
		System.out.println("SubclassThread");
	}
}

class MyInterruptedThread implements Runnable {
	public void run() {
		String[] names = { "Sohan", "Mohan" };
		for (String s : names) {
			try {
				Thread.sleep(1000);
				System.out.println("MyInterruptedThread." + s);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName()
						+ " is interrupted in SLEEP.");
				return;
			}
		}
		System.out.println("MyInterruptedThread.After FOR");
	}
}

class MyInterruptedThread2 implements Runnable {
	public void run() {
		String[] names = { "Sohan", "Mohan" };
		for (String s : names) {
			heavyCrunch(10000000);
			System.out.println("MyInterruptedThread2." + s);
			if (Thread.interrupted()) {
				System.out.println(Thread.currentThread().getName()
						+ " is interrupted after heavyCrunch.");
				return;
			}
		}
		System.out.println("MyInterruptedThread2.After FOR");
	}

	private void heavyCrunch(long count) {
		for (int i = 0; i < count; i++)
			;
	}
}