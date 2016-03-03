package mclama.com.entity;

import static mclama.com.util.Globals.*;

import java.util.ArrayList;

import static mclama.com.util.Artist.*;
import static mclama.com.Network.SendDamageDealt;

import mclama.com.Network.MoveClickOrder;

public class Player extends Entity{
	
	public String name;

	public int incr_quantity=0, incr_rarity=0;
	
	private static ArrayList<Entity> minions = new ArrayList<Entity>();

	
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
	
	public void tick(int delta) {
		if(attackCooldownTicks>0) attackCooldownTicks--;
		
		if(isAttacking && target!= null){
			if(!target.isAlive()){ //target must have died.
				isAttacking=false;
				target = null;
			}
			else if(distance(x, y, target.getX(), target.getY()) > attackRange)
			{ //If out of range, move to range.
				double angle = getAngle(target.getX(), target.getY());
				double distance = distance(x, y, target.getX(), target.getY());
				moveX = x + distanceX(distance - attackRange, angle);
				moveY = y + distanceY(distance - attackRange, angle);
				
				if(gameIsRunning){
					MoveClickOrder moveOrder = new MoveClickOrder();
					moveOrder.id = id;
					moveOrder.x = moveX;
					moveOrder.y = moveY;
					gClient.sendUDP(moveOrder);
				}
				
				moveToLoc=true;
			}
			else if(attackCooldownTicks<1){ //if we can attack
				attackCooldownTicks = (int) (attackCooldownRate*targetFPS);
				
				//calculate damage
				double[] damage = {gen.nextInt(4)+4}; //add minimum damage, while rolling the seperated damage
				
				SendDamageDealt damageMsg = new SendDamageDealt();
				//damageMsg.levelId = currentLevel.getId();
				damageMsg.monId = target.getId();
				damageMsg.damage = damage;
				damageMsg.damagedByPlayer = id;
				
				gClient.sendUDP(damageMsg);
				
				//((Monster) target).takeDamage(damage, this);
				
			}
				
		}

		if (moveToLoc && currentLevel != null) {
			// System.out.println("moveloc");
			calculateDirection(moveX, moveY);
			if (distance(x, y, moveX, moveY) < (speed * delta)
					|| (isAttacking && distance(x, y, target.getX(), target.getY()) < attackRange)) {
				x = moveX;
				y = moveY;
				moveToLoc = false;
			} else { // so the player doesn't move and then stop
				try {
					if (currentLevel.validWalkable(x + (xVelocity * speed * delta), y + (yVelocity * speed * delta))) {
						x += xVelocity * speed * delta;
						y += yVelocity * speed * delta;
					} else {
						moveToLoc = false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (gameIsHosting) {
					gameServer.updateCharacter(id, x, y);
				}

			}

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

	public static ArrayList<Entity> getMinions() {
		return minions;
	}
	

	
}
