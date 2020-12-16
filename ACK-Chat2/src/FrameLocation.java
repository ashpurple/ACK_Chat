import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;

import javax.swing.JFrame;


public class FrameLocation { //Frame의 윈도상의 위치를 지정.
	static public void setLocation(JFrame J)
	{
		GraphicsConfiguration gc = J.getGraphicsConfiguration();
		Rectangle bounds = gc.getBounds();  
		Dimension size = J.getPreferredSize(); 
		J.setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)),   
                 (int) ((bounds.height / 2) - (size.getHeight() / 2))); 
	}
}
