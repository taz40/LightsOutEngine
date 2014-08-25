package dummy;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import io.brace.lightsoutgaming.engine.LightsOut;
import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.*;

public class Main extends LightsOut {
	
	DatagramSocket socket;

	public static void main(String[] args){
		Main main = new Main();
		main.init();
	}
	
	public Main(){
		
	}

	@Override
	protected void render() {
		
	}

	@Override
	protected void update() {
		
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
			socket = NetInit();
			if(socket == null){
				System.err.println("could not bind to port");
				return;
			}
			boolean result = connect("localhost", 8080, "Taz40", "Test Dummy", socket);
			if(result){
				System.out.println("Success");
				createDisplay("Lights Out Engine 0.1 Network Test", 900, 600);
				start();
			}else{
				System.err.println("could not connect to server");
			}
	}
	
}
