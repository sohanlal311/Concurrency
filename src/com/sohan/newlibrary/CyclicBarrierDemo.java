package com.sohan.newlibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {
	public static void main(String[] args) {
		new HorseRace(7, 100);
	}
}

class HorseRace {
	private static final int FINISH_LINE = 100;
	private final List<Horse> horses = new ArrayList<Horse>();
	private final ExecutorService exec = Executors.newCachedThreadPool();
	private final CyclicBarrier barrier;

	public HorseRace(int nHorses, final int pause) {
		barrier = new CyclicBarrier(nHorses, new Runnable() {
			public void run() {
				StringBuilder s = new StringBuilder();
				for (int i = 0; i < FINISH_LINE; i++) {
					s.append("="); // The fence on the race track
				}
				System.out.println(s);

				for (Horse horse : horses) {
					System.out.println(horse.tracks());
				}

				for (Horse horse : horses) {
					if (horse.getStrides() >= FINISH_LINE) {
						System.out.println(horse + " won!");
						exec.shutdownNow();
						return;
					}
				}
				try {
					TimeUnit.MILLISECONDS.sleep(pause);
				} catch (InterruptedException e) {
					System.out.println("barrier-action sleep interrupted");
				}
			}
		});

		for (int i = 0; i < nHorses; i++) {
			Horse horse = new Horse(barrier);
			horses.add(horse);
			exec.execute(horse);
		}
	}
}

class Horse implements Runnable {
	private static int count = 0;
	private final int id = count++;
	private int strides = 0;
	private static Random rand = new Random(2512);
	private final CyclicBarrier barrier;

	Horse(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	public synchronized int getStrides() {
		return strides;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					strides += rand.nextInt(3); // 0, 1 or 2
				}
				barrier.await();
			}
		} catch (InterruptedException e) {
			System.out.println("Acceptable way to exit");
		} catch (BrokenBarrierException e) {
			new RuntimeException(e);
		}
	}

	public String tracks() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < getStrides(); i++) {
			s.append("*");
		}
		return s.toString();
	}

	public String toString() {
		return "Horse " + id + " ";
	}
}