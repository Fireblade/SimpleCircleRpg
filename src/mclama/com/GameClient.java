package mclama.com;

import java.io.IOException;
import java.util.HashMap;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import mclama.com.Network.AddCharacter;
import mclama.com.Network.Login;
import mclama.com.Network.OtherClient;

public class GameClient {
	
	private Client client;
	
	private String name;
	private long uid;
	
	HashMap<Integer, Character> characters = new HashMap();
	
	
	public GameClient (String name) {
		client = new Client();
	    client.start();
		
		Network.register(client);
		
		client.addListener(new ThreadedListener(new Listener() {
			public void connected (Connection connection) {
			}

			public void received (Connection connection, Object object) {
				if (object instanceof OtherClient) {
					System.out.println(((OtherClient) object).name + " has connected with userID: " + ((OtherClient) object).id);
				}
				if (object instanceof AddCharacter) {
					//AddCharacter msg = (AddCharacter)object;
					//characters.put(msg.character.id, msg.character);
					return;
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
