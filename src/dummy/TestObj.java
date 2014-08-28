package dummy;

import java.awt.event.KeyEvent;

import io.brace.lightsoutgaming.engine.Network.Networked;
import io.brace.lightsoutgaming.engine.graphics.Screen;
import io.brace.lightsoutgaming.engine.input.Keyboard;

public class TestObj extends Networked {

	double angle = 0;

	@Override
	public void update() {
		// TODO Auto-generated method stub
		angle += Math.toDegrees(0.1f);
		System.out.println(Keyboard.getKey(KeyEvent.VK_W));
		if(Keyboard.getKey(KeyEvent.VK_W)){
			y--;
		}
		if(Keyboard.getKey(KeyEvent.VK_S)){
			y++;
		}
		if(Keyboard.getKey(KeyEvent.VK_A)){
			x--;
		}
		if(Keyboard.getKey(KeyEvent.VK_D)){
			x++;
		}
	}

	@Override
	public void render(Screen screen) {
		// TODO Auto-generated method stub
		screen.renderSprite(x, y, Main.base, false);
		screen.renderSprite(x, y, Main.turet.rotate(angle), false);
	}

	@Override
	public String[] send() {
		// TODO Auto-generated method stub
		String[] result = new String[3];
		result[0] = ""+x;
		result[1] = ""+y;
		result[2] = ""+angle;
		return result;
	}

	@Override
	public void recv(String[] data) {
		// TODO Auto-generated method stub
		x = Integer.parseInt(data[0]);
		y = Integer.parseInt(data[1]);
		angle = Double.parseDouble(data[2]);
	}
	
	

}
