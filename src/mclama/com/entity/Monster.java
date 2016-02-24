package mclama.com.entity;

import static mclama.com.util.Artist.tex_circle;
import static mclama.com.util.Globals.gGHighestPlayerLevel;

import java.util.ArrayList;
import java.util.Random;

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
	
	
	public void takeDamage(double[] damage, Player damagedBy){
		
		if(health <1){
			die(damagedBy);
		}
	}
	
	public void die(Player killedBy){
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
		
		System.out.println("drop: " + items_drop + ", Quantity: " + quantity + ", Rarity: " + rarity);
		
		items_drop = (int) Math.floor((items_drop * size) * (1+(quantity/100)));
		System.out.println("items Dropped: " + (int) items_drop);
		
		for(int i=0; i<items_drop; i++){
			item_drops.add(new Item("noname", level, rarity, whites));
		}
		
	}


	public ArrayList<Item> getItem_drops() {
		return item_drops;
	}

}
