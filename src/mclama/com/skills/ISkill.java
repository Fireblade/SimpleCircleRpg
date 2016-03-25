package mclama.com.skills;

import mclama.com.entity.Entity;

public abstract interface ISkill {

	boolean projectile=false;
	boolean targetSkill=false;
	boolean directionSkill=false;
	boolean damageOverTime=false;
	boolean areaOfEffect=false;
	boolean meleeEffect=false;
	
	public void onActivate();
	void tick(int delta);
	
	public void buff();
	public void debuff();
	
	void onHit(Entity targetHit);
	
	void destroy();
	void draw();


}
