package com.sohan.practice;

public class Outer {
	int i = 0;

	class Inner {
		final Outer outer = Outer.this;
		int j = i;
	}

	public static void main(String[] args) {
		Outer a = new Outer();
		Outer.Inner b = a.new Inner();
		Outer c = b.outer;
		System.out.println("success full");
	}
}