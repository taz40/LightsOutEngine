package io.brace.lightsoutgaming.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	public static boolean[] keys = new boolean[500];

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		keys[arg0.getID()] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		keys[arg0.getID()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static boolean getKey(int code){
		return keys[code];
	}

}
