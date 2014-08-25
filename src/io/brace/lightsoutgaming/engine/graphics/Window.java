package io.brace.lightsoutgaming.engine.graphics;

import io.brace.lightsoutgaming.engine.input.Mouse;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {
	
	private JFrame frame;
	private int width, height;
	private String title;
	
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
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.addMouseListener(new Mouse());
		frame.setVisible(true);
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
