package com.sohan.evenchecker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
	private IntGenerator generator;
	private final int id;

	EvenChecker(IntGenerator g, int ident) {
		generator = g;
		id = ident;
	}

	@Override
	public void run() {
		int val;
		while (!generator.isCanceled()) {
			val = generator.next();
			if (val % 2 != 0) {
				System.out.println(val + " not even in EvenChecker" + id);
				generator.cancel();
			}
		}
	}

	public static void test(IntGenerator g, int count) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++) {
			exec.execute(new EvenChecker(g, i));
		}
		exec.shutdown();
	}

	public static void test(IntGenerator g) {
		test(g, 10);
	}

}
