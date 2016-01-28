package mclama.com.item;

public class Item {
	
	protected String name="noname";
	protected int itemLevel=0, foundLevel=0;
	protected int rarity=1;
	

	protected boolean armor=false;
	protected boolean weapon=false;
	protected boolean jewellery=false;
	protected boolean relic=false;
	protected boolean symbol=false;
	
	
	public Item(String name, int itemLevel, int foundLevel, int rarity){
		this.name = name;
		this.itemLevel=itemLevel;
		this.foundLevel=foundLevel;
		this.rarity=rarity;
	}
	
	
	
	
	
	
	/*
	 * 1-handed melee weapon
	 * 2-handed melee weapon
	 * 
	 * 1-handed ranged weapon
	 * 2-handed ranged weapon
	 * 
	 * Shield
	 * 
	 * Rings(2)
	 * Amulet
	 * Head
	 * Chest
	 * Belt
	 * Pants
	 * Gloves
	 * Boots
	 * 
	 * Relic(2)                      (effects)
	 * Symbol                        (Skill mod)
	*/
}
