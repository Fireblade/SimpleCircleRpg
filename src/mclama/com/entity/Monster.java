package mclama.com.entity;

import static mclama.com.util.Artist.tex_circle;
import static mclama.com.util.Globals.*;
import static mclama.com.util.Console.*;

import java.util.ArrayList;
import java.util.Random;

import mclama.com.Network.*;
import mclama.com.item.Item;

public class Monster extends Entity{
	
	Player lastHitBy=null;
	
	public int quantity_bonus=0, rarity_bonus=0;
	
	public boolean is_magical=false;
	public boolean is_boss=false;
	public boolean is_aura=false;
	
	private ArrayList<Item> item_drops = new ArrayList<Item>();
	
	
	public Monster(int id, float size, double x, double y){
		this.id = id;
		this.size = size;
		this.level = gGHighestPlayerLevel;
		this.x = x;
		this.y = y;
		texture = tex_circle;
	}

	public void tick(int delta) {
		if (alive) {
			if(gameIsHosting){
				if (!moveToLoc && currentLevel != null) {
					if (target == null) {
						target = gameClient.findClosestTarget(x, y);
						if(target != null){
							//isAttacking=true;
							
							MonsterAttackOrder msg = new MonsterAttackOrder();
							msg.monId = id;
							msg.playerId = target.getId();
							gServer.sendToAllUDP(msg);
							//no minions for now, so just a simple monster and player id
							
						}
					}
				}
			}
			
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
						// TODO Monster move order
//						MoveClickOrder moveOrder = new MoveClickOrder();
//						moveOrder.id = id;
//						moveOrder.x = moveX;
//						moveOrder.y = moveY;
//						gClient.sendUDP(moveOrder);
					}
					
					moveToLoc=true;
				}
				else if(attackCooldownTicks<1){ //if we can attack
					attackCooldownTicks = (int) (attackCooldownRate*targetFPS);
					
					//calculate damage
//					double[] damage = {gen.nextInt(4)+4}; //add minimum damage, while rolling the seperated damage
					
					
					//Monster damage dealt!
//					SendDamageDealt damageMsg = new SendDamageDealt();
//					//damageMsg.levelId = currentLevel.getId();
//					damageMsg.monId = target.getId();
//					damageMsg.damage = damage;
//					damageMsg.damagedByPlayer = id;
//					
//					gClient.sendUDP(damageMsg);
					
					
					//lets not damage players yet :)
					//((Monster) target).takeDamage(damage, this);
					
				}
					
			}
			
		}
		
		
		

		if (moveToLoc && currentLevel != null) {
			// System.out.println("moveloc");
			calculateDirection(moveX, moveY);
			if (distance(x, y, moveX, moveY) < (speed * delta)
					|| (isAttacking && distance(x, y, target.getX(), target.getY()) < attackRange)) {
				moveToLoc = false;
			} else {
				try {
					// x += xVelocity * speed * delta;
					// y += yVelocity * speed * delta;
					if (currentLevel.validWalkable(x + (xVelocity * speed * delta), y + (yVelocity * speed * delta))) {
						x += xVelocity * speed * delta;
						y += yVelocity * speed * delta;
					} else {
						moveToLoc = false;
						System.out.println("x:" + (int) Math.floor((x + (xVelocity * speed * delta)) / 128));
						System.out.println("y:" + (int) Math.floor((y + (yVelocity * speed * delta)) / 128));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		}

		if (target != null) {
			if (distance(x, y, target.getX(), target.getY()) > (gMonsterLeashRange * 2)) {
				isAttacking = false;
				target = null;
			}
			else if (!target.isAlive()) { // target must have died.
				isAttacking = false;
				target = null;
			}
		}
	}
	
	public void takeDamage(double[] damage, Player damagedBy){
		//resistances blah blah
		//0 = physical
		health -= damage[0];
		
		if(health <1){
			die(damagedBy);
		}
	}
	
	public void die(Player killedBy){
		alive=false;
		
			
		if(hasDied == false)
		{
			int quantity = killedBy.getIncr_quantity() + quantity_bonus + (Math.min(level, 100));
			int rarity   = killedBy.getIncr_rarity() + rarity_bonus + (int) (Math.min(level, 200)/2);
			
			long shards = 0;
			
			Random gen = new Random();
			
			double items_drop = gen.nextFloat();
			float whites = 1f;
			if(gen.nextBoolean()) items_drop += gen.nextFloat();
			if(gen.nextInt(10)==0) items_drop += gen.nextFloat();
			if(is_boss) {items_drop += gen.nextFloat()*2; quantity +=50; rarity+= 100; whites =0.25f;}
			else if(is_magical) {items_drop += gen.nextFloat(); quantity +=20; rarity+= 40; whites =0.45f;}
			if(is_aura)    {items_drop += gen.nextFloat(); quantity +=10; rarity+= 10; whites -=0.1f;}
			
			items_drop = (int) Math.floor((items_drop * size) * (1+(quantity/100)));
			
			System.out.println("drop: " + items_drop + ", Quantity: " + quantity + ", Rarity: " + rarity);
			
			for (int i = 0; i < items_drop; i++) {
				//item_drops.add(new Item("noname", level, rarity, whites)); //Test rolls
				Item iDrop = new Item("noname", level, rarity, whites);
				currentLevel.addNewItemDrop(iDrop, (x - width) + (gen.nextInt(width)),
						(y - height) + (gen.nextInt(height)));
			}
			
			conAdd("Monster killed by " + killedBy.getId() + " and dropped " + (int) items_drop + " items.");
		}
		
		hasDied=true; //set monster to have died before, Incase zombification!
	}


	public ArrayList<Item> getItem_drops() {
		return item_drops;
	}

	public boolean inBounds(double mouseX, double mouseY) {
		if(mouseX < x - (width/2)) return false;
		if(mouseX > x + (width/2)) return false;
		
		if(mouseY < y - (height/2)) return false;
		if(mouseY > y + (height/2)) return false;
		return true;
	}

}
