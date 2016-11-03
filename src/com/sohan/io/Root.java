package com.sohan.io;

public class Root {
	public static void main(String[] args) {
		System.out.format("Sohan%n"); // always use %n for local platform
		System.out.print("Lal\n"); // \n always generates a line feed, so never
									// use it unless you specially need a line
									// feed.
		System.out.format("Sevta%n");
		System.out.format("%d,%n%1$,d", 223247827);
	}
}
