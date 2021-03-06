package io.brace.lightsoutgaming.engine.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * used to start a server.
 * @author Taz40
 *
 */

public class Server implements Runnable {
	
	private DatagramSocket socket;
	private Thread recv;
	private Thread console;
	private Thread manage;
	private boolean single;
	private boolean singleconnect = false;
	private int port;
	private Thread run;
	private boolean running = false;
	private final String Game_ID;
	public ArrayList<ServerClient> clients = new ArrayList<ServerClient>();
	private ArrayList<Integer> responses = new ArrayList<Integer>();
	private ArrayList<Networked> networkObjects = new ArrayList<Networked>();
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
			socket = new DatagramSocket(this.port);
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
					String msg = "null";
					try {
						socket.setSoTimeout(1000);
						socket.receive(packet);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						msg = "false";
					}
					if(!msg.equals("false")){
						String message = new String(packet.getData());
						msg = message.split("/e/")[0];
					}
					if(msg.startsWith("/c/")){
						if(!single || !singleconnect){
						String[] info = msg.split("/");
						String name = info[2];
						String ID = info[3];
						int id = UniqueIdentifier.getIdentifier();
						if(ID.equals(Game_ID)){
							send("/c/"+id, packet.getAddress(), packet.getPort());
							clients.add(new ServerClient(name, packet.getPort(), packet.getAddress(), id));
							System.out.println(name + " connected");
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							for(int i = 0; i < networkObjects.size(); i++){
								send("/o/"+networkObjects.get(i).classname + "/" + networkObjects.get(i).ID, packet.getAddress(), packet.getPort());
							}
						}else{
							System.out.println(name + " failed to connect");
						}
						if(single){
							singleconnect = true;
						}
						}
					}else if(msg.startsWith("/r/")){
						String[] info = msg.split("/r/");
						responses.add(Integer.parseInt(info[1]));
					}else if(msg.startsWith("/d/")){
						String[] info = msg.split("/d/");
						dissconnect(findWithID(Integer.parseInt(info[1])), true);
					}else if(msg.startsWith("/o/")){
						int oid = UniqueIdentifier.getIdentifier();
						ServerClient sender = findByIP(packet.getAddress(), packet.getPort());
						String[] info = msg.split("/o/");
						sendToAllExcept("/o/"+info[1]+"/"+oid, sender);
						send("/oid/"+info[1]+"/"+oid, sender.ip, sender.port);
						Class c = null;
						System.out.println("received instantiate packet");
						try {
							c = Class.forName(info[1]);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							System.out.println("can not find class");
						}
						if(Networked.class.isAssignableFrom(c)){
							try {
								Networked n = (Networked)c.newInstance();
								n.classname = info[1];
								n.ID = oid;
								n.cratorid = sender.ID;
								networkObjects.add(n);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println("can not instantiate");
							}
						}
					}else if(msg.startsWith("/u/")){
						int oid = UniqueIdentifier.getIdentifier();
						ServerClient sender = findByIP(packet.getAddress(), packet.getPort());
						String[] info = msg.split("/o/");
						sendToAllExcept(msg, sender);
					}else if(msg.equals("false")) continue;
					else System.out.println("Unknown MSG received");
					System.out.println(networkObjects.size());
				}
			}
		};
		recv.start();
		console = new Thread("console cmd"){
			public void run(){
				Scanner s = new Scanner(System.in);
				while(running){
					String cmd = s.nextLine();
					if(cmd.equals("stop")){
						running = false;
						try {
							manage.join();
							recv.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//sendToAll("/s/");
						break;
					}
				}
			}
		};
		console.start();
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
	
	private void removeNetworkObject(Networked n){
		networkObjects.remove(n);
		this.sendToAll("/ro/"+n.ID);
	}
	
	private void dissconnect(ServerClient client, boolean soft){
		clients.remove(client);
		for(int i = 0; i < this.networkObjects.size(); i++){
			if(networkObjects.get(i).cratorid == client.ID){
				removeNetworkObject(networkObjects.get(i));
			}
		}
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
