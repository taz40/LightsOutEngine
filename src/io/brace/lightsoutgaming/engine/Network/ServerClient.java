package io.brace.lightsoutgaming.engine.Network;

import java.net.InetAddress;

/**
 * used to help the server keep track of its clients.
 * @author Taz40
 *
 */

public class ServerClient {
	
	/**
	 * the port of the client.
	 */
	public int port;
	/**
	 * the ip of the client.
	 */
	public InetAddress ip;
	/**
	 * the client's ID.
	 */
	public final int ID;
	/**
	 * the name of the client.
	 */
	public String name;
	/**
	 * the number of unsuccessful ping attempts the server has made in a row.
	 */
	public int attempts = 0;
	
	/**
	 * creates a new ServerClient.
	 * @param name
	 * the name of the client.
	 * @param port
	 * the port of the client.
	 * @param ip
	 * the ip of the client.
	 * @param ID
	 * the ID of the client.
	 */
	
	public ServerClient(String name, int port, InetAddress ip, int ID){
		this.name = name;
		this.port = port;
		this.ip = ip;
		this.ID = ID;
	}
	
}
