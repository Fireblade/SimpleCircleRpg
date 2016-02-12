package mclama.com.item;

public class ItemArmor extends Item {
	

	public ItemArmor(String name, int itemLevel, int foundLevel,int rarity) {
		super(name, foundLevel, rarity, 1);
		armor=true;
	}


}
