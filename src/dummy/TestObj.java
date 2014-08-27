package dummy;

import io.brace.lightsoutgaming.engine.Network.Networked;
import io.brace.lightsoutgaming.engine.graphics.Screen;

public class TestObj implements Networked {

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Screen s) {
		// TODO Auto-generated method stub
		s.renderSprite(0, 0, Main.sprite, false);
	}

	@Override
	public String[] send() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recv(String[] data) {
		// TODO Auto-generated method stub
		
	}
	
	

}
