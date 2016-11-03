package com.sohan.practice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyListClass {

	public static void main(String[] args) {
		List<Integer> li = new ArrayList<Integer>();
		List l = li;

		// l.add(4);
		l.add("Sohan");
		l.add(new Object());
		System.out.println(l);

		Iterator it = li.iterator();
		// Integer next = it.next();
		// System.out.println(it.next());
		// System.out.println(it.next());
		// System.out.println(it.next());
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		Pair<Integer, String> p1 = new Pair<Integer, String>(1, "Sohan");
		Pair<Integer, String> p2 = new Pair<Integer, String>(2, "Mohan");
		System.out.println(Util.<Integer, String> compare(p1, p2));
	}
	
	static void printObj(List<?> l){
		l.add(null);
	}
}

class Util {
	public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
		return p1.getKey().equals(p2.getKey())
				&& p1.getValue().equals(p2.getValue());
	}
}

class Pair<K, V> {
	private K key;
	private V value;

	Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}
}
