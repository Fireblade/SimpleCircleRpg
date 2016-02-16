package mclama.com.level;

import java.util.Random;

public class Level {
	
	private long levelSeed=0;
	
	private int height=16, width=16, size=1;
	private int spawnX=2, spawnY=2;
	
	private Tile[][] tiles;
	
	
	public Level(int height, int width, int size, long seed){
		this.height = height;
		this.width = width;
		this.levelSeed = seed;
		this.size = size;
		
		Random levelGen = new Random(seed);
		int reRolledCount=0;
		
		
		int goLength = levelGen.nextInt(10)+(levelGen.nextInt(size)*5);
		int currLength=2;
		
		tiles = createTilesBase();
		
		
		tiles[spawnX][spawnY].setTile("woow"); //wall, open, open, wall. Clockwise.
		tiles[spawnX+1][spawnY].setTile("wwoo");
		tiles[spawnX][spawnY+1].setTile("ooww");
		tiles[spawnX+1][spawnY+1].setTile("ooww");
		
		int locX=spawnX+2;
		int locY=spawnY+1;
		
		//To-Do add list of open points to list.
		//add more open points where needed.
		//change open points to walls if cannot progress
		//create path in length to end point. 
		
		
		
	}


	private Tile[][] createTilesBase() {
		Tile[][] msg = null;
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				msg[x][y] = new Tile("blank");
			}
		}
		return msg;
	}

}
