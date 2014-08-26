package io.brace.lightsoutgaming.engine;

import io.brace.lightsoutgaming.engine.graphics.Display;
import io.brace.lightsoutgaming.engine.graphics.Screen;
import io.brace.lightsoutgaming.engine.graphics.Window;

/**
 * 
 * {@code Lights Out} engine template class.
 * 
 * @author Taz40
 * 
 */
public abstract class LightsOut implements Runnable {
	
	private boolean running = false;
	protected Screen screen;
	private Display display;
	private Thread loop;
	
	/**
	 * Starts the main game loop.
	 * Should be called after createDisplay()
	 */
	
	protected final void start(){
		loop = new Thread(this,"Game Loop");
		running = true;
		loop.start();
	}
	
	/**
	 * Creates the java window and sets up for display.
	 * should be called before start()
	 * 
	 * @param name
	 * The title of the window.
	 * @param width
	 * The width of the window.
	 * @param height
	 * The height of the window.
	 */
	
	protected void createDisplay(String name, int width, int height){
		display = new Display(new Window(name, width, height));
		screen = new Screen(width, height);
	}
	
	/**
	 * DO NOT CALL! used for threading only!
	 */
	
	public void run(){
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		long lasttime = System.nanoTime();
		while(running){
			long now = System.nanoTime();
			delta += (now-lasttime) /ns;
			lasttime = now;
			while(delta > 1){
				delta--;
				update();
			}
			render();
		}
	}
	
	/**
	 * call at end of render. prints buffer to screen.
	 */
	
	protected final void show(){
		display.drawImage(screen.getImage());
		display.show();
	}
	
	/**
	 * call from main method to start the game.
	 */
	
	protected abstract void init();
	/**
	 * called every render frame.
	 */
	
	protected abstract void render();
	/**
	 * called every update.
	 */
	protected abstract void update();
	
}
