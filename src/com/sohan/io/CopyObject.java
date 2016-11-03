package com.sohan.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CopyObject {

	public static void main(String[] args) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("c:\\monu.txt")));
		try {
			oos.writeObject(new Room());
		} finally {
			oos.flush();
			oos.close();
		}
	}

}

class Room implements Serializable {
	Table[] tables = new Table[5];

	Room() {
		tables[0] = new Table();
	}
}

class Table implements Serializable {

}