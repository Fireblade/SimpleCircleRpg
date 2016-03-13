package mclama.com.util;

import java.util.Random;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

import mclama.com.GameClient;
import mclama.com.GameServer;
import mclama.com.entity.Player;
import mclama.com.item.Item;
import mclama.com.level.Level;

public class Globals {
	
//	public static int game_width = 720;
//	public static int game_height = 480;
	
	public static int game_width = 1280;
	public static int game_height = 720;
	
	public static int targetFPS=60;
	
	public static boolean gameIsRunning = false;
	public static boolean gameIsHosting = false;
	
	public static int myPlayerId=0;
	
	public static Random gen = new Random(System.nanoTime());
	
	public static Client gClient;
	public static Server gServer;
	
	public static GameClient gameClient;
	public static GameServer gameServer;
	
	public static int gInvWidth=10;
	public static int gInvHeight=8;
	
	public static boolean gShowInventory=false;
	public static boolean gShowConsole=false;
	public static boolean gShowDeveloperConsole=true;
	
	public static Item[] gInventoryGear = {null,null,null,null,null,null,null,null,null,null,null,null,null,null};
	
	public static final int GEAR_WEAPON=0;
	public static final int GEAR_OFFHAND=1;
	public static final int GEAR_RING=2;
	public static final int GEAR_RING2=3;
	public static final int GEAR_AMULET=4;
	public static final int GEAR_HEAD=5;
	public static final int GEAR_CHEST=6;
	public static final int GEAR_BELT=7;
	public static final int GEAR_PANTS=8;
	public static final int GEAR_GLOVES=9;
	public static final int GEAR_BOOTS=10;
	public static final int GEAR_RELIC=11;
	public static final int GEAR_RELIC2=12;
	public static final int GEAR_SYMBOL=13;
	
	public static final int RARITY_WHITE=0;
	public static final int RARITY_BLUE=1;
	public static final int RARITY_GREEN=2;
	public static final int RARITY_YELLOW=3;
	public static final int RARITY_ORANGE=4;
	public static final int RARITY_PURPLE=5;
	
	public static Player myPlayer=new Player();
	public static int gGHighestPlayerLevel=1;
	
	public static float gBaseSpeed = 0.15f;
	public static float gStrengthHealthBonus = 2.0f;
	public static float gIntelligenceShieldBonus = 0.2f;
	
	public static Level currentLevel=null;
	
	public static int gMonsterLeashRange = 128;
	
	public static float gLGBaseMonsterPacks=0.4f; //chance of spawning a pack on a tile
	public static float gLGBaseMonsterPackSize = 4;
	public static float gLGBaseMonsterMagicChance = 0.15f;
	public static float gLGBaseMonsterRareChance = 0.01f;
	
	
	
	public static float gNoclipSpeed=0.5f;
	
	public static double distance(double x1, double y1, double x2, double y2) { //moved for now
		double xDistanceFromTarget = Math.abs(x1 - x2);
		double yDistanceFromTarget = Math.abs(y1 - y2);
		return xDistanceFromTarget + yDistanceFromTarget;
	}

}
