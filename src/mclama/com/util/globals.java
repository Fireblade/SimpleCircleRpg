package mclama.com.util;

import java.util.Random;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

import mclama.com.GameClient;
import mclama.com.entity.Player;

public class globals {
	
	public static Random gen = new Random(System.nanoTime());
	
	public static Client gClient;
	public static Server gServer;
	
	
	public static Player myPlayer=new Player();

}
