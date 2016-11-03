package com.sohan.newlibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DelayQueueDemo {
	public static void main(String[] args) {
		Random rand = new Random(311);
		DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();
		for (int i = 0; i < 5; i++) {
			queue.put(new DelayedTask(rand.nextInt(5000)));
		}
		ExecutorService exec = Executors.newCachedThreadPool();
		queue.put(new DelayedTask.EndSentinel(5000, exec));
		exec.execute(new DelayedTaskConsumer(queue));
	}
}

class DelayedTaskConsumer implements Runnable {
	private DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();

	public DelayedTaskConsumer(DelayQueue<DelayedTask> queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				queue.take().run();
			}
		} catch (InterruptedException e) {
			System.out.println("DelayedTaskConsumer interrupted");
		}
	}
}

class DelayedTask implements Runnable, Delayed {
	private static int count = 0;
	private final int id = count++;
	private final int delay;
	private final long trigger;
	private static List<DelayedTask> sequence = new ArrayList<DelayedTask>();

	DelayedTask(int delayInMilliseconds) {
		this.delay = delayInMilliseconds;
		this.trigger = System.nanoTime()
				+ TimeUnit.NANOSECONDS.convert(delayInMilliseconds,
						TimeUnit.MILLISECONDS);
		sequence.add(this);
	}

	public void run() {
		System.out.println(this + " ");
	}

	public int compareTo(Delayed obj) {
		DelayedTask that = (DelayedTask) obj;
		if (this.trigger < that.trigger)
			return -1;
		if (this.trigger > that.trigger)
			return 1;
		return 0;
	}

	public long getDelay(TimeUnit unit) {
		return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	public String toString() {
		return String.format("[%1$-4d]", delay) + " Task " + id;
	}

	public String summary() {
		return "(" + id + ":" + delay + ")";
	}

	public static class EndSentinel extends DelayedTask {
		private final ExecutorService exec;

		EndSentinel(int delayInMilliseconds, ExecutorService exec) {
			super(delayInMilliseconds);
			this.exec = exec;
		}

		public void run() {
			for (DelayedTask pt : sequence) {
				System.out.print(pt.summary());
			}
			System.out.println();
			System.out.println(this + " calling shutdown");
			exec.shutdownNow();
		}

	}

}