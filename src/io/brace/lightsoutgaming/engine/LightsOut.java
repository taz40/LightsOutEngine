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
	
	protected final void start(){
		loop = new Thread(this,"Game Loop");
		running = true;
		loop.start();
	}
	
	protected void createDisplay(String name, int width, int height){
		display = new Display(new Window(name, width, height));
		screen = new Screen(width, height);
	}
	
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
	
	protected final void show(){
		display.drawImage(screen.getImage());
		display.show();
	}
	
	protected abstract void init();
	protected abstract void render();
	protected abstract void update();
	
}
