package mclama.com.item;

public class ItemArmor extends Item {
	

	public ItemArmor(String name, int itemLevel, int foundLevel,int rarity) {
		super(name, foundLevel, rarity, new float[] {1f, 1f, 1f, 1f, 1f, 1f});
		armor=true;
	}


}
