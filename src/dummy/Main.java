package dummy;

import static io.brace.lightsoutgaming.engine.Network.NetworkUtils.*;
import io.brace.lightsoutgaming.engine.LightsOut;
import io.brace.lightsoutgaming.engine.Network.NetworkUtils;
import io.brace.lightsoutgaming.engine.graphics.Sprite;
import io.brace.lightsoutgaming.engine.graphics.SpriteSheet;

import java.net.DatagramSocket;

public class Main extends LightsOut {
	
	DatagramSocket socket;
	int ID;
	static SpriteSheet sheet = new SpriteSheet("/sheet.png");
	public static Sprite base = new Sprite(0,0,3,16,sheet);
	public static Sprite turet = new Sprite(1,0,3,16,sheet);

	public static void main(String[] args){
		Main main = new Main();
		main.init();
	}
	
	public Main(){
		
	}

	@Override
	protected void render(){
		screen.clear();
		for(int i = 0; i < networkObjects.size(); i++){
			networkObjects.get(i).render(screen);
		}
		for(int i = 0; i < myObjects.size(); i++){
			myObjects.get(i).render(screen);
		}
		show();
	}

	@Override
	protected void update() {
		for(int i = 0; i < myObjects.size(); i++){
			myObjects.get(i).update();
		}
		for(int i = 0; i < myObjects.size(); i++){
			sendObject(myObjects.get(i), serverIP, serverPort, socket);
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
		socket = NetworkUtils.NetInit();
		int id = NetworkUtils.connect("localhost", 8080, "Taz40", "Test Dummy", socket, this);
		createDisplay("Lights Out Engine 0.1 Network Test", 900, 600);
		NetworkUtils.createObject(TestObj.class, serverIP, serverPort, socket);
		start();
	}
	
}
