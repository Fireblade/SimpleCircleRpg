package mclama.com.level;

import static mclama.com.util.Artist.DrawQuadTexNormal;
import static mclama.com.util.Artist.LoadTexture;
import static mclama.com.util.Artist.onScreen;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;

public class Level {
	
	private long levelSeed=0;
	
	private int height, width, size=1;
	private int spawnX=2, spawnY=2;
	
	private int tileWidth=128, tileHeight=128;
	
	private boolean[][] tiles;
	private int totalTiles=0;
	
	private Texture texture;
	
	
	public Level(int width, int height, int size, long seed){
		this.height = height;
		this.width = width;
		this.levelSeed = seed;
		this.size = size;
		
		Random levelGen = new Random(seed);
		//Roll theme type
		String themeType = "grass";
		
		int num = levelGen.nextInt(20)+1;
		String themeName = (num < 10 ? "0" : "") + num;
		try {
			texture = LoadTexture("res/images/tiles/" + themeType + "/" + themeType + themeName + ".png", "PNG");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
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
		
		spawnX = 2;
		spawnY = (height/2);
		
		tiles[spawnX][spawnY]=true;
		int locX=spawnX+1;
		int locY=spawnY;
		
		boolean finished=false;
		que.add(new Point(locX, locY));
		
		
		
		int levelTypeRoll = levelGen.nextInt(100);
		if (levelTypeRoll < cMaze) {
			// maze
			
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
			
			
		} else if (levelTypeRoll < cLine) {
			// Line
			//TODO make chance of straight map being thicker tiles.
			while(que.size()>0){
				Point pnt = que.poll();
				
				try {
					finished = LevelsLine.pullNewPreset(tiles, width, height, pnt, que, levelGen);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(finished){
					break;
				}
			}
		} else if (levelTypeRoll < cNoise) {
			// noise
			while(que.size()>0){
				Point pnt = que.poll();
				
				try {
					finished = LevelsNoise.pullNewPreset(tiles, width, height, pnt, que, levelGen);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(finished){
					break;
				}
			}
		} else {
			// Broad
			while(que.size()>0){
				Point pnt = que.poll();
				
				try {
					finished = LevelsBroad.pullNewPreset(tiles, width, height, pnt, que, levelGen);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(finished){
					break;
				}
			}
		}
		System.out.println("level created + " + (que.size()>0));
		
		
		
		
		totalTiles = getTotalTiles();
		
		//To-Do add list of open points to list.
		//add more open points where needed.
		//change open points to walls if cannot progress
		//create path in length to end point. 
		
		
		
		
	}
	
	public void renderLevel(){
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				if(tiles[x][y]){ //if it is a tile, we should render.
					//Later to be replaced by a list of tiles
					if(onScreen(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2)){
						
					}
					DrawQuadTexNormal(texture, (x*tileWidth), (y*tileHeight),tileWidth, tileHeight);
				}
			}
		}
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

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public boolean validWalkable(double x, double y) {
//		x -= tileWidth/2;
//		y -= tileHeight/2;
		int tileX = (int) Math.floor(x / tileWidth);
		int tileY = (int) Math.floor(y / tileHeight);
		
		tileX = Math.min(tileX, width);
		tileX = Math.max(tileX, 0);
		
		tileY = Math.min(tileY, height);
		tileY = Math.max(tileY, 0);
		
		if (tiles[tileX][tileY]) {
			return true;
		}
		return false;
	}
	
	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public int getSpawnXLoc() {
		return ((spawnX+1)*tileWidth) - (tileWidth/2);
	}

	public int getSpawnYLoc() {
		return ((spawnY+1)*tileHeight) - (tileHeight/2);
	}

}
