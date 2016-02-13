package mclama.com;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import mclama.com.kryo.SomeRequest;
import mclama.com.kryo.SomeResponse;

public class Network {
	static public final int port = 7777;
	static public final int TCPport = 54555;
	static public final int UDPport = 54777;
	
	public static void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		kryo.register(Login.class);
		kryo.register(OtherClient.class);
		kryo.register(RemoveCharacter.class);
		kryo.register(UpdateCharacter.class);
		kryo.register(AddCharacter.class);
		kryo.register(MoveCharacter.class);
		kryo.register(Character.class);
		kryo.register(MoveClickOrder.class);
		kryo.register(sendPlayersCharacter.class);
	}
	
	public static class Login {
		public String name;
	}
	
	public static class OtherClient {
		public String name;
		public int id;
	}
	
	public static class RemoveCharacter {
		public int id;
	}
	
	public static class UpdateCharacter {
		public int id;
		public double x, y;
	}
	
	public static class AddCharacter {
		public Character character;
	}
	
	public static class MoveCharacter {
		public double x, y;
	}
	
	public static class MoveClickOrder {
		public int id;
		public double x, y;
	}
	
	public static class sendPlayersCharacter{
		public int id;
	}

}
