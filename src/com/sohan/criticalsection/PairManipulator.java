package com.sohan.criticalsection;

public class PairManipulator implements Runnable {

	private PairManager pm;

	PairManipulator(PairManager pm) {
		this.pm = pm;
	}

	@Override
	public void run() {
		while (true) {
			pm.increment();
		}
	}

	@Override
	public String toString() {
		return "Pair: " + pm.getPair() + " checkCounter= " + pm.checkCounter;
	}

}
