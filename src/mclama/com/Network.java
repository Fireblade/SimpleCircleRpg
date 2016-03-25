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
		kryo.register(SendPlayersCharacter.class);
		kryo.register(PlayerRequestNewLevel.class);
		kryo.register(SendNewLevelSeed.class);
		kryo.register(SendDamageDealt.class);
		kryo.register(double[].class);
		kryo.register(MonsterAttackOrder.class);
		kryo.register(PlayerSkillCasted.class);
		
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
	
	public static class SendPlayersCharacter{
		public int id;
	}
	
	public static class PlayerRequestNewLevel{
		
	}
	
	public static class SendNewLevelSeed{
		public long seed;
		public int id;
		public int zoneLevel;
	}
	
	public static class SendDamageDealt{
		public int monId;
		//public int levelId;
		public double[] damage;
		public int damagedByPlayer;
	}
	
	public static class MonsterAttackOrder{
		public int monId;
		public boolean isMinion=false;
		public int minionId;
		public int playerId;
	}
	
	public static class PlayerSkillCasted{
		public int playerId;
		public int skillId;
		public double spawnx, spawny;
		public double targetx, targety;
	}

}
