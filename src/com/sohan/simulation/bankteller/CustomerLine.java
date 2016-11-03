package com.sohan.simulation.bankteller;

import java.util.concurrent.ArrayBlockingQueue;

public class CustomerLine extends ArrayBlockingQueue<Customer> {

	private static final long serialVersionUID = 1L;

	public CustomerLine(int maxSize) {
		super(maxSize);
	}

	public String toString() {
		if (this.size() == 0)
			System.out.println("[Empty]");

		StringBuilder sb = new StringBuilder();
		for (Customer customer : this) {
			sb.append(customer);
		}
		return sb.toString();
	}

}
