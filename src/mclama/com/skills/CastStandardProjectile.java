package mclama.com.skills;

import static mclama.com.util.Utility.distance;

import mclama.com.entity.Entity;
import mclama.com.entity.Monster;

import static mclama.com.util.Globals.*;

public class CastStandardProjectile extends Skill implements ISkill {

	private boolean directionSkill=true;
	
	@Override
	public void onActivate() {
		//onActivate send projectile in a direction
		//when projectile hits an enemy, onHit
	}

	@Override
	public void tick(int delta) {

		if (currentLevel.validWalkable(x + (xVelocity * speed * delta), y + (yVelocity * speed * delta))) {
			x += xVelocity * speed * delta;
			y += yVelocity * speed * delta;
		} else {
			destroy(); //we hit out of bounds, lets destroy the projectile.
		}

		if(gameIsHosting){
			for(int i=0; i<gameClient.activeMonsters.size(); i++){
				Monster mons = gameClient.activeMonsters.get(i);
				if(distance(mons.getX(), mons.getY(), x, y) < (radius*size)){
					onHit(mons);
				}
			}
		}
		
	}

	@Override
	public void buff() {
		// TODO Auto-generated method stub

	}

	@Override
	public void debuff() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHit(Entity targetHit) {
		//monster takes damage, send damage report to players
		
		destroy(); //we hit our target, lets destroy us ;)
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		//destroy
	}

}
