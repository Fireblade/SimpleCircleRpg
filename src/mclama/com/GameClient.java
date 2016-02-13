package mclama.com;

import java.io.IOException;
import java.util.HashMap;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import mclama.com.Network.AddCharacter;
import mclama.com.Network.Login;
import mclama.com.Network.MoveClickOrder;
import mclama.com.Network.OtherClient;
import mclama.com.Network.sendPlayersCharacter;
import mclama.com.entity.Player;

import static mclama.com.util.globals.*;

public class GameClient {
	
	private Client client;
	
	private String name;
	private long uid;
	
	public Character myCharacter;
	
	public static HashMap<Integer, Player> characters = new HashMap();
	
	
	public GameClient (String name) {
		client = new Client();
	    client.start();
	    
	    gClient = client;
	    
	    this.name = name;
		
		Network.register(client);
		
		client.addListener(new ThreadedListener(new Listener() {
			public void connected (Connection connection) {
			}

			public void received (Connection connection, Object object) {
				if (object instanceof OtherClient) {
					System.out.println(((OtherClient) object).name + " has connected with userID: " + ((OtherClient) object).id);
				}
				if (object instanceof sendPlayersCharacter) {
					System.out.println("received my character");
					sendPlayersCharacter msg = (sendPlayersCharacter)object;
					Player plyr = characters.get(msg.id);
					if(plyr!= null)
						myPlayer = plyr;
				}
				if (object instanceof AddCharacter) {
					AddCharacter msg = (AddCharacter)object;
					Player plyr = new Player();
					
//					if(msg.character.name.equals(name)){ //First connection means its the host.
//						myCharacter = msg.character;
//						myPlayer.setId(1);
//						//myPlayer.name = msg.character.name;
//						characters.put(1, myPlayer);
//						System.out.println("first connection");
//					}else
					{
						plyr.setId(msg.character.id);
						plyr.name = msg.character.name;
						characters.put(msg.character.id, plyr);
						System.out.println("added other char... " + plyr.name);
					}
					System.out.println("Added character... " + msg.character.name + " ID: " + msg.character.id);
					return;
				}
				
				if (object instanceof MoveClickOrder){
					MoveClickOrder clickOrder = ((MoveClickOrder) object);
					System.out.println("Move order received by: " + clickOrder.id);
					if(!(((MoveClickOrder) object).id==myPlayer.getId())){ //if not self.
						characters.get(clickOrder.id).movingClickOrder(clickOrder.x, clickOrder.y);
						
					}
				}
				
			}

			public void disconnected (Connection connection) {
				System.exit(0);
			}
		}));
		
		try {
			client.connect(5000, "localhost", Network.TCPport, Network.UDPport);
			
			Login login = new Login();
			login.name = name;
			client.sendTCP(login);
			
//			OtherClient oc = new OtherClient();
//			oc.name = name;
//			client.sendTCP(oc);
			// Server communication after connection can go here, or in Listener#connected().
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
