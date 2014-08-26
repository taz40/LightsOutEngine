package io.brace.lightsoutgaming.engine.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen {
	
	private int width, height;
	/**
	 * the xOffset of the screen.
	 * Normally used for player movement.
	 */
	public int xOffset;
	/**
	 * the yOffset of the screen.
	 * Normally used for player movement.
	 */
	public int yOffset;
	/**
	 * the pixels of the screen.
	 */
	public int[] pixels;
	
	private BufferedImage image;
	
	/**
	 * creates a new screen.
	 * @param width
	 * the width of the screen.
	 * @param height
	 * the height of the screen.
	 */
	
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
	/**
	 * clears the screen in the specified color.
	 * @param color
	 * the color to clear the screen with.
	 */
	
	public void clear(int color){
		for(int i = 0; i < width*height; i++){
			pixels[i] = color;
		}
	}
	
	/**
	 * renders a sprite to the screen.
	 * @param xp
	 * the x position of the sprite.
	 * @param yp
	 * the y position of the sprite.
	 * @param sprite
	 * the sprite.
	 * @param fixed
	 * if true the sprite will stick to the level.
	 * if false it will move with the player.
	 */
	
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
	
	/**
	 * clears the screen black.
	 */
	
	public void clear(){
		clear(0);
	}
	
	/**
	 * 
	 * @return
	 * the width of the screen.
	 */
	
	public int getWidth(){
		return width;
	}
	
	/**
	 * 
	 * @return
	 * the height of the screen.
	 */
	
	public int getHeight(){
		return height;
	}
	
	//public void render(){
	//	
	//}
	
	/**
	 * @return
	 * the screens image data.
	 */
	
	public BufferedImage getImage(){
		return image;
	}
	
}
