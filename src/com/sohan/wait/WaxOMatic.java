package com.sohan.wait;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WaxOMatic {
	public static void main(String[] args) throws InterruptedException {
		Car car = new Car();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOff(car));
		exec.execute(new WaxOn(car));
		TimeUnit.SECONDS.sleep(5);
		System.out.println("Sending interrupt SIGNAL!!!");
		exec.shutdownNow();
	}
}

class WaxOn implements Runnable {

	private final Car car;

	WaxOn(Car car) {
		this.car = car;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("Waxing On!");
				TimeUnit.SECONDS.sleep(1);
				car.waxed();
				car.waitForBuffing();
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted WaxOn.");
		}
	}
}

class WaxOff implements Runnable {

	private final Car car;

	WaxOff(Car car) {
		this.car = car;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				car.waitForWaxing();
				System.out.println("Buffing On!");
				TimeUnit.SECONDS.sleep(1);
				car.buffed();
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted WaxOn.");
		}
	}
}

class Car {

	private boolean waxOn = false;

	synchronized void waxed() {
		waxOn = true;
		notifyAll();
	}

	synchronized void buffed() {
		waxOn = false;
		notifyAll();
	}

	synchronized void waitForWaxing() throws InterruptedException {
		while (!waxOn)
			wait();
	}

	synchronized void waitForBuffing() throws InterruptedException {
		while (waxOn)
			wait();
	}

}