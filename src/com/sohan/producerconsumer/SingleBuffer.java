package com.sohan.producerconsumer;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingleBuffer {

	ExecutorService exec;
	Buffer buffer = new Buffer(5);
	Producer producer = new Producer(buffer);
	Consumer consumer = new Consumer(buffer);

	SingleBuffer() {
		exec = Executors.newCachedThreadPool();
		exec.execute(consumer);
		exec.execute(producer);
	}

	public static void main(String[] args) throws InterruptedException {
		SingleBuffer singleBuffer = new SingleBuffer();
		TimeUnit.SECONDS.sleep(10);
		singleBuffer.exec.shutdownNow();
	}
}

class Producer implements Runnable {

	private final Buffer buffer;

	private final Random rand = new Random(47);

	Producer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				buffer.add(rand.nextInt(1000));
				TimeUnit.MILLISECONDS.sleep(500);
			}
		} catch (InterruptedException e) {
			System.out.println("Producer interrupted.");
		}
	}
}

class Consumer implements Runnable {

	private final Buffer buffer;

	Consumer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				buffer.pop();
				TimeUnit.MILLISECONDS.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.out.println("Consumer interrupted.");
		}
	}
}

class Buffer {

	private final int[] arr;
	private int count = 0;

	Buffer(int size) {
		arr = new int[size];
	}

	private boolean isEmpty() {
		return count == 0;
	}

	private boolean isFull() {
		return count == arr.length;
	}

	public synchronized void add(int n) throws InterruptedException {
		while (isFull()) {
			System.out.println("Buffer is FULL !!!");
			wait();
		}
		arr[count++] = n;
		System.out.println("P: " + n);
		notify();
	}

	public synchronized int pop() throws InterruptedException {
		while (isEmpty()) {
			System.out.println("Buffer is EMPTY !!!");
			wait();
		}
		int n = arr[--count];
		System.out.println("C: " + n);
		notify();
		return n;
	}

}