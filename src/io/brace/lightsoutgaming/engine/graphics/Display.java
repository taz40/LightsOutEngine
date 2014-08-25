package io.brace.lightsoutgaming.engine.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Display extends Canvas{

	private double scale = 1;
	
	private Window window;
	private Graphics g;
	private BufferStrategy bs;
	
	public Display(Window window){
		this.window = window;
		window.createBufferStrategy(3);
		bs = window.getBufferStrategy();
	}
	
	public void setScale(double scale){
		this.scale = scale;
	}
	
	public void drawImage(BufferedImage image){
		g = bs.getDrawGraphics();
		g.drawImage(image,0, 0, window.getWidth(), window.getHeight(), null);
	}
	
	public void show(){
		g.dispose();
		bs.show();
	}
	
}
