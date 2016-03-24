package mclama.com.skills;

import mclama.com.entity.Entity;

import static mclama.com.util.Globals.targetFPS;
import static mclama.com.util.Globals.myPlayer;

public abstract class Skill {
	
	protected double x, y, moveX, moveY;
	protected double speed=0.15f, xVelocity, yVelocity;
	
	protected float size=1;
	protected float width=32, height=32;
	protected float radius=32;
	
	protected Entity target;
	
	protected int level=0;
	protected int tickCount=0;
	protected int castSpeed = targetFPS;
	
	protected boolean activated=false;

//	protected boolean targetSkill=false;
//	protected boolean projectile=false;
//	protected boolean damageOverTime=false;
//	protected boolean areaOfEffect=false;
//	protected boolean meleeEffect=false;
	

}
