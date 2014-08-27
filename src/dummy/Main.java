package dummy;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import io.brace.lightsoutgaming.engine.LightsOut;
import io.brace.lightsoutgaming.engine.input.Mouse;
import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.*;

public class Main extends LightsOut {
	
	DatagramSocket socket;
	int ID;

	public static void main(String[] args){
		Main main = new Main();
		main.init();
	}
	
	public Main(){
		
	}

	@Override
	protected void render(){
		
	}

	@Override
	protected void update() {
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		createDisplay("Lights Out Engine 0.1 Network Test", 900, 600);
		start();
	}
	
}
