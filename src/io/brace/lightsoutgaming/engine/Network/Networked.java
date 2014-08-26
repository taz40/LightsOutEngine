package io.brace.lightsoutgaming.engine.Network;

/**
 * is used to make an object networkable.
 * @author Taz40
 *
 */

public interface Networked {
	/**
	 * is called when the game is ready to send the object data accross the network.
	 * @return
	 * the data to send.
	 */
	public String[] send();
	/**
	 * is called when the game receives object data from across the network.
	 * @param data
	 * the received data.
	 */
	public void recv(String[] data);

}
