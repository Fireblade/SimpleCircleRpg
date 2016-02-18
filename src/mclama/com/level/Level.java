package mclama.com.level;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Level {
	
	private long levelSeed=0;
	
	private int height, width, size=1;
	private int spawnX=2, spawnY=2;
	
	private boolean[][] tiles;
	private int totalTiles=0;
	
	
	public Level(int width, int height, int size, long seed){
		this.height = height;
		this.width = width;
		this.levelSeed = seed;
		this.size = size;
		
		Random levelGen = new Random(seed);
		int reRolledCount=0;
		
		
		int goLength = levelGen.nextInt(10)+(levelGen.nextInt(size)*5);
		int currLength=1;
		
		tiles = createTilesBase();
		
		//Weight system
		int cNoise=30; //random tiles everywhere
		int cBroad=45; //very open, straight forward.
		int cLine =20; //line, straight forward
		int cMaze = 5; //maze like.
		
		Queue<Point> que = new LinkedList<Point>();
		
		spawnX=2;
		spawnY = (height/2);
		
		tiles[spawnX][spawnY]=true;
		int locX=spawnX+1;
		int locY=spawnY;
		
		boolean finished=false;
		que.add(new Point(locX, locY));
		while(que.size()>0){
			Point pnt = que.poll();
			
			try {
				finished = LevelsMaze.pullNewPreset(tiles, width, height, pnt, que, levelGen);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(finished){
				break;
			}
		}
		System.out.println("level created + " + (que.size()>0));
		
//		int levelTypeRoll = levelGen.nextInt(100);
//		if (levelTypeRoll < cMaze) {
//			// maze
//			spawnX=2;
//			spawnY = (height/2);
//			
//			tiles[spawnX][spawnY]=false;
//			int locX=spawnX+1;
//			int locY=spawnY;
//			
//			que.add(new Point(locX, locY));
//			while(que.size()>0){
//				Point pnt = que.poll();
//				boolean finished = pullNewMazePreset(pnt, que, levelGen);
//			}
//			
//			
//		} else if (levelTypeRoll < cLine) {
//			// Line
//			
//		} else if (levelTypeRoll < cNoise) {
//			// noise
//			
//		} else {
//			// Broad
//			
//		}
		
		
		
		
		
		totalTiles = getTotalTiles();
		
		//To-Do add list of open points to list.
		//add more open points where needed.
		//change open points to walls if cannot progress
		//create path in length to end point. 
		
		
		
	}

	private int getTotalTiles() {
		int count=0;
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				if(tiles[x][y]){
					count++;
				}
			}
		}
		return count;
	}

	private boolean[][] createTilesBase() {
		boolean[][] msg = new boolean[width][height];
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				msg[x][y] = false;
			}
		}
		return msg;
	}

	private Tile[][] createTilesBase2() {
		Tile[][] msg = null;
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				msg[x][y] = new Tile("blank");
			}
		}
		return msg;
	}

	public boolean[][] getTiles() {
		return tiles;
	}

}
