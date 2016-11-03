package com.sohan.criticalsection;

public class Pair {
	private int x, y;

	Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	Pair() {
		this(0, 0);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void incrementX() {
		x++;
	}

	public void incrementY() {
		y++;
	}

	@Override
	public String toString() {
		return "x: " + x + " y: " + y;
	}

	public class PairValuesNotEqualException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		PairValuesNotEqualException() {
			super("Pair values not equal: " + Pair.this);
		}
	}

	public void checkState() {
		if (x != y) {
			throw new PairValuesNotEqualException();
		}
	}
}
