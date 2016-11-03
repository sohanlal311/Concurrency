package com.sohan.practice;

import java.util.ArrayList;
import java.util.List;

public class BoxDemo {

	static <U> void addBox(U u, List<Box<U>> boxes) {
		Box<U> box = new Box<U>();
		box.set(u);
		boxes.add(box);
	}

	static <U> void printBoxes(List<Box<U>> boxes) {
		int counter = 0;
		for (Box<U> box : boxes) {
			System.out.println("Box #" + counter++ + " contains ["
					+ box.get().toString() + "]");
		}
	}

	public static void main(String[] args) {
		List<Box<Integer>> boxes = new ArrayList<Box<Integer>>();
		BoxDemo.<Integer> addBox(10, boxes);
		BoxDemo.addBox(20, boxes);
		BoxDemo.addBox(30, boxes);
		BoxDemo.printBoxes(boxes);
	}
}

class Box<T> {
	private T t;

	void set(T t) {
		this.t = t;
	}

	T get() {
		return t;
	}
}