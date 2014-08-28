package io.brace.lightsoutgaming.engine;

import io.brace.lightsoutgaming.engine.graphics.Screen;

public abstract class Entity {
	
	protected int x, y;

	public abstract void update();
	public abstract void render(Screen s);
	
}
