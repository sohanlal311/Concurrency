package com.sohan.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CopyLines {
	public static void main(String[] args) throws IOException {
		File inFile = new File("C:\\cpsweb.log");
		File outFile = new File("C:\\cpswebOUT.log");
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			in = new BufferedReader(new FileReader(inFile));
			out = new PrintWriter(new FileWriter(outFile));

			String line;
			while ((line = in.readLine()) != null) {
				out.println(line);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.flush(); // no effect unless it is buffered
				out.close();
			}
		}
	}
}
