package com.sohan.uncaughtexception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CaptureUncaughtException {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread.setDefaultUncaughtExceptionHandler(new MyDefaultUncaughtExceptionHandler());
		ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
		exec.execute(new ExceptionThread());
	}

}
