package io.brace.lightsoutgaming.engine.Network;

import io.brace.lightsoutgaming.engine.Entity;

/**
 * is used to make an object networkable.
 * @author Taz40
 *
 */

public abstract class Networked extends Entity {
	
	int ID = -1;
	public String classname;
	
	/**
	 * is called when the game is ready to send the object data accross the network.
	 * @return
	 * the data to send.
	 */
	public abstract String[] send();
	/**
	 * is called when the game receives object data from across the network.
	 * @param data
	 * the received data.
	 */
	public abstract void recv(String[] data);

}
