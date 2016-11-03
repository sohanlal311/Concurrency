package com.sohan.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HeapPollutionExample {

	public static void main(String[] args) {
		List<String> la = new ArrayList<String>();
		List<String> lb = new ArrayList<String>();
		ArrayBuilder.addToList(la, "One", "Two", "Three");
		ArrayBuilder.addToList(la, "Four", "Five", "Six");

		List<List<String>> listOfStringLists = new ArrayList<List<String>>();
		ArrayBuilder.addToList(listOfStringLists, la, lb);

		ArrayBuilder.faultyMethod(Arrays.asList("Sohan"),
				Arrays.asList("Mohan"));
	}
}

class ArrayBuilder {

	public static <T> void addToList(List<T> l, T... elements) {
		for (T t : elements) {
			l.add(t);
		}
	}

	public static void faultyMethod(List<String>... l) {
		Object[] objectArray = l;
		objectArray[0] = Arrays.asList(42);
		String string = l[0].get(0);
	}
}