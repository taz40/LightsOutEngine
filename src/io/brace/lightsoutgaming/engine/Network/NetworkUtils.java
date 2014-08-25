package io.brace.lightsoutgaming.engine.Network;

import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.recv;
import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.send;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetworkUtils {
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
	
	public static void send(final String s, final InetAddress ip, final int port, final DatagramSocket socket){
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
	
	public static DatagramSocket NetInit(){
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			return null;
		}
		return socket;
	}
	
	public static boolean connect(String ip, int port,String name , String Game_ID, DatagramSocket socket){
		try {
			send("/c/"+name+"/"+Game_ID, InetAddress.getByName(ip), port, socket);
		} catch (UnknownHostException e) {
			return false;
		}
		String response = recv(socket);
		if(response.equals("false")){
			return  false;
		}
		return true;
	}
}
