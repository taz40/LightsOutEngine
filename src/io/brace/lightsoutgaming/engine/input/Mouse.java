package io.brace.lightsoutgaming.engine.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * used to track mouse pos, and button state.
 * 
 * @author Taz40
 */
import java.awt.event.MouseListener;

public class Mouse implements MouseListener, MouseMotionListener{
	/**
	 * the X position of the mouse.
	 */
	public static int mouseX;
	/**
	 * the Y position of the mouse.
	 */
	public static int mouseY;
	/**
	 * the X position of the mouse last click.
	 */
	public static int clickX;
	/**
	 * the Y position of the mouse last click.
	 */
	public static int clickY;
	/**
	 * the currently clicked mouse button.
	 */
	public static int button;
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		button = arg0.getButton();
		clickX = arg0.getX();
		clickY = arg0.getY();
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		button = 0;
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseX = arg0.getX();
		mouseY = arg0.getY();
	}
	
}
