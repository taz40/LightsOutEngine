package io.brace.lightsoutgaming.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * is used to load sprite sheets into the game.
 * @author Taz40
 *
 */

public class SpriteSheet {
	/**
	 * the width of the sprite sheet.
	 */
	public int width;
	/**
	 * the height of the sprite sheet.
	 */
	public int height;
	/**
	 * the path of the sprite sheet's file.
	 */
	private String path;
	/**
	 * the pixel data of the sprite sheet.
	 */
	public int[] pixels;
	
	/**
	 * creates a sprite sheet form a path
	 * @param path
	 * the file path of the sprite sheet.
	 */
	
	public SpriteSheet(String path){
		this.path = path;
		load();
	}
	
	/**
	 * loads a sprite sheet's file to memory for use.
	 */
	
	public void load(){
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
