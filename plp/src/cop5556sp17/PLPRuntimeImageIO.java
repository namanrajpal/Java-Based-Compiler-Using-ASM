package cop5556sp17;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class PLPRuntimeImageIO{

	public final static String className = "cop5556sp17/PLPRuntimeImageIO";
	public final static String desc = "Lcop5556sp17/PLPRuntimeImageIO;";
	public static final String StringDesc = "Ljava/lang/String;";
	public static final String BufferedImageDesc = "Ljava/awt/image/BufferedImage;";
	public static final String BufferedImageClassName = "java/awt/image/BufferedImage";
	public final static String StringArrayDesc = "[Ljava/lang/String;";
	public final static String FileDesc = "Ljava/io/File;";
	public final static String URLDesc = "Ljava/net/URL;";


	public static final String getURLSig = "("+ StringArrayDesc + "I)" + URLDesc; 
	public static URL getURL(String[] args, int index) {
		PLPRuntimeLog.globalLogAddEntry("getURL("+args[index] + ")");
		URL url;
		try {
			url = new URL(args[index]);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return url;
	}

	public static final String readFromFileDesc = "(" + FileDesc + ")" + BufferedImageDesc;
	
	public static BufferedImage readFromFile(File f) {
		//System.out.println("\n\n\n\n...opening file");
		PLPRuntimeLog.globalLogAddEntry("readFromFile("+f+")");
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return bi;
	}

	public static final String writeImageDesc = "(" +  BufferedImageDesc 
			+ FileDesc + ")" + BufferedImageDesc;
	public static BufferedImage write(BufferedImage image, File f) {
		PLPRuntimeLog.globalLogAddEntry("write("+f+")");
		try {
			ImageIO.write(image, "jpg", f);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return image;
	}

	public final static String readFromURLSig = "(Ljava/net/URL;)" + BufferedImageDesc;
	public static BufferedImage readFromURL(URL url) {
		PLPRuntimeLog.globalLogAddEntry("readFromURL("+url+")");
		try {
			System.out.println("reading image from url " + url);
			return ImageIO.read(url);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}



}
