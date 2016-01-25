package mclama.com;

import java.io.IOException;
import java.util.Scanner;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import mclama.com.kryo.SomeRequest;
import mclama.com.kryo.SomeResponse;

public class SimpleCircleRpg {
	
	public static boolean gameIsRunning=true;

	public static void main(String[] args) {
		System.out.println("Starting");
		
		
		Server server = new Server();
	    server.start();
	    registerKryo(server);
	    try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof SomeRequest) {
	              SomeRequest request = (SomeRequest)object;
	              System.out.println(request.text);

	              SomeResponse response = new SomeResponse();
	              response.text = "Thanks";
	              connection.sendTCP(response);
	           }
	        }
	     });
	    
	    Scanner reader = new Scanner(System.in);
	    System.out.println("Host or name: ");
	    String text = reader.next();
		
	    Client client = new Client();
	    
	    if(text.equals("host")){
	        client.start();
	        registerKryo(client);
	        try {
				client.connect(5000, "localhost", 54555, 54777);
			} catch (IOException e) {
				e.printStackTrace();
			}

	        SomeRequest request = new SomeRequest();
	        request.text = "Here is the request, you are the server.";
	        client.sendTCP(request);
	    }
	    else
	    {
	    	System.out.println("Your name is... " + text);
	        client.start();
	        registerKryo(client);
	        try {
				client.connect(5000, "localhost", 54555, 54777);
			} catch (IOException e) {
				e.printStackTrace();
			}

	        SomeRequest request = new SomeRequest();
	        request.text = "Here is the request " + text;
	        client.sendTCP(request);
	    }
	    while(gameIsRunning){
	    	gameloop();
	    }
	    
	}
	
	public static void gameloop(){
		
	}

	private static void registerKryo (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
	}
}
