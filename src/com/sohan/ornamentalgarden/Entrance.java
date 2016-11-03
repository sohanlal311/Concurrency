package com.sohan.ornamentalgarden;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Entrance implements Runnable {

	private final int id;

	private int number = 0;

	private static volatile boolean canceled = false;

	private static Count count = new Count();

	private static List<Entrance> list = new ArrayList<Entrance>();

	Entrance(int iden) {
		id = iden;
		list.add(this);
	}

	@Override
	public void run() {
		while (!canceled) {
			synchronized (this) {
				++number;
			}
			System.out.println(this + " Total: " + count.increment());
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException i) {
				System.out.println("Sleep interrupted.");
			}
		}
		System.out.println("Stopping " + this);
	}

	public static void cancel() {
		canceled = true;
	}

	public synchronized int getValue() {
		return number;
	}

	public String toString() {
		return "Entrance " + id + ": " + getValue();
	}

	public static int getTotalCount() {
		return count.value();
	}

	public static int sumEntrance() {
		int sum = 0;
		for (Entrance e : list)
			sum += e.getValue();
		return sum;
	}

}
