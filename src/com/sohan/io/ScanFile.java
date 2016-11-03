package com.sohan.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ScanFile {

	public static void main(String[] args) throws IOException {
		Scanner s = null;

		try {
			s = new Scanner(new BufferedReader(new FileReader("C:\\sohan.txt")));
			s.useDelimiter(",\\s*");
			while (s.hasNext()) {
				System.out.println(s.next());
			}
		} finally {
			if (s != null) {
				s.close();
			}
		}

	}

}
