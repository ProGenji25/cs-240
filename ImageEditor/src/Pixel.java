
public class Pixel {
	
	private int red, green, blue;
	
	public Pixel(int r, int g, int b) {
		this.red = r;
		this.green = g;
		this.blue = b;
	}
	
	public int getRed() {
		return red;
	}
	
	public int getGreen() {
		return green;
	}
	
	public int getBlue() {
		return blue;
	}
	
	public void setRGB(int r, int g, int b) {
		this.red = r;
		this.green = g;
		this.blue = b;
	}
	
	public void grayscale() {
		int average = (red + green + blue) / 3;
		this.red = average;
		this.green = average;
		this.blue = average;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(red);
		builder.append(" ");
		builder.append(green);
		builder.append(" ");
		builder.append(blue);
		builder.append("\n");
		return builder.toString();
	}
}