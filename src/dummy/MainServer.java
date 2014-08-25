package dummy;

import io.brace.lightsoutgaming.engine.Network.Server;

public class MainServer {
	
	public MainServer(){
		Server s = new Server(false, 8080, "Test Dummy");
	}
	
	public static void main(String[] args){
		new MainServer();
	}

}
