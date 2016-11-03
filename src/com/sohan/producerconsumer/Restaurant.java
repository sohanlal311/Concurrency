package com.sohan.producerconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Restaurant {

	Meal meal;
	ExecutorService exec;
	WaitPerson waitPerson = new WaitPerson(this);
	Chef chef = new Chef(this);

	Restaurant() {
		exec = Executors.newCachedThreadPool();
		exec.execute(waitPerson);
		exec.execute(chef);
	}

	public static void main(String[] args) {
		new Restaurant();
	}

}

class Meal {

	private final int id;

	Meal(int iden) {
		id = iden;
	}

	public String toString() {
		return "Meal #" + id;
	}
}

class WaitPerson implements Runnable {

	private final Restaurant res;

	WaitPerson(Restaurant res) {
		this.res = res;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (res.meal == null)
						wait();
				}

				System.out.println("WaitPerson got " + res.meal);

				synchronized (res.chef) {
					res.meal = null;
					res.chef.notifyAll();
				}
			}
		} catch (InterruptedException e) {
			System.out.println("WaitPerson interrupted.");
		}
	}
}

class Chef implements Runnable {

	private final Restaurant res;
	private int count = 0;

	Chef(Restaurant res) {
		this.res = res;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (res.meal != null)
						wait();
				}

				if (++count == 10) {
					System.out.println("Out of food, closing.");
					res.exec.shutdownNow();
				}

				System.out.print("Order up! ");

				synchronized (res.waitPerson) {
					res.meal = new Meal(count);
					res.waitPerson.notifyAll();
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Chef interrupted.");
		}
	}
}
