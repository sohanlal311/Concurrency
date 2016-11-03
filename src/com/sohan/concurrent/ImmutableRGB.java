package com.sohan.concurrent;

public final class ImmutableRGB {
	private final int red;
	private final int green;
	private final int blue;
	private final String name;

	public ImmutableRGB(int red, int green, int blue, String name) {
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

	// public void setRGB(int red, int green, int blue, String name) {
	// check(red, green, blue);
	// synchronized (this) {
	// this.red = red;
	// this.green = green;
	// this.blue = blue;
	// this.name = name;
	// }
	// }

	public int getRGB() {
		return red << 16 | green << 8 | blue;
	}

	public String getName() {
		return name;
	}

	public ImmutableRGB invert() {
		return new ImmutableRGB(255 - red, 255 - green, 255 - blue,
				"Inverse of " + name);
	}

	public static void main(String[] args) {
		ImmutableRGB color = new ImmutableRGB(0, 0, 0, "Pitch Black");
		synchronized (color) {
			int rgb = color.getRGB();
			System.out.println(rgb);
			String name = color.getName();
			System.out.println(name);
		}
	}
}
