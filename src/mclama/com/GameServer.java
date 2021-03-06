package mclama.com;

import java.io.IOException;
import java.util.HashSet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import mclama.com.Network.*;
import mclama.com.entity.Player;
import mclama.com.level.Level;

import static mclama.com.util.Globals.*;

public class GameServer {
	
	private Server server;
	private HashSet<Character> loggedIn = new HashSet();
	
	private int total_users_connected = 0;
	private int total_levels_created = 0;

	
	public GameServer () throws IOException {
		server = new Server() {
			protected Connection newConnection() {
				return new CharacterConnection();
			}
		};
		Network.register(server);
		
		server.addListener(new Listener() {
			public void received (Connection c, Object object) {
				// We know all connections for this server are actually CharacterConnections.
				CharacterConnection connection = (CharacterConnection)c;
				Character character = connection.character;

				if (object instanceof SendDamageDealt) {
					SendDamageDealt msg = (SendDamageDealt) object;
					//return
					server.sendToAllUDP(msg);
				}
				
				if (object instanceof OtherClient){
					((OtherClient)object).id = setCharacterID();
					server.sendToAllTCP(object);
				}
				
				if (object instanceof MoveClickOrder){
					server.sendToAllTCP(object);
				}
				
				if (object instanceof Login) {
					// Ignore if already logged in.
					if (character != null) return;

					// Reject if the name is invalid.
					String name = ((Login)object).name;
					if (!isValid(name)) {
						c.close();
						return;
					}

					// Reject if already logged in.
					for (Character other : loggedIn) {
						if (other.name.equals(name)) {
							c.close();
							return;
						}
					}

					character = loadCharacter(name);

					// Reject if couldn't load character.
					if (character == null) {
						System.out.println("Failed to load char");
						return;
					}

					loggedIn(connection, character);
					return;
				}

				if (object instanceof MoveCharacter) {
					// Ignore if not logged in.
					if (character == null) return;

					MoveCharacter msg = (MoveCharacter)object;

					// Ignore if invalid move.
					if (Math.abs(msg.x) != 1 && Math.abs(msg.y) != 1) return;

					character.x += msg.x;
					character.y += msg.y;

					UpdateCharacter update = new UpdateCharacter();
					update.id = character.id;
					update.x = character.x;
					update.y = character.y;
					server.sendToAllTCP(update);
					return;
				}
			}

			private boolean isValid (String value) {
				if (value == null) return false;
				value = value.trim();
				if (value.length() == 0) return false;
				return true;
			}

			public void disconnected (Connection c) {
				CharacterConnection connection = (CharacterConnection)c;
				if (connection.character != null) {
					loggedIn.remove(connection.character);

					RemoveCharacter removeCharacter = new RemoveCharacter();
					removeCharacter.id = connection.character.id;
					server.sendToAllTCP(removeCharacter);
				}
			}
		});
		server.bind(Network.TCPport, Network.UDPport);
		gServer = server;
		gameServer=this;
		server.start();
	}
	
	public int createNewLevel(){
		return createNewLevel(gen.nextLong());
	}
	
	public int createNewLevel(long seed){
		total_levels_created++;
		
		
		SendNewLevelSeed newLevelSeed = new SendNewLevelSeed();
		System.out.println("sent seed with " + seed);
		newLevelSeed.seed = seed;
		newLevelSeed.id = total_levels_created;
		server.sendToAllUDP(newLevelSeed);
		
		return total_levels_created;
	}
	

	protected int setCharacterID() {
		total_users_connected++ ;
		return total_users_connected;
	}


	protected Character loadCharacter(String name) {
		Character character = new Character();
		character.id = 0;
		character.name = name;
		character.x = 64;
		character.y = 64;
		return character;
	}


	void loggedIn (CharacterConnection c, Character character) {
		c.character = character;
		character.id = setCharacterID();
		
		// Add existing characters to new logged in connection.
		for (Character other : loggedIn) {
			AddCharacter addCharacter = new AddCharacter();
			other = getCharacterData(other);
			addCharacter.character = other;
			c.sendUDP(addCharacter);
		}

		loggedIn.add(character);

		// Add logged in character to all connections.
		AddCharacter addCharacter = new AddCharacter();
		addCharacter.character = character;
		//addCharacter.myId = character.id;
		
		server.sendToAllTCP(addCharacter);
		
		SendPlayersCharacter sendChar = new SendPlayersCharacter();
		sendChar.id = character.id;
		c.sendUDP(sendChar);
	}
	
	private Character getCharacterData(Character other){
		for(int i=0; i<GameClient.characters.size(); i++){
			Player plyr = GameClient.characters.get(i);
			if(plyr.getId()==other.id)
			{
				other.x = plyr.getX();
				other.y = plyr.getY();
				other.id = plyr.getId();
			}
		}
		return other;
	}
	
	public void updateCharacter(int id, double x, double y){
		for (Character other : loggedIn) {
			if(other.id==id){
				other.x=x;
				other.y=y;
				break;
			}
		}
	}
	
	static class CharacterConnection extends Connection {
		public Character character;
	}
	
	public int connectedClients(){
		return loggedIn.size();
	}

}
