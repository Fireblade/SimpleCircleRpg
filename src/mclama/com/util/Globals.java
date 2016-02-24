package mclama.com.util;

import java.util.Random;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

import mclama.com.GameClient;
import mclama.com.GameServer;
import mclama.com.entity.Player;
import mclama.com.level.Level;

public class Globals {
	
	public static int game_width = 720;
	public static int game_height = 480;
	
//	public static int game_width = 1280; //doesnt work yet, hmmm
//	public static int game_height = 720;
	
	public static boolean gameIsRunning = false;
	public static boolean gameIsHosting = false;
	
	public static int myPlayerId=0;
	
	public static Random gen = new Random(System.nanoTime());
	
	public static Client gClient;
	public static Server gServer;
	
	public static GameClient gameClient;
	public static GameServer gameServer;
	
	
	public static Player myPlayer=new Player();
	public static int gGHighestPlayerLevel=1;
	
	public static float gBaseSpeed = 0.15f;
	public static float gStrengthHealthBonus = 2.0f;
	public static float gIntelligenceShieldBonus = 0.2f;
	
	public static Level currentLevel=null;
	
	public static float gLGBaseMonsterPacks=0.4f; //chance of spawning a pack on a tile
	public static float gLGBaseMonsterPackSize = 4;

}
