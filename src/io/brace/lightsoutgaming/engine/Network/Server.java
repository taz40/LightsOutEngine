package io.brace.lightsoutgaming.engine.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Server implements Runnable {
	
	DatagramSocket socket;
	Thread recv;
	Thread manage;
	boolean single;
	int port;
	Thread run;
	public boolean running = false;
	public final String Game_ID;
	ArrayList<ServerClient> clients = new ArrayList<ServerClient>();
	ArrayList<Integer> responses = new ArrayList<Integer>();
	public static final int MAX_ATTEMPTS = 5;
	
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
	
	public void sendToAll(String msg){
		for(int i = 0; i < clients.size(); i++){
			ServerClient c = clients.get(i);
			send(msg, c.ip, c.port);
		}
	}
	
	public void recvThread(){
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
					}else if(msg.equals("false")) continue;
					else System.out.println("Unknown MSG received");
				}
			}
		};
		recv.start();
	}
	
	public void manage(){
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
							responses.remove(clients.get(i).ID);
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
	
	public void dissconnect(ServerClient client, boolean soft){
		clients.remove(client);
		if(soft){
			System.out.println(client.name + " dissconnected");
		}else{
			System.out.println(client.name + " Timed Out");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		running = true;
		recvThread();
		manage();
	}
	
}
