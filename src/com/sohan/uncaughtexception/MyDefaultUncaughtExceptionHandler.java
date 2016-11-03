package com.sohan.uncaughtexception;

import java.lang.Thread.UncaughtExceptionHandler;

public class MyDefaultUncaughtExceptionHandler implements
		UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		System.out.println("Default: caught " + e);
	}

}
