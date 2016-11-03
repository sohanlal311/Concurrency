package com.sohan.uncaughtexception;

public class MyUncaughtExceptionHandler implements
		Thread.UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		System.out.println("Caught " + e);
	}

}
