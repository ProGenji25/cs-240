import java.util.*;

public class Image {
	
	private int width, height;
	public Pixel[][] pixels;
	
	public Image(int w, int h) {
		this.width = w;
		this.height = h;
		this.pixels = new Pixel[w][h];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void addPixel(int x, int y, int r, int g, int b) {
		pixels[x][y] = new Pixel(r, g, b);
	}

	public void grayscale() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				
				pixels[x][y].grayscale();
			}
		}
	}

	public void invert() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				pixels[x][y].setRGB(255 - pixels[x][y].getRed(), 255 - pixels[x][y].getGreen(), 255 - pixels[x][y].getBlue());
			}
		}
	}

	public void emboss() {
		for(int y = height - 1; y >= 0; y--){
			for(int x = width - 1; x >= 0; x--){
				int value = 0;
				if(x - 1 < 0 || y - 1 < 0){
					value = 128;
					pixels[x][y].setRGB(value,value,value);
				}
				else{
					int redDiff = pixels[x][y].getRed()-pixels[x-1][y-1].getRed();
					int greenDiff = pixels[x][y].getGreen()-pixels[x-1][y-1].getGreen();
					int blueDiff = pixels[x][y].getBlue()-pixels[x-1][y-1].getBlue();
					ArrayList<Integer> newArray = new ArrayList<Integer>();
					newArray.add(redDiff);
					newArray.add(greenDiff);
					newArray.add(blueDiff);
					int max = Collections.max(newArray);
					int min = Collections.min(newArray);
					if(Math.abs(min) > Math.abs(max)){
						value = min + 128;
					}
					else if(Math.abs(min) < Math.abs(max)){
						value = max + 128;
					}
					else if (Math.abs(min) == Math.abs(max)){
						if(max == redDiff || min == redDiff){
							value = redDiff + 128;
						}
						else if(max == greenDiff || min == greenDiff){
							value = greenDiff + 128;
						}
						else{
							value = blueDiff + 128;
						}
					}
					
					if(value > 255){
						value = 255;
					}
					else if(value < 0){
						value = 0;
					}
					pixels[x][y].setRGB(value,value,value);
				}
			}
		}	
	}

	public void motionblur(int n) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int newRed = 0;
				int newGreen = 0;
				int newBlue = 0;
				int num = 0;
				for(int i = 0; i < n; i++) {
					if(x + num < width) {
						newRed += pixels[x + i][y].getRed();
						newGreen += pixels[x + i][y].getGreen();
						newBlue += pixels[x + i][y].getBlue();
						num++;
					}
				}
				pixels[x][y].setRGB((newRed / num), (newGreen / num), (newBlue / num));
			}
		}
		
	}
	
	public String output() {
		StringBuilder builder = new StringBuilder();
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++){
				builder.append(pixels[x][y].toString());
			}
		}
		return builder.toString();
	}
}