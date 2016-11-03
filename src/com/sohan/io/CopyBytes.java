package com.sohan.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyBytes {
	public static void main(String[] args) throws IOException {
		File inFile = new File("C:\\cpsweb.log");
		File outFile = new File("C:\\cpswebOUT.log");

		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			in = new FileInputStream(inFile);
			outFile.createNewFile();
			out = new FileOutputStream(outFile);

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
