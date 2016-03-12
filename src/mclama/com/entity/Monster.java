package mclama.com.entity;

import static mclama.com.util.Artist.*;
import static mclama.com.util.Globals.*;
import static mclama.com.util.Utility.df;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static mclama.com.util.Console.*;
import static mclama.com.util.DebugGlobals.D_PlayerShowMoveToLine;

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
		
		width *= size;
		height *= size;
		if(size < 1){
			speed *= 1 + Math.abs(1-size);
		}
		attackRange *= size;
		
		health *= size;
		maxHealth *= size;
		if(is_magical){
			health *= 1.5;
			maxHealth *= 1.5;
		}
		if(is_boss){
			health *= 2.5;
			maxHealth *= 2.5;
		}
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
					if(attackMovePenaltyTicks>0){
						attackMovePenaltyTicks--;
					}
					else
					{
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
				}
				else if(attackCooldownTicks<1){ //if we can attack
					attackCooldownTicks = (int) (attackCooldownRate*targetFPS);
					attackMovePenaltyTicks = (int) (attackMovePenaltyRate*targetFPS);
					//System.out.println(attackMovePenaltyTicks);
					
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
			
			if (moveToLoc && currentLevel != null) {
				// System.out.println("moveloc");
				calculateDirection(moveX, moveY);
				if (distance(x, y, moveX, moveY) < (speed * delta)
						|| (isAttacking && distance(x, y, target.getX(), target.getY()) < attackRange)) {
					x = moveX;
					y = moveY;
					moveToLoc = false; 
					// TODO Attack method if we are attacking
					if(attackCooldownTicks<1 && isAttacking){
						attackCooldownTicks = (int) (attackCooldownRate*targetFPS);
						attackMovePenaltyTicks = (int) (attackMovePenaltyRate*targetFPS);
						//System.out.println(attackMovePenaltyTicks);
					}
				} else {
					try {
						// x += xVelocity * speed * delta;
						// y += yVelocity * speed * delta;
						if (currentLevel.validWalkable(x + (xVelocity * speed * delta), y + (yVelocity * speed * delta))) {
							x += xVelocity * speed * delta;
							y += yVelocity * speed * delta;
						} else {
							moveToLoc = false;
//							System.out.println("x:" + (int) Math.floor((x + (xVelocity * speed * delta)) / 128));
//							System.out.println("y:" + (int) Math.floor((y + (yVelocity * speed * delta)) / 128));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
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
	
	public void draw(){
		
		if(texture!=null){
			if(alive){
				glColor4f(1f, 0.8f, 0.8f, 1f);
				DrawQuadTex(texture, x, y, width-1, height-1);
				if(is_magical){
					glColor4f(0f, 0f, 1f, 1f);
					DrawQuadTex(tex_circle_ring_outer_thick, x, y, width-1, height-1);
				}
				if(is_boss){
					glColor4f(1f, 1f, 0f, 1f);
					DrawQuadTex(tex_circle_ring_outer_thick, x, y, width-1, height-1);
				}

			}
		} else texture = LoadTexture("res/images/circles/circle.png", "PNG");
		
		if(moveToLoc && D_PlayerShowMoveToLine){
			glTranslated(camX,camY,0);
			glDisable(GL_TEXTURE_2D);
			glColor4f(1f,0f,0f,1f);
			glBegin(GL_LINES);
				glVertex2d(x,y);
				glVertex2d(moveX, moveY);
			glEnd();
			glLoadIdentity();
			glColor4f(1f,1f,1f,1f);
			glEnable(GL_TEXTURE_2D);
		}
		
//		if(O_showMonsterHealthBars){
//			
//		}
//		
//		if(O_showMonsterHealthBarNumbers){
//			TrueTypeFont ttf = getMonsterTtf();
//			int widthOffset = getMonsterFontOffset((int) health+" ", getMonsterFont());
//			ttf.drawString( camX + x - widthOffset/2, camY + y - height/2 - 10, (int) health + " ");
//		}
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
			float[] rarityMods = {1f, 1f, 1f, 1f, 1f, 1f};
			if(gen.nextBoolean()) items_drop += gen.nextFloat();
			if(gen.nextInt(10)==0) items_drop += gen.nextFloat();
			if(is_boss) {items_drop += gen.nextFloat()*2; quantity +=50; rarity+= 100; rarityMods[0] =0.25f;}
			else if(is_magical) {items_drop += gen.nextFloat(); quantity +=20; rarity+= 40; rarityMods[0] =0.45f;}
			if(is_aura)    {items_drop += gen.nextFloat(); quantity +=10; rarity+= 10; rarityMods[0] -=0.1f;}
			
			items_drop = (int) Math.floor((items_drop * size) * (1+(quantity/100)));
			
			System.out.println("drop: " + items_drop + ", Quantity: " + quantity + ", Rarity: " + rarity);
			
			for (int i = 0; i < items_drop; i++) {
				//item_drops.add(new Item("noname", level, rarity, whites)); //Test rolls
				Item iDrop = new Item("noname", level, rarity, rarityMods);
				currentLevel.addNewItemDrop(iDrop, (x - width) + (gen.nextInt(width)),
						(y - height) + (gen.nextInt(height)));
			}
			// Experience reward
			double grantExp = 0;
			grantExp = 1 + (level/2.5);
			grantExp *= 1 + (level/10);
			grantExp *= (size);
			if(is_magical) grantExp *= 1.25f;
			if(is_boss) grantExp *= 2;
			
			myPlayer.gainedExperience(grantExp);
			
			conAdd("Monster killed by " + killedBy.getId() + " and dropped " + (int) items_drop + " items. +" + df.format(grantExp) + "xp.");
		}// end of first death
		
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
