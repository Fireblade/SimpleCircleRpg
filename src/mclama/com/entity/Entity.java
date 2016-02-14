package mclama.com.entity;

import org.newdawn.slick.opengl.Texture;

import mclama.com.GameServer;

import static mclama.com.util.DebugGlobals.*;
import static mclama.com.util.Artist.*;
import static mclama.com.util.globals.*;
import static org.lwjgl.opengl.GL11.*;

public class Entity {
	
	protected int id, level;
	protected double x=64, y=64;
	protected float size=1;
	public double speed=0.15f, xVelocity, yVelocity;
	protected int attackRange=48;
	
	protected double health = 10;
	
	protected int width=32, height=32;
	protected Texture texture;
	
	protected boolean alive=false;
	protected boolean hasDied=true;
	protected boolean moveToLoc=false;
	
	protected boolean isAttacking=false;
	
	protected Entity target;
	
	protected double moveX, moveY;
	
	
	public void tick(int delta){
		
		if (moveToLoc) {
			//System.out.println("moveloc");
			calculateDirection(moveX, moveY);
			if (distance(x, y, moveX, moveY) < (speed * delta) 
					|| (isAttacking && distance(x,y,target.getX(),target.getY()) < attackRange)) {
				moveToLoc = false;
			} else { // so the player doesn't move and then stop
				x += xVelocity * speed * delta;
				y += yVelocity * speed * delta;
				
				if(this instanceof Player){
					if(gameIsHosting){
						gameServer.updateCharacter(id, x,y);
					}
				}
			}

		}
		
		if(target != null){
			if(!target.isAlive()){ //target must have died.
				isAttacking=false;
				target = null;
			}
		}
	}
	
	public void draw(){
		
		if(texture!=null){
			DrawQuadTex(texture, x, y, width, height);
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
	
	protected void calculateDirection(double moveX2, double moveY2){
		double totalAllowedMovement = 1.0f;
		double xDistanceFromTarget = Math.abs(moveX2 - x);
		double yDistanceFromTarget = Math.abs(moveY2 - y);
		double totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
		double xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
		xVelocity = xPercentOfMovement;
		yVelocity = totalAllowedMovement - xPercentOfMovement;
		if (moveX2 < x)
			xVelocity *= -1;
		if(moveY2 < y) 
			yVelocity *= -1;
	}
	
	public double getAngle(float x2, float y2) {
		double angle = Math.toDegrees(Math.atan2(
				(x2 - x),
				(y2 - y)
				));
		//angle += 90;
		//float angle = (float) ((float) Math.atan2(x2 - (x + width/2),y2 - (y + height/2)) * 180 / 3.14);
		//angle -= 90;
		//if(angle < 0) angle+=360;
		return angle;
	}
	
	protected double distance(double x1, double y1, double x2, double y2) {
		double xDistanceFromTarget = Math.abs(x1 - x2);
		double yDistanceFromTarget = Math.abs(y1 - y2);
		return xDistanceFromTarget + yDistanceFromTarget;
	}
	
	protected float distanceX(float distance, double angle){
		return (float) (distance * Math.sin(Math.toRadians(angle)));
		//return (float) (distance * Math.cos(angle));
	}
	protected float distanceY(float distance, double angle){
		return (float) (distance * Math.cos(Math.toRadians(angle)));
		//return (float) (distance * Math.sin(angle));
	}



	public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public double getX() {
		return x;
	}



	public void setX(double x) {
		this.x = x;
	}



	public double getY() {
		return y;
	}



	public void setY(double y) {
		this.y = y;
	}



	public double getHealth() {
		return health;
	}



	public void setHealth(double health) {
		this.health = health;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public Texture getTexture() {
		return texture;
	}



	public void setTexture(Texture texture) {
		this.texture = texture;
	}



	public boolean isAlive() {
		return alive;
	}



	public void setAlive(boolean alive) {
		this.alive = alive;
	}



	public boolean isHasDied() {
		return hasDied;
	}



	public void setHasDied(boolean hasDied) {
		this.hasDied = hasDied;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
