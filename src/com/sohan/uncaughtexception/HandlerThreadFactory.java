package com.sohan.uncaughtexception;

import java.util.concurrent.ThreadFactory;

public class HandlerThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		Thread t = new Thread(r);
		System.out.println("Creating new thread.");
		t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		System.out.println("eh=" + t.getUncaughtExceptionHandler());
		return t;
	}

}
