package mclama.com.skills;

import mclama.com.entity.Entity;

import static mclama.com.util.Globals.targetFPS;
import static mclama.com.util.Globals.myPlayer;

public class Skill implements ISkill{
	
	protected int skillId=0;
	protected int playerId=0;
	
	protected double x, y, moveX, moveY;
	protected double speed=0.25f, xVelocity, yVelocity;
	
	protected float size=1;
	protected float width=24, height=24;
	protected float radius=32;
	
	protected Entity target;
	
	protected int level=0;
	protected int tickCount=0;
	protected int castSpeed = targetFPS;
	
	protected boolean activated=false;
	
//	public abstract void onActivate();
//	public abstract void tick(int delta);
//	
//	public abstract void buff();
//	public abstract void debuff();
//	
//	public abstract void onHit(Entity targetHit);
//	
//	public abstract void destroy();
//	public abstract void draw();
	
	public void onActivate(){}
	public void tick(int delta){}
	
	public void buff(){}
	public void debuff(){}
	
	public void onHit(Entity targetHit){}
	
	public void destroy(){}
	public void draw(){}

	public int getSkillId() {
		return skillId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public double getSpeed() {
		return speed;
	}

	public float getSize() {
		return size;
	}

	public float getRadius() {
		return radius;
	}

	public Entity getTarget() {
		return target;
	}

	public int getLevel() {
		return level;
	}

	public int getCastSpeed() {
		return castSpeed;
	}

	public boolean isActivated() {
		return activated;
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

//	protected boolean targetSkill=false;
//	protected boolean projectile=false;
//	protected boolean damageOverTime=false;
//	protected boolean areaOfEffect=false;
//	protected boolean meleeEffect=false;
	

}
