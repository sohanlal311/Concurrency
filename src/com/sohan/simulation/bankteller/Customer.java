package com.sohan.simulation.bankteller;

public class Customer {
	private final int serviceTimeInMillis;

	Customer(int serviceTimeInMillis) {
		this.serviceTimeInMillis = serviceTimeInMillis;
	}

	public int getServiceTimeInMillis() {
		return serviceTimeInMillis;
	}

	public String toString() {
		return "[" + serviceTimeInMillis + "]";
	}
}
