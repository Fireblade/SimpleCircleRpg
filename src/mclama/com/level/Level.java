package mclama.com.level;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Level {
	
	private long levelSeed=0;
	
	private int height=16, width=16, size=1;
	private int spawnX=2, spawnY=2;
	
	private boolean[][] tiles;
	
	
	public Level(int height, int width, int size, long seed){
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
		
		int levelTypeRoll = levelGen.nextInt(100);
		if (levelTypeRoll < cMaze) {
			// maze
			spawnX=2;
			spawnY = (height/2);
			
			tiles[spawnX][spawnY]=false;
			int locX=spawnX+1;
			int locY=spawnY;
			
			que.add(new Point(locX, locY));
			while(que.size()>0){
				Point pnt = que.poll();
				pullNewMazePreset(pnt, que, levelGen);
			}
			
			
		} else if (levelTypeRoll < cLine) {
			// Line
			
		} else if (levelTypeRoll < cNoise) {
			// noise
			
		} else {
			// Broad
			
		}
		
		
		
		
		
//		tiles = createTilesBase();
//		
//		
//		tiles[spawnX][spawnY].setTile("woow"); //wall, open, open, wall. Clockwise.
//		tiles[spawnX+1][spawnY].setTile("wwoo");
//		tiles[spawnX][spawnY+1].setTile("ooww");
//		tiles[spawnX+1][spawnY+1].setTile("oowo");
//		
//		int locX=spawnX+2;
//		int locY=spawnY+1;
//		
//		Queue<Point> que = new LinkedList<Point>();
//		
//		que.add(new Point(spawnX+2, spawnY+1));
//		
//		while(que.size()>0){
//			
//		}
		
		//To-Do add list of open points to list.
		//add more open points where needed.
		//change open points to walls if cannot progress
		//create path in length to end point. 
		
		
		
	}

	private void pullNewMazePreset(Point pnt, Queue<Point> que, Random levelGen) {
		int cycles=0;
		int mazePresets = 1;
		int x = pnt.x;
		int y = pnt.y;
		while(true){
			cycles++;
			
			int presetRoll = levelGen.nextInt(mazePresets);
			
			switch(presetRoll){
			
			case 0:
				if (x + 3 < width && y - 2 > 0 && y + 3 < height) {
					tiles[x + 2][y + 0] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 2][y - 3] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x - 1][y + 0] = true;
					tiles[x - 2][y + 0] = true;
					tiles[x - 2][y - 1] = true;
					tiles[x - 3][y - 1] = true;

					que.add(new Point(x - 2, y + 3));
				}

				break;
				
			case 1:
				if (x + 4 < width && y - 2 > 0 && y + 3 < height) {
					tiles[x + 2][y + 0] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 2][y - 3] = true;
					tiles[x + 2][y - 4] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 0][y - 2] = true;
					tiles[x + 0][y - 3] = true;
					tiles[x - 1][y + 0] = true;
					tiles[x - 1][y - 3] = true;
					tiles[x - 2][y + 0] = true;
					tiles[x - 2][y - 1] = true;
					tiles[x - 2][y - 3] = true;
					tiles[x - 3][y - 1] = true;
					tiles[x - 3][y - 2] = true;
					tiles[x - 3][y - 3] = true;

					que.add(new Point(x - 2, y + 4));
				}
				break;

			case 2:
				if (x + 4 < width && y - 2 > 0 && y + 3 < height) {
					tiles[x + 2][y + 0] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 2][y - 3] = true;
					tiles[x + 2][y - 4] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 0][y - 2] = true;
					tiles[x + 0][y - 3] = true;
					tiles[x - 1][y + 0] = true;
					tiles[x - 1][y - 3] = true;
					tiles[x - 2][y + 0] = true;
					tiles[x - 2][y - 1] = true;
					tiles[x - 2][y - 3] = true;
					tiles[x - 3][y - 1] = true;
					tiles[x - 3][y - 2] = true;
					tiles[x - 3][y - 3] = true;

					que.add(new Point(x - 2, y + 4));
				}

			case 3:
				if (x + 8 < width && y - 3 > 0 && y + 4 < height) {
					tiles[x + 3][y - 3] = true;
					tiles[x + 3][y - 4] = true;
					tiles[x + 3][y - 5] = true;
					tiles[x + 3][y - 6] = true;
					tiles[x + 3][y - 7] = true;
					tiles[x + 2][y + 0] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 2][y - 3] = true;
					tiles[x + 2][y - 5] = true;
					tiles[x + 2][y - 7] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 1][y - 5] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 0][y - 1] = true;
					tiles[x + 0][y - 3] = true;
					tiles[x + 0][y - 4] = true;
					tiles[x + 0][y - 5] = true;
					tiles[x + 0][y - 6] = true;
					tiles[x + 0][y - 7] = true;
					tiles[x - 1][y - 5] = true;
					tiles[x - 2][y + 0] = true;
					tiles[x - 2][y - 1] = true;
					tiles[x - 2][y - 4] = true;
					tiles[x - 2][y - 5] = true;
					tiles[x - 2][y - 7] = true;
					tiles[x - 2][y - 8] = true;
					tiles[x - 3][y + 0] = true;
					tiles[x - 3][y - 1] = true;
					tiles[x - 3][y - 4] = true;
					tiles[x - 3][y - 7] = true;
					tiles[x - 4][y + 0] = true;
					tiles[x - 4][y - 1] = true;
					tiles[x - 4][y - 2] = true;
					tiles[x - 4][y - 3] = true;
					tiles[x - 4][y - 4] = true;
					tiles[x - 4][y - 5] = true;
					tiles[x - 4][y - 6] = true;
					tiles[x - 4][y - 7] = true;

					que.add(new Point(x + 2, y + 8));
				}
				break;

			case 4:
				if (x + 6 < width && y - 7 > 0 && y + 6 < height) {
					tiles[x + 7][y - 5] = true;
					tiles[x + 6][y - 1] = true;
					tiles[x + 6][y - 2] = true;
					tiles[x + 6][y - 3] = true;
					tiles[x + 6][y - 4] = true;
					tiles[x + 6][y - 5] = true;
					tiles[x + 5][y - 1] = true;
					tiles[x + 5][y - 4] = true;
					tiles[x + 4][y - 1] = true;
					tiles[x + 4][y - 4] = true;
					tiles[x + 4][y - 5] = true;
					tiles[x + 4][y - 6] = true;
					tiles[x + 3][y - 1] = true;
					tiles[x + 3][y - 4] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 2][y - 3] = true;
					tiles[x + 2][y - 4] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 1][y - 3] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 0][y - 1] = true;
					tiles[x + 0][y - 3] = true;
					tiles[x + 0][y - 4] = true;
					tiles[x + 0][y - 5] = true;
					tiles[x - 1][y - 1] = true;
					tiles[x - 1][y - 5] = true;
					tiles[x - 2][y - 1] = true;
					tiles[x - 2][y - 2] = true;
					tiles[x - 2][y - 3] = true;
					tiles[x - 2][y - 5] = true;
					tiles[x - 3][y - 5] = true;
					tiles[x - 4][y - 1] = true;
					tiles[x - 4][y - 2] = true;
					tiles[x - 4][y - 3] = true;
					tiles[x - 4][y - 5] = true;
					tiles[x - 5][y - 3] = true;
					tiles[x - 5][y - 5] = true;
					tiles[x - 6][y - 3] = true;
					tiles[x - 6][y - 4] = true;
					tiles[x - 6][y - 5] = true;

					que.add(new Point(x - 4, y + 6));
				}
				break;

			case 5:
				if (x + 5 < width && y - 2 > 0 && y + 7 < height) {
					tiles[x + 2][y - 1] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 2][y - 3] = true;
					tiles[x + 2][y - 4] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 1][y - 2] = true;
					tiles[x + 1][y - 3] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 0][y - 1] = true;
					tiles[x + 0][y - 2] = true;
					tiles[x - 1][y - 1] = true;
					tiles[x - 2][y - 1] = true;
					tiles[x - 2][y - 3] = true;
					tiles[x - 2][y - 4] = true;
					tiles[x - 3][y - 1] = true;
					tiles[x - 3][y - 4] = true;
					tiles[x - 4][y - 1] = true;
					tiles[x - 4][y - 4] = true;
					tiles[x - 5][y - 1] = true;
					tiles[x - 5][y - 4] = true;
					tiles[x - 5][y - 5] = true;
					tiles[x - 6][y - 1] = true;
					tiles[x - 6][y - 2] = true;
					tiles[x - 6][y - 3] = true;
					tiles[x - 6][y - 4] = true;
					tiles[x - 7][y - 3] = true;

					que.add(new Point(x + 5, y + 5));
				}
				break;
			case 6:
				if (x + 2 < width && y - 0 > 0 && y + 6 < height) {
					tiles[x + 0][y + 0] = true;
					tiles[x + 0][y - 1] = true;
					tiles[x - 1][y - 1] = true;
					tiles[x - 2][y + 0] = true;
					tiles[x - 2][y - 1] = true;
					tiles[x - 3][y + 0] = true;
					tiles[x - 4][y + 0] = true;
					tiles[x - 4][y - 1] = true;
					tiles[x - 4][y - 2] = true;
					tiles[x - 5][y + 0] = true;
					tiles[x - 6][y + 0] = true;
					tiles[x - 6][y - 1] = true;

					que.add(new Point(x + 4, y + 2));
				}
				break;

			case 7:
				if (x + 2 < width && y - 5 > 0 && y + 2 < height) {
					tiles[x + 5][y + 0] = true;
					tiles[x + 5][y - 1] = true;
					tiles[x + 5][y - 2] = true;
					tiles[x + 4][y + 0] = true;
					tiles[x + 3][y + 0] = true;
					tiles[x + 3][y - 1] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 0][y - 1] = true;
					tiles[x - 1][y - 1] = true;
					tiles[x - 2][y + 0] = true;
					tiles[x - 2][y - 1] = true;

					que.add(new Point(x - 5, y + 2));
				}
				break;

				}
			
			if(cycles>100){
				System.out.println("reached end of pullNewMazePreset cycle");
				break;
			}
		}
	}

	private boolean[][] createTilesBase() {
		boolean[][] msg = null;
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

}
