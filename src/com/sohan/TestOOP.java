package com.sohan;

public class TestOOP {

	public static void main(String[] args) {
		new Child().g();
	}

}

class Parent {

	void f() {
		System.out.println("Parent.f()");
		p();
	}

	private void p() {
		System.out.println("Parent.p()");
		k();
	}

	void k() {
		System.out.println("Parent.k()");
	}

}

class Child extends Parent {

	void g() {
		System.out.println("Child.g()");
		f();
	}

	private void p() {
		System.out.println("Child.p()");
		k();
	}

	void k() {
		System.out.println("Child.k()");
	}

}