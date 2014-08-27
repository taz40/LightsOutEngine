package dummy;

import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.networkObjects;
import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.serverIP;
import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.serverPort;
import io.brace.lightsoutgaming.engine.LightsOut;
import io.brace.lightsoutgaming.engine.Network.NetworkUtils;
import io.brace.lightsoutgaming.engine.Network.Server;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.engine.graphics.SpriteSheet;

import java.net.DatagramSocket;

public class Main extends LightsOut {
	
	DatagramSocket socket;
	int ID;
	static SpriteSheet sheet = new SpriteSheet("/sheet.png");
	public static Sprite sprite = new Sprite(0,0, 3, 16,16,sheet);

	public static void main(String[] args){
		Main main = new Main();
		main.init();
	}
	
	public Main(){
		
	}

	@Override
	protected void render(){
		for(int i = 0; i < networkObjects.size(); i++){
			networkObjects.get(i).render(screen);
		}
	}

	@Override
	protected void update() {
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		DatagramSocket s = NetworkUtils.NetInit();
		int id = NetworkUtils.connect("localhost", 8080, "Taz401", "Test Dummy", s);
		createDisplay("Lights Out Engine 0.1 Network Test", 900, 600);
		NetworkUtils.createObject(TestObj.class, serverIP, serverPort, s);
		start();
	}
	
}
