package io.brace.lightsoutgaming.engine.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server implements Runnable {
	
	DatagramSocket socket;
	Thread recv;
	boolean single;
	int port;
	Thread run;
	public boolean running = false;
	public final String Game_ID;
	
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
						if(ID.equals(Game_ID)){
							send("/c/", packet.getAddress(), packet.getPort());
							System.out.println(name + " connected");
						}else{
							System.out.println(name + " failed to connect");
						}
					}else if(msg.equals("false")) continue;
					else System.out.println("Unknown MSG received");
				}
			}
		};
		recv.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		running = true;
		recvThread();
	}
	
}
