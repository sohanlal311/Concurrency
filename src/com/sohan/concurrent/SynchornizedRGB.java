package com.sohan.concurrent;

public class SynchornizedRGB {
	private int red;
	private int green;
	private int blue;
	private String name;

	public SynchornizedRGB(int red, int green, int blue, String name) {
		check(red, green, blue);
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.name = name;
	}

	private void check(int red, int green, int blue) {
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0
				|| blue > 255) {
			throw new IllegalArgumentException();
		}
	}

	public void setRGB(int red, int green, int blue, String name) {
		check(red, green, blue);
		synchronized (this) {
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.name = name;
		}
	}

	public synchronized int getRGB() {
		return red << 16 | green << 8 | blue;
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void invert() {
		red = 255 - red;
		green = 255 - green;
		blue = 255 - blue;
		name = "Inverse of " + name;
	}

	public static void main(String[] args) {
		SynchornizedRGB color = new SynchornizedRGB(0, 0, 0, "Pitch Black");
		synchronized (color) {
			int rgb = color.getRGB();
			System.out.println(rgb);
			String name = color.getName();
			System.out.println(name);
		}
	}
}
