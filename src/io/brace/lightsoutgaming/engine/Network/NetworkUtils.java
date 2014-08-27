package io.brace.lightsoutgaming.engine.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * a bunch of utilities used for networking.
 * @author Taz40
 *
 */

public class NetworkUtils {
	public static boolean running = false;
	public static InetAddress serverIP;
	public static int serverPort;
	public static ArrayList<Networked> networkObjects = new ArrayList<Networked>();
	/**
	 * listens for a message on the specified socket.
	 * @param socket
	 * the socket to listen on.
	 * @return
	 * the message.
	 */
	public static String recv(DatagramSocket socket){
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
	
	public static DatagramPacket recvp(DatagramSocket socket){
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		
		try {
			socket.setSoTimeout(1000);
			socket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return packet;
	}
	
	/**
	 * sends a message through the specified socket to the specified ip and port.
	 * @param msg
	 * the message to send.
	 * @param ip
	 * the ip to send it to.
	 * @param port
	 * the port to send it to.
	 * @param socket
	 * the socket to send it on.
	 */
	
	public static void send(final String msg, final InetAddress ip, final int port, final DatagramSocket socket){
		Thread send = new Thread("Send"){
		final String string = msg + "/e/";
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
	
	/**
	 * initializes the network for use.
	 * @return
	 * the Datagram Socket for network communication.
	 */
	
	public static DatagramSocket NetInit(){
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			return null;
		}
		return socket;
		
	}
	
	/**
	 * connects to a server
	 * @param ip
	 * the ip of the server.
	 * @param port
	 * the port of the server.
	 * @param name
	 * the name of the player connecting.
	 * @param Game_ID
	 * a string identifying the game. must be the same as the one used on the server side.
	 * @param socket
	 * the socket to use to connect.
	 * @return
	 * the id of the player. returns -1 if connection was unsuccessful.
	 */
	
	public static int connect(String ip, int port,String name , String Game_ID, final DatagramSocket socket){
		try {
			send("/c/"+name+"/"+Game_ID, InetAddress.getByName(ip), port, socket);
		} catch (UnknownHostException e) {
			return -1;
		}
		String response = recv(socket);
		if(response.equals("false")){
			return  -1;
		}
		try {
			serverIP = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverPort = port;
		running = true;
		String[] info = response.split("/c/");
		final int id = Integer.parseInt(info[1]);
		Thread netMan = new Thread("Network Manager"){
			public void run(){
				while(running){
					DatagramPacket packet = recvp(socket);
					String message;
					if(packet != null){
					message = new String(packet.getData());
					String msg = message.split("/e/")[0];
					if(msg.startsWith("/p/")){
						send("/r/"+id, packet.getAddress(), packet.getPort(), socket);
					}else if(msg.startsWith("/o/")){
						String[] info = msg.split("/o/");
						Class c = null;
						try {
							c = Class.forName(info[1]);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(Networked.class.isAssignableFrom(c)){
							try {
								networkObjects.add((Networked)c.newInstance());
							} catch (InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					}
				}
			}
		};
		netMan.start();
		return id;
	}
	
	public static void createObject(Class<?> c, InetAddress ip, int port, DatagramSocket s){
		send("/o/"+c.getName(), ip, port, s);
	}
}
