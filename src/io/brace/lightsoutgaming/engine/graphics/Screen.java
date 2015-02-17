package io.brace.lightsoutgaming.engine.graphics;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
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
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int x = 0; x < sprite.width; x++){
			for(int y = 0; y < sprite.height; y++){
				if(x+xp >= width || x+xp < 0 || y+yp >= height || y+yp < 0) continue;
				int col = sprite.pixels[x+y*sprite.width];
				if(col != 0xffff00ff) pixels[(xp+x) + (yp+y) * width] = col;
			}
		}
	}
	
	public void renderString(int xp, int yp, String txt, java.awt.Color c, boolean fixed){
		renderString(xp, yp, txt, c, image.getGraphics().getFont(), fixed);
	}
	
	public void renderString(int xp, int yp, String txt, java.awt.Color c, Font font, boolean fixed){
		renderSprite(xp, yp, stringToSprite(txt, font, c), fixed);
	}
	
	public Font getFont(){
		return image.getGraphics().getFont();
	}
	
	Sprite stringToSprite(String txt, Font font, java.awt.Color c){
		FontMetrics metrics = image.getGraphics().getFontMetrics(font);
		int height = metrics.getHeight();
		int width = metrics.stringWidth(txt);
		Sprite s = new Sprite(width+2,height+2);
		BufferedImage img = new BufferedImage(width+2, height+2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(java.awt.Color.decode("0xff00ff"));
		g.fillRect(0, 0, width+2, height+2);
		g.setFont(font);
		g.setColor(c);
		g.drawString(txt, 2, height-2);
		s.pixels = img.getRGB(0, 0, width+2, height+2, null, 0, width+2);
		return s;
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
