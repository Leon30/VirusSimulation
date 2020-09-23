package simulacion;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class DoubleBuffer extends JPanel{

	private static final long serialVersionUID = 1L;

	public DoubleBuffer(){
        super();
    }
	
	private int bufferWidth;
	private int bufferHeight;
	private Image bufferImage;
	private Graphics bufferGraphics;

	public void paint(final Graphics g){
		if(bufferWidth!=getSize().width ||
				bufferHeight!=getSize().height ||
				bufferImage==null || bufferGraphics==null)
			resetBuffer();
		
		resetBuffer();

	    if(bufferGraphics!=null){
	        //this clears the offscreen image, not the onscreen one
	        bufferGraphics.clearRect(0,0,bufferWidth,bufferHeight);

	        //calls the paintbuffer method with
	        //the offscreen graphics as a param
	        paintBuffer(bufferGraphics);

	        //we finaly paint the offscreen image onto the onscreen image
	        g.drawImage(bufferImage,0,0,this);
	    }
	}

	private void resetBuffer(){
		bufferWidth=getSize().width;
		bufferHeight=getSize().height;

		if(bufferGraphics!=null){
			bufferGraphics.dispose();
			bufferGraphics=null;
		}
		if(bufferImage!=null){
			bufferImage.flush();
			bufferImage=null;
		}
		System.gc();

		bufferImage=createImage(bufferWidth,bufferHeight);
		bufferGraphics=bufferImage.getGraphics();
	}
	
	public void paintBuffer(Graphics g){
	}
}
