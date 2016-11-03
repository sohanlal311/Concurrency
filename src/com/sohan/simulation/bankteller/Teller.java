package com.sohan.simulation.bankteller;

import java.util.concurrent.TimeUnit;

public class Teller implements Runnable, Comparable<Teller> {
	private static int count = 0;
	private final int id = count++;
	private final CustomerLine customers;
	private int customerServed = 0;
	private boolean serveCustomerLine = true;

	Teller(CustomerLine customers) {
		this.customers = customers;
	}

	@Override
	public int compareTo(Teller that) {
		return this.customerServed < that.customerServed ? -1
				: this.customerServed > that.customerServed ? 1 : 0;
	}

	@Override
	public void run() {

		try {
			while (!Thread.interrupted()) {
				Customer customer = customers.take();
				TimeUnit.MILLISECONDS.sleep(customer.getServiceTimeInMillis());
				synchronized (this) {
					customerServed++;
					while (!serveCustomerLine) {
						wait();
					}
				}
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted.");
		}

	}

	public synchronized void doSomethingElse() {
		customerServed = 0;
		serveCustomerLine = false;
	}

	public synchronized void serveCustomerLine() {
		assert !serveCustomerLine : "already serving: " + this;
		serveCustomerLine = true;
		notifyAll();
	}

	public String toString() {
		return "Teller " + id;
	}

	public String toShortString() {
		return "T" + id;
	}

}
