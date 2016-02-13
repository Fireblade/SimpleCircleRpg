package mclama.com.entity;

public class Player extends Entity{
	
	public String name;

	public int incr_quantity=0, incr_rarity=0;

	
	public void playerClicked(double mouseX, double mouseY){
		moveX = mouseX;
		moveY = mouseY;
		moveToLoc=true;
		
	}
	
//	public void tick(int delta){
//		
//	}

	public int getIncr_quantity() {
		return incr_quantity;
	}

	public int getIncr_rarity() {
		return incr_rarity;
	}
	

	
}
