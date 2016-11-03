package com.sohan.io;

import java.io.Console;
import java.util.Arrays;

public class Password {

	public static void main(String[] args) {
		Console console = System.console();
		if (console == null) {
			System.err.println("Console not available.");
			System.exit(1);
		}

		String login = console.readLine("Enter your user name: ");
		char[] oldPaswd = console.readPassword("Enter your old password: ");

		if (verify(login, oldPaswd)) {
			boolean noMatch;
			do {
				char[] newPaswd1 = console
						.readPassword("Enter your new password: ");
				char[] newPaswd2 = console
						.readPassword("Enter new password again: ");
				noMatch = !Arrays.equals(newPaswd1, newPaswd2);
				if (noMatch) {
					console.format("Passwords don't match. Try again.%n");
				} else {
					changePaswd(login, newPaswd1);
					console.format("Password for %s changed.%n", login);
				}
				Arrays.fill(newPaswd1, ' ');
				Arrays.fill(newPaswd2, ' ');
			} while (noMatch);
		}

		Arrays.fill(oldPaswd, ' ');
	}

	private static boolean verify(String login, char[] oldPaswd) {
		// TODO Auto-generated method stub
		return true;
	}

	private static void changePaswd(String login, char[] newPaswd1) {
		// TODO Auto-generated method stub

	}

}
