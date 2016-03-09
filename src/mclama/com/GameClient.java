package mclama.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import mclama.com.Network.AddCharacter;
import mclama.com.Network.Login;
import mclama.com.Network.MonsterAttackOrder;
import mclama.com.Network.MoveClickOrder;
import mclama.com.Network.OtherClient;
import mclama.com.Network.RemoveCharacter;
import mclama.com.Network.SendDamageDealt;
import mclama.com.Network.SendNewLevelSeed;
import mclama.com.Network.SendPlayersCharacter;
import mclama.com.entity.Entity;
import mclama.com.entity.Monster;
import mclama.com.entity.Player;
import mclama.com.item.Item;
import mclama.com.level.Level;

import static mclama.com.util.Globals.*;
import static mclama.com.util.Artist.*;

public class GameClient {
	
	private Client client;
	
	private String name;
	private long uid;
	
	public Character myCharacter;
	
	//public static HashMap<Integer, Player> characters = new HashMap();
	public static ArrayList<Player> characters = new ArrayList<Player>();
	public static ArrayList<Monster> activeMonsters = new ArrayList<Monster>();
	//public ArrayList<Level> gameLevels = new ArrayList<Level>();
	
	public int queGenerateNewLevelId=0;
	public long queGenerateNewLevelSeed=0;
	
	private Item playerInventory[][] = new Item[10][8];
	
	public GameClient (String name, String hostAddr) {
		client = new Client();
	    client.start();
	    
	    gClient = client;
	    gameClient=this;
	    
	    this.name = name;
	    createPlayerInventory();
		
		Network.register(client);
		
		client.addListener(new ThreadedListener(new Listener() {
			public void connected (Connection connection) {
			}

			public void received (Connection connection, Object object) {
				if (object instanceof MonsterAttackOrder) {
					MonsterAttackOrder msg = (MonsterAttackOrder) object;
					Monster monst = currentLevel.getMonsterId(msg.monId);
					if(monst != null){
						monst.setTarget(getCharacter(msg.playerId));
						monst.setAttacking(true);
						System.out.println("attacking");
					}
				}
				if (object instanceof SendDamageDealt) {
					SendDamageDealt msg = (SendDamageDealt) object;
					
					currentLevel.sendDamageDealt(msg.monId, msg.damage, msg.damagedByPlayer);
				}
				if (object instanceof SendNewLevelSeed) {
					SendNewLevelSeed msg = (SendNewLevelSeed) object;
					//currentLevel = generateNewLevel(msg.id, msg.seed);
					queGenerateNewLevelId = msg.id;
					queGenerateNewLevelSeed = msg.seed;
//					System.out.println("set new que level... " + msg.id + " .. " + msg.seed);
				}
				if (object instanceof OtherClient) {
					System.out.println(((OtherClient) object).name + " has connected with userID: " + ((OtherClient) object).id);
				}
				if (object instanceof SendPlayersCharacter) {
					System.out.println("received my character");
					SendPlayersCharacter msg = (SendPlayersCharacter)object;
					myPlayerId = msg.id;
					Player plyr = getCharacter(msg.id);
					deleteCharacter(msg.id);
					myPlayer = loadPlayerCharacter();
					if(plyr == null)
						System.out.println("ERROR Player is NULL");
					else
					{
						//myPlayer = plyr;
						myPlayer.setId(plyr.getId());
						myPlayer.setX(plyr.getX());
						myPlayer.setY(plyr.getY());
						myPlayer.name = plyr.name;
						plyr = myPlayer;
						characters.add(myPlayer);
						if(myPlayer==null){
							System.out.println("error 777");
						}
					}
					if(myPlayer.getId()==0){ //double check valid host player.
						System.out.println("ERROR PLAYER ID IS 0");
					}
				}
				if (object instanceof AddCharacter) {
					AddCharacter msg = (AddCharacter)object;
					Player plyr = new Player();
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
					//System.out.println("Move order received by: " + clickOrder.id);
					if(!(((MoveClickOrder) object).id==myPlayer.getId())){ //if not self.
						getCharacter(clickOrder.id).movingClickOrder(clickOrder.x, clickOrder.y);
						
					}
				}
				
				if (object instanceof RemoveCharacter){
					deleteCharacter(((RemoveCharacter) object).id);
				}
					
			}

			public void disconnected (Connection connection) {
				System.exit(0);
			}
		}));
		
		try {
			client.connect(5000, hostAddr, Network.TCPport, Network.UDPport);
			
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
	
	
	protected Player loadPlayerCharacter() {
		return new Player();
	}


	private void createPlayerInventory() {
		for(int y=0; y<gInvHeight; y++){
			for(int x=0; x<gInvWidth; x++){
				playerInventory[x][y] = null;
			}
		}
	}
	
	public void renderInventory(){
		for(int y=0; y<gInvHeight; y++){
			for(int x=0; x<gInvWidth; x++){
				if(playerInventory[x][y] != null){
					//DrawQuadTex(tex_item_droplet_melee, 200, 200, 32, 32);
					DrawQuadTex(tex_item_droplet_melee, (game_width - 330) + (x * 32), (game_height - 330) + (y * 32), 32, 32);
				}
			}
		}
	}
	
	public void equipItem(Item item){
		if(gInventoryGear[GEAR_CHEST]==null){
			gInventoryGear[GEAR_CHEST]=item;
		}
		else {
			addItemToInventory(gInventoryGear[GEAR_CHEST]);
			gInventoryGear[GEAR_CHEST]=item;
		}
		String hpto = myPlayer.getMaxHealth() + " ";
		myPlayer.calculateInfo();
		hpto += myPlayer.getMaxHealth() + "";
		System.out.println(hpto);
	}
	
	/**
	 * Adds an item to inventory.
	 * @param item
	 * @return True if the item was added to list.
	 */
	public boolean addItemToInventory(Item item){
		if(isInventoryFull()) return false;
		for(int y=0; y<gInvHeight; y++){
			for(int x=0; x<gInvWidth; x++){
				if(playerInventory[x][y] == null){
					playerInventory[x][y] = item;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks to see if the inventory is full.
	 * @return True if the inventory is full.
	 */
	public boolean isInventoryFull(){
		for(int y=0; y<gInvHeight; y++){
			for(int x=0; x<gInvWidth; x++){
				if(playerInventory[x][y] == null) return false;
			}
		}
		return true;
	}
	
	/**
	 * returns item at given x and y.
	 * @param x
	 * @param y
	 * @return item
	 */
	public Item getInventoryItemAt(int x, int y){
		return playerInventory[x][y];
	}

	public void deleteCharacter(int id){
		for(int i=0; i<GameClient.characters.size(); i++){
			Player plyr = GameClient.characters.get(i);
			if(plyr.getId()==id){
				GameClient.characters.remove(i);
			}
		}
	}
	
	public Player getCharacter(int id){
		for(int i=0; i<GameClient.characters.size(); i++){
			Player plyr = GameClient.characters.get(i);
			if(plyr.getId()==id){
				return plyr;
			}
		}
		return null;
	}
	
	public Level generateNewLevel(int id, long seed){
		Level newLevel = new Level(id, seed);
		//gameLevels.add(newLevel);
		return newLevel;
	}


	public Item[][] getPlayerInventory() {
		return playerInventory;
	}


	public void setPlayerInventory(Item[][] playerInventory) {
		this.playerInventory = playerInventory;
	}


	public Entity findClosestTarget(double x, double y) {
		Entity closestTarget = null;
		double closest = 9999;
		double dist;
		for(int i=0; i<GameClient.characters.size(); i++){
			Player plyr = GameClient.characters.get(i);
			for(int pm=0; pm<plyr.getMinions().size();pm++){
				Entity minion = plyr.getMinions().get(pm);
				dist = distance(x,y, minion.getX(), minion.getY());
				if(dist<closest && dist<gMonsterLeashRange){
					closest=dist;
					closestTarget = minion;
				}
			} //end of minion check
			dist = distance(x,y, plyr.getX(), plyr.getY());
			if(dist<closest && dist<gMonsterLeashRange){
				closest=dist;
				closestTarget = plyr;
			}
			
		}
		return closestTarget;
	}

}
