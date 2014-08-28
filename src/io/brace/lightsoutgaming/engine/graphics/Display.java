package io.brace.lightsoutgaming.engine.graphics;

import io.brace.lightsoutgaming.engine.input.Keyboard;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * works with the buffered image and interfaces to the Window.
 * @author Taz40
 *
 */

public class Display extends Canvas{
	
	/**
	 * the scale of the window.
	 */

	private double scale = 1;
	
	/**
	 * the window.
	 */
	
	private Window window;
	/**
	 * the Graphics for darwing to the Buffer Strategy.
	 */
	private Graphics g;
	/**
	 * the buffer strategy linked to the window.
	 */
	private BufferStrategy bs;
	
	/**
	 * creates a Display object from a window.
	 * 
	 * @param window
	 * the window of choice.
	 */
	
	public Display(Window window){
		this.window = window;
		window.createBufferStrategy(3);
		bs = window.getBufferStrategy();
	}
	
	/**
	 * sets the scale of the window.
	 * @param scale
	 * the scale to set
	 */
	
	public void setScale(double scale){
		this.scale = scale;
	}
	
	/**
	 * draws the image to the window.
	 * @param image
	 * the image to draw.
	 */
	
	public void drawImage(BufferedImage image){
		g = bs.getDrawGraphics();
		g.drawImage(image,0, 0, window.getWidth(), window.getHeight(), null);
	}
	
	/**
	 * displays the Buffer Stragegy.
	 */
	
	public void show(){
		g.dispose();
		bs.show();
	}
	
}
