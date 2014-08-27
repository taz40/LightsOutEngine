package io.brace.lightsoutgaming.engine.graphics;

import io.brace.lightsoutgaming.engine.input.Mouse;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * is used to interface with the JFrame window in a more user friendly way.
 * @author Taz40
 *
 */

public class Window extends Canvas {
	
	private JFrame frame;
	private int width, height;
	private String title;
	
	/**
	 * creates a window.
	 * @param title
	 * the title of the window.
	 * @param width
	 * the width of the window.
	 * @param height
	 * the height of the window.
	 */
	public Window(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		create();
	}
	
	private void create(){
		this.setPreferredSize(new Dimension(width, height));
		frame = new JFrame(title);
		frame.setResizable(false);
		this.addMouseListener(new Mouse());
		this.addMouseMotionListener(new Mouse());
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * @return
	 * the width of the window.
	 */

	public int getWidth(){
		return width;
	}
	
	/**
	 * @return
	 * the height of the window.
	 */
	
	public int getHeight(){
		return height;
	}
}
