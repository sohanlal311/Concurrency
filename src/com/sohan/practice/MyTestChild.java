package com.sohan.practice;

public class MyTestChild {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyParent p = new MyChild();
		System.out.println(p.i);
		MyParent m = new MyChild();

		String o = new String("AC");
//		m.m2(o);

		short i = 0;
		short j = 9;
		m.m4(i, j);
		
		MyParent parent = new MyParent();
		parent.m2(null);
	}
}

class MyParent {
	public int i = 5;
	
	public void m2(Number o) {
		System.out.println("parent.m2.object");
	}
	
	public void m2(Byte s) {
		System.out.println("parent.m2.string");
	}

	public void m4(int i, int j) {
		System.out.println("parent.m4");
	}
}

class MyChild extends MyParent {
	public int i = 10;
	public void m2(String o) {
		System.out.println("child.m2");
	}

	public void m4(short i, short j) {
		System.out.println("child.m4");
	}
}
