package io.brace.lightsoutgaming.engine.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen {
	
	private int width, height;
	public int xOffset, yOffset;
	public int[] pixels;
	
	private BufferedImage image;
	
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
	public void clear(int color){
		for(int i = 0; i < width*height; i++){
			pixels[i] = color;
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
		if(fixed){
			xp += xOffset;
			yp += yOffset;
		}
		for(int x = 0; x < sprite.width; x++){
			for(int y = 0; y < sprite.height; y++){
				if(x+xp >= width || x+xp < 0 || y+yp >= height || y+yp < 0) continue;
				int col = sprite.pixels[x+y*sprite.width];
				if(col != 0xffff00ff) pixels[(xp+x) + (yp+y) * width] = col;
			}
		}
	}
	
	public void clear(){
		clear(0);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void render(){
		
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
}
