package mclama.com.entity;

import static mclama.com.util.Globals.*;
import static mclama.com.util.Artist.*;

import mclama.com.Network.MoveClickOrder;

public class Player extends Entity{
	
	public String name;

	public int incr_quantity=0, incr_rarity=0;

	
	public Player(){
		texture = tex_circle;
		
	}
	
	public void playerClicked(double mouseX, double mouseY){
		moveX = mouseX;
		moveY = mouseY;
		moveToLoc=true;
		
		if(myPlayer==null){
			System.out.println("null playr click");
		}
		
		if(gameIsRunning){
			MoveClickOrder moveOrder = new MoveClickOrder();
			moveOrder.id = id;
			moveOrder.x = moveX;
			moveOrder.y = moveY;
			gClient.sendUDP(moveOrder);
		}
		
	}
	
	public void movingClickOrder(double x, double y){
		moveX = x;
		moveY = y;
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
