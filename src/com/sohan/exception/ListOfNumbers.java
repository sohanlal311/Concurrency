package com.sohan.exception;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListOfNumbers {
	private final List<Integer> arrayList;
	private final static int size = 10;

	public ListOfNumbers() {
		this(size);
	}

	public ListOfNumbers(int size) {
		arrayList = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			arrayList.add(new Integer(i));
		}
	}

	public void printList() {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("c:\\out.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Exception e;
		try {
			Handler fileHandler = new FileHandler("C:\\sohanlog.txt");
			Logger.getLogger("").addHandler(fileHandler);
			e = new Exception();
			throw e;
		} catch (Exception e1) {
			StackTraceElement[] stackTrace = e1.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement s = stackTrace[i];
				// System.out.println(s.getFileName() + " " + s.getClassName()
				// + " " + s.getLineNumber() + " " + s.getMethodName());

				Logger logger = Logger.getLogger("Sohanexception");
				logger.log(Level.WARNING, s.getMethodName());
			}
			// e1.printStackTrace();
		} finally {
			return;
		}
	}
}

class MyException extends RuntimeException {

}
