package com.sohan.uncaughtexception;

public class ExceptionThread implements Runnable {

	@Override
	public void run() {
		Thread currentThread = Thread.currentThread();
		System.out.println("Run by: " + currentThread.getName());
		System.out.println("eh=" + currentThread.getUncaughtExceptionHandler());
		throw new RuntimeException();
	}

}
