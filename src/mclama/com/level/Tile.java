package mclama.com.level;

public class Tile {
	
	private String tile="blank";
	private int tileId=0;

	public Tile(String string) {
		tile = string;
		
	}
	
	public boolean isWall(int index){
		if(tile.charAt(index)=='w') return true;
		return false;
	}
	
	public boolean isOpen(int index){
		if(tile.charAt(index)=='o') return true;
		return false;
	}

	public String getTile() {
		return tile;
	}

	public void setTile(String tile) {
		this.tile = tile;
	}

	public int getTileId() {
		return tileId;
	}

	public void setTileId(int tileId) {
		this.tileId = tileId;
	}

}
