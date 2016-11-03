package com.sohan.newlibrary;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerDemo {
	public static void main(String[] args) throws Exception {
		Exchanger<List<Fat>> ex = new Exchanger<List<Fat>>();
		List<Fat> producerHolder = new CopyOnWriteArrayList<Fat>();
		List<Fat> consumerHolder = new CopyOnWriteArrayList<Fat>();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new ExchangerConsumer<Fat>(ex, consumerHolder));
		exec.execute(new ExchangerProducer<Fat>(ex, producerHolder,
				new Generator<Fat>(Fat.class), 25));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}

class ExchangerProducer<T> implements Runnable {
	private final Exchanger<List<T>> ex;
	private final Generator<T> gen;
	private final int size;
	private List<T> holder;

	ExchangerProducer(Exchanger<List<T>> ex, List<T> holder, Generator<T> gen,
			int size) {
		this.ex = ex;
		this.holder = holder;
		this.gen = gen;
		this.size = size;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				for (int i = 0; i < size; i++) {
					holder.add(gen.next());
				}
				holder = ex.exchange(holder);
			}
		} catch (Exception e) {
			System.out.println("Exception in ExchangerProducer " + e);
		}
	}
}

class ExchangerConsumer<T> implements Runnable {
	private final Exchanger<List<T>> ex;
	private List<T> holder;

	ExchangerConsumer(Exchanger<List<T>> ex, List<T> holder) {
		this.ex = ex;
		this.holder = holder;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				holder = ex.exchange(holder);
				for (T t : holder) {
					holder.remove(t);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in ExchangerConsumer " + e);
		}
	}
}

class Generator<T> {
	private final Class<T> classObject;

	Generator(Class<T> classObject) {
		this.classObject = classObject;
	}

	public T next() throws Exception {
		return classObject.newInstance();
	}
}