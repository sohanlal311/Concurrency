package com.sohan.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyCharacters {
	public static void main(String[] args) throws IOException {
		File inFile = new File("C:\\cpsweb.log");
		File outFile = new File("C:\\cpswebOUT.log");

		FileReader in = null;
		FileWriter out = null;

		try {
			in = new FileReader(inFile);
			outFile.createNewFile();
			out = new FileWriter(outFile);

			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
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