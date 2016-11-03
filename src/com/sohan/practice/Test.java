package com.sohan.practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		// new Properties().getProperty(name, defaultValue)
		Collection<Integer> c = Arrays.asList(1, 2, 3, 4, 5, 6);
		System.out.println("There are "
				+ Algorithm.countIf(c, new OddPredicate()) + " odd numbers.");

		List<Integer> list = Arrays.asList(13, 22, 33, 14, 5, 16);
		System.out.println("Maximum number in the array is "
				+ Algorithm.findMaximal(list, 0, list.size()));

		List<Integer> li = Arrays.asList(3, 4, 6, 8, 11, 15, 28, 32);
		Collection<Integer> ci = Arrays.asList(7, 18, 19, 25);
		int i = Algorithm.findFirstRelativePrime(li, 0, li.size(),
				new RelativelyPrimePredicate(ci));
		if (i != -1) {
			System.out.print(li.get(i) + " is relatively prime to ");
			for (Integer k : ci) {
				System.out.print(k + " ");
			}
			System.out.println();
		}
	}
}

class Algorithm {
	public static <T> int countIf(Collection<T> c, UnaryPredicate<T> p) {
		int count = 0;
		for (T t : c) {
			if (p.test(t)) {
				count++;
			}
		}
		return count;
	}

	public static <T extends Object & Comparable<T>> T findMaximal(List<T> l,
			int begin, int end) {
		T max = l.get(begin);
		for (++begin; begin < end; begin++) {
			if (l.get(begin).compareTo(max) > 0) {
				max = l.get(begin);
			}
		}
		return max;
	}

	public static <T> int findFirstRelativePrime(List<T> list, int begin,
			int end, UnaryPredicate<T> p) {
		for (; begin < end; begin++) {
			if (p.test(list.get(begin))) {
				return begin;
			}
		}
		return -1;
	}

	public static int gcd(int x, int y) {
		for (int r; (r = x % y) != 0; x = y, y = r)
			;
		return y;
	}
}

interface UnaryPredicate<T> {
	boolean test(T t);
}

class OddPredicate implements UnaryPredicate<Integer> {

	@Override
	public boolean test(Integer t) {
		return t % 2 != 0;
	}

}

class RelativelyPrimePredicate implements UnaryPredicate<Integer> {

	private Collection<Integer> c;

	public RelativelyPrimePredicate(Collection<Integer> c) {
		this.c = c;
	}

	@Override
	public boolean test(Integer x) {
		for (Integer i : c) {
			if (Algorithm.gcd(x, i) != 1) {
				return false;
			}
		}
		return c.size() > 0;
	}

}