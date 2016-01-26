package mclama.com;

import java.io.IOException;
import java.util.Scanner;

import com.esotericsoftware.minlog.Log;


public class SimpleCircleRpg {
	
	public static boolean gameIsRunning=true;
	public static boolean gameIsHosting=false;
	
	public static GameClient client;
	public static GameServer server;

	public static void main(String[] args) throws IOException {
		System.out.println("Starting");
		
		Scanner reader = new Scanner(System.in);
	    System.out.println("Host or name: ");
	    String text = reader.next();
	    
	    Log.DEBUG();
	    
	    if(text.equals("host")){
	    	System.out.println("What is your name: ");
	    	text = reader.next();
	    	gameIsHosting=true;
	    	try {
				server = new GameServer();
				client = new GameClient(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    else 
	    {
	    	client = new GameClient(text);
	    }
		
	    while(gameIsRunning){
	    	gameloop();
	    }
	    
	}
	
	public static void gameloop(){
		
	}

}
