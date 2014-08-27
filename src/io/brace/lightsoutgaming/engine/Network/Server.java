package io.brace.lightsoutgaming.engine.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * used to start a server.
 * @author Taz40
 *
 */

public class Server implements Runnable {
	
	private DatagramSocket socket;
	private Thread recv;
	private Thread manage;
	private boolean single;
	private int port;
	private Thread run;
	private boolean running = false;
	private final String Game_ID;
	private ArrayList<ServerClient> clients = new ArrayList<ServerClient>();
	private ArrayList<Integer> responses = new ArrayList<Integer>();
	private static final int MAX_ATTEMPTS = 5;
	
	/**
	 * creates a server.
	 * @param single
	 * if the server is being used for single player or not.
	 * @param port
	 * the port to run the server on.
	 * @param Game_ID
	 * the game id. must be the same as the one used on client side. otherwise it will not connect.
	 */
	
	public Server(boolean single, int port, String Game_ID){
		this.Game_ID = Game_ID;
		this.single = single;
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		run = new Thread(this, "Server");
		run.start();
	}

	private String recv(){
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		
		try {
			socket.setSoTimeout(1000);
			socket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "false";
		}
		String message = new String(packet.getData());
		return message.split("/e/")[0];
	}
	
	private void send(final String s, final InetAddress ip, final int port){
		Thread send = new Thread("Send"){
		final String string = s + "/e/";
			public void run(){
				DatagramPacket packet = new DatagramPacket(string.getBytes(), string.getBytes().length, ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
	
	private void sendToAll(String msg){
		for(int i = 0; i < clients.size(); i++){
			ServerClient c = clients.get(i);
			send(msg, c.ip, c.port);
		}
	}
	
	private void sendToAllExcept(String msg, ServerClient client){
		for(int i = 0; i < clients.size(); i++){
			ServerClient c = clients.get(i);
			if(c != client)
			send(msg, c.ip, c.port);
		}
	}
	
	private void recvThread(){
		recv = new Thread("recv"){
			public void run(){
				while(running){
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String msg = new String(packet.getData());
					msg = msg.split("/e/")[0];
					if(msg.startsWith("/c/")){
						String[] info = msg.split("/");
						String name = info[2];
						String ID = info[3];
						int id = UniqueIdentifier.getIdentifier();
						if(ID.equals(Game_ID)){
							send("/c/"+id, packet.getAddress(), packet.getPort());
							clients.add(new ServerClient(name, packet.getPort(), packet.getAddress(), id));
							System.out.println(name + " connected");
						}else{
							System.out.println(name + " failed to connect");
						}
					}else if(msg.startsWith("/r/")){
						String[] info = msg.split("/r/");
						responses.add(Integer.parseInt(info[1]));
					}else if(msg.startsWith("/d/")){
						String[] info = msg.split("/d/");
						dissconnect(findWithID(Integer.parseInt(info[1])), true);
					}else if(msg.startsWith("/o/")){
						sendToAllExcept(msg,findByIP(packet.getAddress(), packet.getPort()));
					}else if(msg.equals("false")) continue;
					else System.out.println("Unknown MSG received");
				}
			}
		};
		recv.start();
	}
	
	public ServerClient findByIP(InetAddress ip, int port){
		for(int i = 0; i < clients.size(); i++){
			if(clients.get(i).ip.equals(ip) && clients.get(i).port == port){
				return clients.get(i);
			}
		}
		return null;
	}
	
	public ServerClient findWithID(int ID){
		for(int i = 0; i < clients.size(); i++){
			if(clients.get(i).ID == ID){
				return clients.get(i);
			}
		}
		return null;
	}
	
	private void manage(){
		manage = new Thread("Client Management"){
			public void run(){
				while(running){
					sendToAll("/p/");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int i = 0; i < clients.size(); i++){
						if(responses.contains(clients.get(i).ID)){
							responses.remove((Integer)clients.get(i).ID);
							clients.get(i).attempts = 0;
						}else{
							clients.get(i).attempts++;
							if(clients.get(i).attempts >= MAX_ATTEMPTS){
								dissconnect(clients.get(i), false);
							}
						}
					}
				}
			}
		};
		manage.start();
	}
	
	private void dissconnect(ServerClient client, boolean soft){
		clients.remove(client);
		if(soft){
			System.out.println(client.name + " dissconnected");
		}else{
			System.out.println(client.name + " Timed Out");
		}
	}
	
	/**
	 * DO NOT USE! for use with Threading ONLY!
	 */

	@Override
	public void run() {
		// TODO Auto-generated method stub
		running = true;
		recvThread();
		manage();
	}
	
}
