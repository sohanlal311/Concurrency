package com.sohan.evenchecker;

public class EvenGenerator extends IntGenerator {
	private int currentEvenNum = 0;

	public int next() {
		++currentEvenNum;
		++currentEvenNum;
		return currentEvenNum;
	}

	public static void main(String[] args) {
		EvenChecker.test(new EvenGenerator());
	}
}
