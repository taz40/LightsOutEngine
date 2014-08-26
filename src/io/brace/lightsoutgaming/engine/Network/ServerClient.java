package io.brace.lightsoutgaming.engine.Network;

import java.net.InetAddress;

public class ServerClient {

	public int port;
	public InetAddress ip;
	public final int ID;
	public String name;
	public int attempts = 0;
	
	public ServerClient(String name, int port, InetAddress ip, int ID){
		this.name = name;
		this.port = port;
		this.ip = ip;
		this.ID = ID;
	}
	
}
