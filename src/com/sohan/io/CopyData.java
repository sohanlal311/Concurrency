package com.sohan.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyData {
	private static String fileName = "C:\\myData.txt";
	private static String[] products = { "Java T-Shirts", "Java Mug",
			"Java Pin" };
	private static double[] prices = { 23.34, 56.43, 89.56 };
	private static int[] units = { 14, 16, 27 };

	public static void main(String[] args) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(fileName)));
		try {
			for (int i = 0; i < products.length; i++) {
				out.writeDouble(prices[i]);
				out.writeInt(units[i]);
				out.writeUTF(products[i]);
			}
		} finally {
			out.flush();
			out.close();
		}

		DataInputStream in = new DataInputStream(new BufferedInputStream(
				new FileInputStream(fileName)));
		try {
			while (true) {
				System.out.println(in.readDouble());
				System.out.println(in.readInt());
				System.out.println(in.readUTF());
			}
		} catch (EOFException e) {
			System.out.format("#################%nEOF reached");
		} finally {
			in.close();
		}
	}

}
