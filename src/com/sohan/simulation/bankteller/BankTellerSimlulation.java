package com.sohan.simulation.bankteller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankTellerSimlulation {

	private static final int MAX_LINE_SIZE = 50;
	private static final int ADJUSTMENT_PERIOD = 1000;

	public static void main(String[] args) throws IOException {
		final CustomerLine customerLine = new CustomerLine(MAX_LINE_SIZE);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new CustomerGenerator(customerLine));
		exec.execute(new TellerManager(exec, customerLine, ADJUSTMENT_PERIOD));
		System.out.println("Press enter to exit...");
		System.in.read();
		exec.shutdownNow();
	}
}
