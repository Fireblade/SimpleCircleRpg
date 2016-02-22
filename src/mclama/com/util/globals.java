package mclama.com.util;

import java.util.Random;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

import mclama.com.GameClient;
import mclama.com.GameServer;
import mclama.com.entity.Player;

public class globals {
	
	public static boolean gameIsRunning = false;
	public static boolean gameIsHosting = false;
	
	public static Random gen = new Random(System.nanoTime());
	
	public static Client gClient;
	public static Server gServer;
	
	public static GameClient gameClient;
	public static GameServer gameServer;
	
	
	public static Player myPlayer=new Player();
	
	public static float gBaseSpeed = 0.15f;
	public static float gStrengthHealthBonus = 2.0f;
	public static float gIntelligenceShieldBonus = 0.2f;

}
