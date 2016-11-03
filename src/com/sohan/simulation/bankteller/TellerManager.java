package com.sohan.simulation.bankteller;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TellerManager implements Runnable {
	private final ExecutorService exec;
	private final CustomerLine customers;
	private final int adjustmentPeriodInMillis;
	private final PriorityQueue<Teller> workingTellers = new PriorityQueue<Teller>();
	private final Queue<Teller> tellersDoingSomethingElse = new LinkedList<Teller>();

	TellerManager(ExecutorService exec, CustomerLine customers,
			int adjustmentPeriodInMillis) {
		this.exec = exec;
		this.customers = customers;
		this.adjustmentPeriodInMillis = adjustmentPeriodInMillis;
		Teller teller = new Teller(customers);
		this.exec.execute(teller);
		this.workingTellers.add(teller);
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(adjustmentPeriodInMillis);
				adjustTellerNumber();
				System.out.print(customers + " {");
				for (Teller teller : workingTellers) {
					System.out.print(teller.toShortString() + " ");
				}
				System.out.println("}");
			}
		} catch (InterruptedException e) {
			System.out.println("TellerManager interrupted");
		}

	}

	public void adjustTellerNumber() {
		if (customers.size() / workingTellers.size() > 2) {
			if (tellersDoingSomethingElse.size() > 0) {
				Teller teller = tellersDoingSomethingElse.remove();
				teller.serveCustomerLine();
				workingTellers.offer(teller);
			}
			Teller teller = new Teller(customers);
			exec.execute(teller);
			workingTellers.add(teller);
			return;
		}

		if (customers.size() / workingTellers.size() < 2) {
			reassignOneTeller();
		}

		if (customers.size() == 0) {
			while (workingTellers.size() > 1)
				reassignOneTeller();
		}
	}

	private void reassignOneTeller() {
		Teller teller = workingTellers.poll();
		teller.doSomethingElse();
		tellersDoingSomethingElse.offer(teller);
	}

}
