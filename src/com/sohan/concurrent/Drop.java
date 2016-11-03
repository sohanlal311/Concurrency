package com.sohan.concurrent;

public class Drop {
	private String message;
	private boolean empty = true;

	public synchronized String get() {
		while (empty) {
			try {
				wait();
			} catch (InterruptedException e) {

			}
		}
		empty = true;
		notifyAll();
		return message;
	}

	public synchronized void put(String message) {
		while (!empty) {
			try {
				wait();
			} catch (InterruptedException e) {

			}
		}
		empty = false;
		this.message = message;
		notifyAll();
	}
}
