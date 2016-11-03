package com.sohan.producerconsumer;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PipedIO {
	public static void main(String[] args) throws Exception {
		Sender sender = new Sender();
		Receiver receiver = new Receiver(sender);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(sender);
		exec.execute(receiver);
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}

class Sender implements Runnable {
	private PipedWriter writer = new PipedWriter();
	private final Random rand = new Random(47);

	public PipedWriter getWriter() {
		return writer;
	}

	public void run() {
		try {
			while (true) {
				for (char ch = 'A'; ch <= 'z'; ch++) {
					writer.write(ch);
					TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
				}
			}
		} catch (IOException e) {
			System.out.println("Sender write exception." + e);
		} catch (InterruptedException e) {
			System.out.println("Sender interrupted." + e);
		}
	}
}

class Receiver implements Runnable {
	private final PipedReader reader;

	Receiver(Sender sender) throws IOException {
		reader = new PipedReader(sender.getWriter());
	}

	public void run() {
		try {
			while (true) {
				System.out.println((char) reader.read());
			}
		} catch (IOException e) {
			System.out.println("Receiver read exception." + e);
		}
	}
}