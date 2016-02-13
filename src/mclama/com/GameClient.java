package mclama.com;

import java.io.IOException;
import java.util.ArrayList;
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
import mclama.com.item.Item;

import static mclama.com.util.globals.*;
import static mclama.com.util.Artist.*;

public class GameClient {
	
	private Client client;
	
	private String name;
	private long uid;
	
	public Character myCharacter;
	
	//public static HashMap<Integer, Player> characters = new HashMap();
	public static ArrayList<Player> characters = new ArrayList<Player>();
	
	
	public GameClient (String name) {
		client = new Client();
	    client.start();
	    
	    gClient = client;
	    gameClient=this;
	    
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
					Player plyr = getCharacter(msg.id);
					if(plyr!= null)
						myPlayer = plyr;
				}
				if (object instanceof AddCharacter) {
					AddCharacter msg = (AddCharacter)object;
					Player plyr = new Player();
					//plyr.setTexture(tex_circle);
					{
						plyr.setId(msg.character.id);
						plyr.name = msg.character.name;
						plyr.setX(msg.character.x);
						plyr.setY(msg.character.y);
						characters.add(plyr);
					}
					System.out.println("Added character... " + msg.character.name + " ID: " + msg.character.id);
					return;
				}
				
				if (object instanceof MoveClickOrder){
					MoveClickOrder clickOrder = ((MoveClickOrder) object);
					System.out.println("Move order received by: " + clickOrder.id);
					if(!(((MoveClickOrder) object).id==myPlayer.getId())){ //if not self.
						getCharacter(clickOrder.id).movingClickOrder(clickOrder.x, clickOrder.y);
						
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
	
	public static Player getCharacter(int id){
		for(int i=0; i<GameClient.characters.size(); i++){
			Player plyr = GameClient.characters.get(i);
			if(plyr.getId()==id){
				return plyr;
			}
		}
		return null;
	}

}