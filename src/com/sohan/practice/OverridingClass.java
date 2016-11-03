package com.sohan.practice;

public class OverridingClass {

	public static void main(String[] args) {
		Parent p = new Child();
		p.m1();
		p.m2("ABC");
		p.m3();
		p.m4(1, 2);
		Child.m3();
	}

}

class Parent {
	public void m1() {
		System.out.println("A.m1()");
	}

	public void m2(Object o) {
		System.out.println("A.m2()");
	}

	public static void m3() {
		System.out.println("A.m3()");
	}

	public void m4(int i, int j) {
		System.out.println("A.m4()");
	}
}

class Child extends Parent {
	public void m1() {
		System.out.println("B.m1()");
	}

	public void m2(String s) {
		System.out.println("B.m2()");
	}

	public static void m3() {
		System.out.println("B.m3()");
	}

	public void m4(short i, short j) {
		System.out.println("B.m4()");
	}
}

