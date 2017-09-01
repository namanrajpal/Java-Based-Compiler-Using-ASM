package cop5556sp17;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

public class PLPRuntimeImageOps {

	static StringBuilder log;
	public static void setLog(StringBuilder log){
		PLPRuntimeFrame.log = log;
	};
	
	public static final String JVMName = "cop5556sp17/PLPRuntimeImageOps";
	public final static String getWidthSig = "()I";
	// use getWidth method of BufferedImage

	public final static String getHeightSig = "()I";
	// use getHeight method of BufferedImage

	public final static String scaleSig = "(" + PLPRuntimeImageIO.BufferedImageDesc + "I)" + PLPRuntimeImageIO.BufferedImageDesc;
	public static BufferedImage scale(BufferedImage image, int factor) {
		PLPRuntimeLog.globalLogAddEntry("scale");
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage scaledImage = new BufferedImage(w * factor, h * factor, image.getType());
		AffineTransform at = new AffineTransform();
		at.scale(factor, factor);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		scaledImage = op.filter(image, scaledImage);
		return scaledImage;
	}

	public static final String addSig = "(" + PLPRuntimeImageIO.BufferedImageDesc 
			+ PLPRuntimeImageIO.BufferedImageDesc + ")" + PLPRuntimeImageIO.BufferedImageDesc;
	public static BufferedImage add(BufferedImage i0, BufferedImage i1) {
		PLPRuntimeLog.globalLogAddEntry("add");
		int w0 = i0.getWidth();
		int w1 = i1.getWidth();
		int h0 = i0.getHeight();
		int h1 = i0.getHeight();
		int w = w0 <= w1 ? w0 : w1;
		int h = h0 <= h1 ? h0 : h1;
		BufferedImage dest = new BufferedImage(w,h,i0.getType());		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				Color c0 = new Color(i0.getRGB(j, i));
				Color c1 = new Color(i1.getRGB(j, i));
				int red = (int) (c0.getRed() + c1.getRed());
				int green = (int) (c0.getGreen() + c1.getGreen());
				int blue = (int) (c0.getBlue() + c1.getBlue());
				red = red > 255 ? 255 : (red < 0 ? 0 : red); // ensure in range
																// [0,255]
				green = green > 255 ? 255 : (green < 0 ? 0 : green);
				blue = blue > 255 ? 255 : (blue < 0 ? 0 : blue);
				Color newColor = new Color(red, green, blue);
				dest.setRGB(j, i, newColor.getRGB());
			}
		}
		return dest;
	}

	public static final String subSig = "(" + PLPRuntimeImageIO.BufferedImageDesc 
			+ PLPRuntimeImageIO.BufferedImageDesc + ")" + PLPRuntimeImageIO.BufferedImageDesc;	
	public static BufferedImage sub(BufferedImage i0, BufferedImage i1) {
		PLPRuntimeLog.globalLogAddEntry("sub");
		int w0 = i0.getWidth();
		int w1 = i1.getWidth();
		int h0 = i0.getHeight();
		int h1 = i0.getHeight();
		int w = w0 <= w1 ? w0 : w1;
		int h = h0 <= h1 ? h0 : h1;
		BufferedImage dest = new BufferedImage(w,h,i0.getType());
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				Color c0 = new Color(i0.getRGB(j, i));
				Color c1 = new Color(i1.getRGB(j, i));
				int red = (int) (c0.getRed() - c1.getRed());
				int green = (int) (c0.getGreen() - c1.getGreen());
				int blue = (int) (c0.getBlue() - c1.getBlue());
				red = red > 255 ? 255 : (red < 0 ? 0 : red); // ensure in range
																// [0,255]
				green = green > 255 ? 255 : (green < 0 ? 0 : green);
				blue = blue > 255 ? 255 : (blue < 0 ? 0 : blue);
				Color newColor = new Color(red, green, blue);
				dest.setRGB(j, i, newColor.getRGB());
			}
		}
		return dest;
	}
	
	public static final String mulSig = "(" + PLPRuntimeImageIO.BufferedImageDesc 
			+ "I" + ")" + PLPRuntimeImageIO.BufferedImageDesc;
	public static BufferedImage mul(BufferedImage i0, int factor) {
		PLPRuntimeLog.globalLogAddEntry("mul");
		int w = i0.getWidth();
		int h = i0.getHeight();
		BufferedImage dest = new BufferedImage(w,h,i0.getType());		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				Color c0 = new Color(i0.getRGB(j, i));
				int red = (int) (c0.getRed() * factor);
				int green = (int) (c0.getGreen() * factor);
				int blue = (int) (c0.getBlue() * factor);
				red = red > 255 ? 255 : (red < 0 ? 0 : red); // ensure in range
																// [0,255]
				green = green > 255 ? 255 : (green < 0 ? 0 : green);
				blue = blue > 255 ? 255 : (blue < 0 ? 0 : blue);
				Color newColor = new Color(red, green, blue);
				dest.setRGB(j, i, newColor.getRGB());
			}
		}
		return dest;
	}

	public static final String divSig = "(" + PLPRuntimeImageIO.BufferedImageDesc 
			+ "I" + ")" + PLPRuntimeImageIO.BufferedImageDesc;
	public static BufferedImage div(BufferedImage i0, int divisor) {
		PLPRuntimeLog.globalLogAddEntry("div");		
		int w = i0.getWidth();
		int h = i0.getHeight();
		BufferedImage dest = new BufferedImage(w,h,i0.getType());
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				Color c0 = new Color(i0.getRGB(j, i));
				int red = (int) (c0.getRed() / divisor);
				int green = (int) (c0.getGreen() / divisor);
				int blue = (int) (c0.getBlue() / divisor);
				red = red > 255 ? 255 : (red < 0 ? 0 : red); // ensure in range
																// [0,255]
				green = green > 255 ? 255 : (green < 0 ? 0 : green);
				blue = blue > 255 ? 255 : (blue < 0 ? 0 : blue);
				Color newColor = new Color(red, green, blue);
				dest.setRGB(j, i, newColor.getRGB());
			}
		}
		return dest;
	}

	public static final String modSig = "(" + PLPRuntimeImageIO.BufferedImageDesc 
			+ "I" + ")" + PLPRuntimeImageIO.BufferedImageDesc;
	public static BufferedImage mod(BufferedImage i0, int divisor) {
		PLPRuntimeLog.globalLogAddEntry("mod");
		int w = i0.getWidth();
		int h = i0.getHeight();
		BufferedImage dest = new BufferedImage(w,h,i0.getType());
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				Color c0 = new Color(i0.getRGB(j, i));
				int red = (int) (c0.getRed() % divisor);
				int green = (int) (c0.getGreen() % divisor);
				int blue = (int) (c0.getBlue() % divisor);
				red = red > 255 ? 255 : (red < 0 ? 0 : red); // ensure in range
																// [0,255]
				green = green > 255 ? 255 : (green < 0 ? 0 : green);
				blue = blue > 255 ? 255 : (blue < 0 ? 0 : blue);
				Color newColor = new Color(red, green, blue);
				dest.setRGB(j, i, newColor.getRGB());
			}
		}
		return dest;
	}
	
	public static final String copyImageSig = "("+PLPRuntimeImageIO.BufferedImageDesc+")"+PLPRuntimeImageIO.BufferedImageDesc; 
	public static BufferedImage copyImage(BufferedImage source){
		PLPRuntimeLog.globalLogAddEntry("copyImage");
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics2D g = (Graphics2D) b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
}
