package dummy;

import io.brace.lightsoutgaming.engine.LightsOut;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.engine.graphics.SpriteSheet;

import java.awt.Font;
import java.net.DatagramSocket;

public class Main extends LightsOut {
	
	DatagramSocket socket;
	int ID;
	static SpriteSheet sheet = new SpriteSheet("/sheet.png");
	public static Sprite base = new Sprite(0,0,3,16,sheet);
	public static Sprite turet = new Sprite(1,0,3,16,sheet);

	public static void main(String[] args){
		Main main = new Main();
		main.init();
	}
	
	public Main(){
		
	}

	@Override
	protected void render(){
		screen.clear(0xffffff);
		Font f = new Font(screen.getFont().getFontName(), Font.BOLD, 20);
		screen.renderString(10, 10, "Hello World!", f, true);
		show();
	}

	@Override
	protected void update() {
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		createDisplay("Lights Out Engine 0.1 String Test", 900, 600);
		start();
	}
	
}
