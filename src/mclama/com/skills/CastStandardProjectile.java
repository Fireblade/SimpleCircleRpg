package mclama.com.skills;

import static mclama.com.util.Utility.distance;

import mclama.com.Network.SendDamageDealt;
import mclama.com.Network.PlayerSkillCasted;
import mclama.com.entity.Entity;
import mclama.com.entity.Monster;

import static mclama.com.util.Artist.DrawQuadTex;
import static mclama.com.util.Artist.tex_circle_ring_outer;
import static mclama.com.util.Globals.*;

public class CastStandardProjectile extends Skill implements ISkill {

	private boolean directionSkill=true;
	
	public CastStandardProjectile(int plyrId, double x, double y, double targetx, double targety){
		this(plyrId,gameClient.newSkillUsed(), x, y, targetx, targety);
		//Send spell cast to others.
		PlayerSkillCasted msg = new PlayerSkillCasted();
		msg.playerId = plyrId;
		msg.skillId=this.skillId;
		msg.spawnx = x;
		msg.spawny = y;
		msg.targetx = targetx;
		msg.targety = targety;
		
		gClient.sendUDP(msg);
	}
	
	public CastStandardProjectile(int plyrId, int skillId, double x, double y, double targetx, double targety){
		this.playerId = plyrId;
		this.skillId = skillId;
		this.x=x;
		this.y=y;
		this.moveX=targetx;
		this.moveY=targety;
		
		onActivate();
	}
	
	@Override
	public void onActivate() {
		calculateDirection(moveX, moveY); //one time thing
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
				if(mons.isAlive()){
					if(distance(mons.getX(), mons.getY(), x, y) < (radius*size)){
						onHit(mons);
						break;
					}
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
		//calculate damage
		double[] damage = {gen.nextInt(10)+8}; //add minimum damage, while rolling the seperated damage
		
		SendDamageDealt damageMsg = new SendDamageDealt();
		//damageMsg.levelId = currentLevel.getId();
		damageMsg.monId = targetHit.getId();
		damageMsg.damage = damage;
		damageMsg.damagedByPlayer = playerId;
		
		gClient.sendUDP(damageMsg);
		
		destroy(); //we hit our target, lets destroy us ;)
	}

	@Override
	public void destroy() {
		gameClient.skillDestroyed(playerId, skillId);
	}

	@Override
	public void draw() {
		DrawQuadTex(tex_circle_ring_outer, x, y, width*size, height*size);
	}

}
