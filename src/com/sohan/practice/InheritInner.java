package com.sohan.practice;

public class InheritInner extends WithInner.Inner {
	int i = 20;

	public void print() {
		System.out.println(i);
	}
	
	InheritInner(WithInner w) {
		w.super();
	}

	public static void main(String[] args) {
		WithInner wi = new WithInner();
		InheritInner ii = new InheritInner(wi);
		ii.print();
	}
}

class WithInner {
	class Inner {
		int i = 10;

		public void print() {
			System.out.println(i);
		}
	}
}