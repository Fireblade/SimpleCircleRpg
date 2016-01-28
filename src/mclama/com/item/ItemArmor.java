package mclama.com.item;

public class ItemArmor extends Item {
	
	private int baseArmor=0;
	private int addedArmor=0;
	private float bonusArmor=0f;
	
	private int baseEvasion=0;
	private int addedEvasion=0;
	private float bonusEvasion=0f;
	
	private int baseShield=0;
	private int addedShield=0;
	private float bonusShield=0f;

	public ItemArmor(String name, int itemLevel, int foundLevel,int rarity) {
		super(name, itemLevel, foundLevel, rarity);
		armor=true;
	}


}
