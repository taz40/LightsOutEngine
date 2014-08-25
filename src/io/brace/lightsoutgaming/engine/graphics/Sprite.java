package io.brace.lightsoutgaming.engine.graphics;

public class Sprite {

	public int width, height;
	private SpriteSheet sheet;
	public int[] pixels;
	public int scale;
	
	public Sprite(int x, int y, int scale, int width, int height, SpriteSheet sheet){
		pixels = new int[(width*scale)*(height*scale)];
		this.sheet = sheet;
		this.width = width*scale;
		this.height = height*scale;
		this.scale = scale;
		for(int xa = 0; xa < width; xa++){
			for(int ya = 0; ya < height; ya++){
				for(int xi = 0; xi < scale; xi++){
					for(int yi = 0; yi < scale; yi++){
						pixels[((xa*scale)+xi)+((ya*scale)+yi)*(width*scale)] = sheet.pixels[((x*width)+xa)+((y*height)+ya)*sheet.width];
					}
				}
			}
		}
	}
	
	public Sprite(){
		
	}
	
	public Sprite(int x, int y, int size, SpriteSheet sheet){
		this(x, y, 1, size, size, sheet);
	}
	
	public Sprite(int x, int y, int scale, int size, SpriteSheet sheet){
		this(x, y, scale, size, size, sheet);
	}
	
	public Sprite(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
	}
	
	public Sprite rotate(double angle){
		Sprite s = new Sprite(width, height);
		s.pixels = rotate(angle, pixels, width, height);
		for(int i = 0; i < pixels.length; i++){
			if(s.pixels[i] == 16777215) s.pixels[i] = 0xffff00ff;
		}
		return s;
	}
	
	private static int[ ] rotate( double angle, int[ ] pixels, int width, int height ) {
		final double radians = Math.toRadians( angle );
		final double cos = Math.cos( radians );
		final double sin = Math.sin( radians );
		final int[ ] pixels2 = new int[ pixels.length ];
		for( int pixel = 0; pixel < pixels2.length; pixel++ ) {
			pixels2[ pixel ] = 0xFFFFFF;
		}
		for( int x = 0; x < width; x++ ) {
			for( int y = 0; y < height; y++ ) {
				final int centerx = width / 2;
				final int centery = height / 2;
				final int m = x - centerx;
				final int n = y - centery;
				final int j = ( ( int ) ( m * cos + n * sin ) ) + centerx;
				final int k = ( ( int ) ( n * cos - m * sin ) ) + centery;
				if( j >= 0 && j < width && k >= 0 && k < height ){
					pixels2[ ( y * width + x ) ] = pixels[ ( k * width + j ) ];
				}
			}
		}
		return pixels2;
	}

	
}
