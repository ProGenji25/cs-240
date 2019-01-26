import java.io.*;
import java.util.*;

public class ImageEditor {
	
	Image i;
	ImageEditor(){}
	
	public static void main(String args[]) {
		
		ImageEditor editor = new ImageEditor();
		editor.load(args[0]);
		if (args[2].equals("grayscale")){
			editor.i.grayscale();
		}
		else if (args[2].equals("invert")){
			editor.i.invert();
		}
		else if (args[2].equals("emboss")){
			editor.i.emboss();
		}
		else if (args[2].equals("motionblur")) {
			if (Integer.parseInt(args[3]) > 0) {
				editor.i.motionblur(Integer.parseInt(args[3]));
			}
		}
		editor.save(args[1]);
	}
	
	private void load(String file) {
		try {
			FileReader fr = new FileReader(file);
			Scanner s = new Scanner(fr);
			s.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");
			s.next();
			int width = Integer.parseInt(s.next());
			int height = Integer.parseInt(s.next());
			this.i = new Image(width,height);
			s.next();
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
					int r = Integer.parseInt(s.next());
					int g = Integer.parseInt(s.next());
					int b = Integer.parseInt(s.next());
					this.i.addPixel(x,y,r,g,b);
				}
			}
			s.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void save(String file) {
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			StringBuilder sb = new StringBuilder();
			sb.append("P3\n");
			sb.append(this.i.getWidth());
			sb.append("\n");
			sb.append(this.i.getHeight());
			sb.append("\n");
			sb.append("255");
			pw.println(sb.toString());
			pw.print(this.i.output());
			pw.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}	
	}
}