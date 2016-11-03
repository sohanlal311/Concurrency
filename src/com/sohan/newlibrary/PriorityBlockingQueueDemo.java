package com.sohan.newlibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueDemo {
	public static void main(String[] args) {
		PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new PrioritizedTaskConsumer(queue));
		exec.execute(new PrioritizedTaskProducer(queue, exec));
	}
}

class PrioritizedTaskConsumer implements Runnable {
	private PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();

	public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				queue.take().run();
			}
		} catch (InterruptedException e) {
			System.out.println("PrioritizedTaskConsumer interrupted");
		}
	}
}

class PrioritizedTaskProducer implements Runnable {
	private final Random rand = new Random(2512);
	private final Queue<Runnable> queue;
	private final ExecutorService exec;

	public PrioritizedTaskProducer(Queue<Runnable> queue, ExecutorService exec) {
		this.queue = queue;
		this.exec = exec;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
			queue.add(new PrioritizedTask(rand.nextInt(10)));
			Thread.yield();
		}

		try {
			for (int i = 0; i < 10; i++) {
				TimeUnit.MILLISECONDS.sleep(250);
				queue.add(new PrioritizedTask(10));
			}

			for (int i = 0; i < 10; i++) {
				queue.add(new PrioritizedTask(i));
			}
			queue.add(new PrioritizedTask.EndSentinel(exec));
		} catch (InterruptedException e) {
			System.out.println("PrioritizedTask interrupted");
		}
	}
}

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
	private static int count = 0;
	private final int id = count++;
	private final int priority;
	private final Random rand = new Random(311);
	private static List<PrioritizedTask> sequence = new ArrayList<PrioritizedTask>();

	PrioritizedTask(int priority) {
		this.priority = priority;
		sequence.add(this);
	}

	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
			System.out.println(this + " ");
		} catch (InterruptedException e) {
			System.out.println("Run interrupted");
		}
	}

	public int compareTo(PrioritizedTask that) {
		if (this.priority < that.priority)
			return 1;
		if (this.priority > that.priority)
			return -1;
		return 0;
	}

	public String toString() {
		return String.format("[%1$-4d]", priority) + " Task " + id;
	}

	public String summary() {
		return "(" + id + ":" + priority + ")";
	}

	public static class EndSentinel extends PrioritizedTask {
		private final ExecutorService exec;

		EndSentinel(ExecutorService exec) {
			super(-1);
			this.exec = exec;
		}

		public void run() {
			int count = 0;
			for (PrioritizedTask pt : sequence) {
				System.out.print(pt.summary());
				if (++count % 5 == 0)
					System.out.println();
			}
			System.out.println();
			System.out.println(this + " calling shutdown");
			exec.shutdownNow();
		}

	}

}