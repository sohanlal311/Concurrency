package com.sohan.practice;

import java.util.ArrayList;
import java.util.List;

public class WildCard {

	public static void main(String[] args) {
		List<Integer> li = new ArrayList<Integer>();
		li.add(10);
		li.add(20);
		WildCard.addInteger(li, 30);

		List<?> l = li;
		helper(l, 40);

		System.out.println(li);
	}

	static void addInteger(List<?> list, Integer i) {
		// list.add(i);
		list.add(null);
	}

	static <T> void helper(List<T> l, Integer t) {
		l.add((T) t);
	}
}
