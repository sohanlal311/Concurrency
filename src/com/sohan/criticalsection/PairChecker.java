package com.sohan.criticalsection;

public class PairChecker implements Runnable {

	private PairManager pm;

	PairChecker(PairManager pm) {
		this.pm = pm;
	}

	@Override
	public void run() {
		while (true) {
			pm.checkCounter.incrementAndGet();
			pm.getPair().checkState();
		}
	}

}
