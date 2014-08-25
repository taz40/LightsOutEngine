package io.brace.lightsoutgaming.engine.Network;

public interface Networked {
	
	public String[] send();
	public void recv(String[] data);

}
