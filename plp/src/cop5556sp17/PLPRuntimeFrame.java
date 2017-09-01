package cop5556sp17;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
//import cop5555.runtime.Image;
import javax.swing.*;

@SuppressWarnings("serial")
public class PLPRuntimeFrame extends JFrame {
	
	static StringBuilder log;
	public static void setLog(StringBuilder log){
		PLPRuntimeFrame.log = log;
	};
	
	private ImageIcon icon;
	BufferedImage image;
	
	boolean verbose = true;
	
	static Component last = null;
	
	public final static String JVMClassName = "cop5556sp17/PLPRuntimeFrame";
	public final static String JVMDesc = "Lcop5556sp17/PLPRuntimeFrame;";

	
	/**
	 * Use this routine when the target of a BinaryChain is a frame.  It will instantiate a frame if
	 * necessary and set the image
	 * 
	 * @param i
	 * @return
	 */
	public final static String createOrSetFrameSig = "(" + PLPRuntimeImageIO.BufferedImageDesc + JVMDesc + ")" + JVMDesc;
	public static PLPRuntimeFrame createOrSetFrame(BufferedImage i, PLPRuntimeFrame f) {
		PLPRuntimeLog.globalLogAddEntry("createOrSetFrame");
		if (f == null) {
			return createFrame(i);
		} else
			f.setImage(i);
		return f;
	}
	

	private static PLPRuntimeFrame createFrame(BufferedImage i) {
		final PLPRuntimeFrame frame = new PLPRuntimeFrame(i);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.initialize();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return frame;
	}

	private PLPRuntimeFrame(BufferedImage image) {
		this.image = image;
	}

	// initializes frame for display
	private void initialize() {
		Container contentPane = getContentPane();
		icon = new ImageIcon();
		icon.setImage(image);
		contentPane.add(new JLabel(icon));
		pack();
		setLocationRelativeTo(last);  //initial location is centered.
		last = this;
	}

	
	
	public static final String moveFrameDesc = "(II)" + JVMDesc;
	public PLPRuntimeFrame moveFrame(final int x, final int y) {
		PLPRuntimeLog.globalLogAddEntry("moveFrame");		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					setLocation(x,y);
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return this;
	}

	public static String showImageDesc = "()" + JVMDesc;

	public PLPRuntimeFrame showImage() {
		PLPRuntimeLog.globalLogAddEntry("showImage");
		setVisible(true);
		return this;
	}

	public static String hideImageDesc = "()" + JVMDesc;

	public PLPRuntimeFrame hideImage() {
		PLPRuntimeLog.globalLogAddEntry("hideImage");
		setVisible(false);
		return this;
	}


	private void setImage(final BufferedImage image2) {
		PLPRuntimeLog.globalLogAddEntry("showImage");
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					BufferedImage image1 = image2;
					icon.setImage(image1);
					pack();
					repaint();
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}


	public static final String getXValDesc = "()I";
	public int getXVal(){
		PLPRuntimeLog.globalLogAddEntry("getX");
		return super.getX();
	}

	public static final String getYValDesc = "()I";
	public int getYVal(){
		PLPRuntimeLog.globalLogAddEntry("getY");
		return super.getY();
	}

	public final static String getScreenWidthSig = "()I";
	public static int getScreenWidth() {
		PLPRuntimeLog.globalLogAddEntry("getScreenWidth");
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	public final static String getScreenHeightSig = "()I";
	public static int getScreenHeight() {
		PLPRuntimeLog.globalLogAddEntry("getScreenHeight");
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}

}
