package com.sohan.serialchecker;

public class CircularSet {
	private int[] arr;
	private final int len;
	private int index = 0;

	CircularSet(int size) {
		arr = new int[size];
		len = size;
		for (int i = 0; i < size; i++) {
			arr[i] = -1;
		}
	}

	public synchronized void add(int i) {
		arr[index] = i;
		index = ++index % len;
	}

	public synchronized boolean contains(int val) {
		for (int i = 0; i < len; i++) {
			if (arr[i] == val) {
				return true;
			}
		}
		return false;
	}

}
