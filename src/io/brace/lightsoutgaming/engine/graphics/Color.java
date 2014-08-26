package io.brace.lightsoutgaming.engine.graphics;

/**
 * used to convert colors.
 * 
 * @author Taz40
 *
 */

public class Color {
	
	private int color;
	
	/**
	 * takes hexadecimal color 
	 * 
	 * @param hex
	 * the color.
	 */

	public Color(int hex){
		this.color = hex;
	}
	
	/**
	 * takes RGB color
	 * @param r
	 * the red channel
	 * @param g
	 * the green channel
	 * @param b
	 * the blue channel
	 */
	
	public Color(int r, int g, int b){
		this.color = getHex(r, g, b);
	}
	
	/**
	 * returns the RGB form of the color.
	 * @param hex
	 * the color.
	 * @return
	 * an RGB array, from the color.
	 */
	
	public static int[] getRGB(int hex){
		int r = (hex & 0xff0000) >> 16;
		int g = (hex & 0xff00) >> 8;
		int b = (hex & 0xff);
		return new int[]{r,g,b};
	}
	
	/**
	 * returns the RGBA form of the color
	 * @param hex
	 * the color.
	 * @return
	 * the RGBA array, from the color.
	 */
	
	public static int[] getRGBA(int hex){
		int r = (hex & 0xff0000) >> 16;
		int g = (hex & 0xff00) >> 8;
		int b = (hex & 0xff);
		int a = (hex & 0xff000000) >> 24;
		return new int[]{r,g,b,a};
	}
	
	/**
	 * returns the Hexadecimal form of the color
	 * @param r
	 * the red channel.
	 * @param g
	 * the green channel.
	 * @param b
	 * the blue channel.
	 * @return
	 * the hexadecimal color.
	 */
	
	public static int getHex(int r, int g, int b){
		return r << 16 | g << 8 | b;
	}
	
	/**
	 * returns the Hexadecimal form of the color.
	 * @param r
	 * the red channel.
	 * @param g
	 * the green channel.
	 * @param b
	 * the blue channel.
	 * @param a
	 * the alpha channel.
	 * @return
	 * the hexadecimal color.
	 */
	
	public static int getHex(int r, int g, int b, int a){
		return a << 24 | r << 16 | g << 8 | b;
	}// comment
	
	/**
	 * returns the color as Hexadecimal.
	 * @return
	 * the Hexadecimal color.
	 */
	
	public int getColor(){
		return color;
	}
	
}
