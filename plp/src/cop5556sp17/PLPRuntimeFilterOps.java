package cop5556sp17;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class PLPRuntimeFilterOps {
	
	public static final String JVMName = "cop5556sp17/PLPRuntimeFilterOps";
	public static final String JVMDesc = "Lcop5556sp17/PLPRuntimeFilterOps;";
	
	static final float ninth = 1.0f/9.0f;
	static 	final float[] blurKernel = {
			ninth, ninth, ninth,
			ninth, ninth, ninth,
			ninth, ninth, ninth
	};
	static final float[] sharpenKernel = {
			0.0f, -1.0f, 0.0f,
			-1.0f, 5.0f, -1.0f,
			0.0f, -1.0f, 0.0f
	};
	
	/**
	 * Returns blurred version of source image in dest image. 
	 * If dest is null, a new BufferedImage object is created and returned.
	 * 
	 * Precondition:  source != dest.
	 * 
	 * @param image
	 * @param dest
	 * @return
	 */
	public static BufferedImage blurOp(BufferedImage image, BufferedImage dest){
		PLPRuntimeLog.globalLogAddEntry("blurOp");
		BufferedImageOp op = new ConvolveOp(new Kernel(3,3,blurKernel));
		return op.filter(image, dest);
	}
	public final static String opSig = "(" + PLPRuntimeImageIO.BufferedImageDesc + PLPRuntimeImageIO.BufferedImageDesc + ")" + PLPRuntimeImageIO.BufferedImageDesc;
	
	/**
	 * Returns sharpened version of source image in dest image. 
	 * If dest is null, a new BufferedImage object is created and returned.
	 * 
	 * Precondition:  source != dest.
	 * 
	 * @param image
	 * @param dest
	 * @return
	 */
	public static BufferedImage convolveOp(BufferedImage image, BufferedImage dest){
		PLPRuntimeLog.globalLogAddEntry("convolve");
		BufferedImageOp op = new ConvolveOp(new Kernel(3,3,sharpenKernel));
		return op.filter(image, dest);
	}
	
	
	/**
	 * Returns a gray scale version of source image in dest image.
	 * If dest is null, a new BufferedImage object is created and returned.
	 * 
	 * @param image
	 * @param dest
	 * @return
	 */
	public static BufferedImage  grayOp(BufferedImage image, BufferedImage dest){
		PLPRuntimeLog.globalLogAddEntry("grayOp");
		int w = image.getWidth();
		int h = image.getHeight();
		if (dest == null){
			   	ColorModel destColorModel = image.getColorModel();   	
			    dest = new BufferedImage(destColorModel, destColorModel.createCompatibleWritableRaster(w, h), destColorModel.isAlphaPremultiplied(), null);
		}
        for(int i=0; i<h; i++){            
            for(int j=0; j<w; j++){            
               Color c = new Color(image.getRGB(j, i));
               int red = (int)(c.getRed() * 0.299);
               int green = (int)(c.getGreen() * 0.587);
               int blue = (int)(c.getBlue() *0.114);
               Color newColor = new Color(red+green+blue,         
               red+green+blue,red+green+blue);
               dest.setRGB(j,i,newColor.getRGB());
            }
         }
        return dest;
	}
}
