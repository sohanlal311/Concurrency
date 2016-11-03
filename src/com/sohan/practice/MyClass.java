package com.sohan.practice;

import com.sohan.interfaces.ClassInInterface;

public class MyClass {
	public static void main(String[] args) {
		A a = new B();
		System.out.println(a.i);
		a.print();
		A a1 = new A();
		A.InnerClass inner = new A.InnerClass();
		Dummy print = a1.print();
		print.draw();
		System.out.println(A.InnerClass.j);
		new ClassInInterface.Test().howdy();
	}
}

interface Dummy {
	void draw();
}

class A {
	protected int i = 10;
	static int k = 20;

	protected Dummy print() {
		System.out.println(i);
		abstract class ClassInMethod {
			int g = 29;

			public void printG() {
				System.out.println(g);
			}

			public abstract void myMethod();
		}
		class ClassInMethod2 extends ClassInMethod implements Dummy {
			@Override
			public void myMethod() {
			}

			@Override
			public void draw() {
			}
		}
		return new ClassInMethod2();
	}

	public static class InnerClass {
		// int i = 5;
		static final int j = 10;

		public void printInner() {
			System.out.println(k);
		}
	}
}

class B extends A {
	public int i = 15;

	// public void print() {
	// System.out.println(i);
	// }
}