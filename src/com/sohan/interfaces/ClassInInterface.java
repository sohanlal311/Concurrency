package com.sohan.interfaces;

public interface ClassInInterface {
	void howdy();

	class Test implements ClassInInterface {
		public void howdy() {
			System.out.println("Inside nested inner class.");
		}

		public static void main(String[] args) {
			new Test().howdy();
		}
	}
}
