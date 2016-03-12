package mclama.com.item;

import java.util.ArrayList;
import java.util.Random;

import static mclama.com.util.Globals.*;

public class Item {
	
	protected String name="noname";
	protected int itemLevel=0, foundLevel=0;
	protected int rarity=0;
	
	protected int vArmor=0; //Value of Armor
	protected int vEvasion=0;
	protected int vShield=0;
	
	protected int vHealth=0;
	
	protected int vD4DmgDice=0;
	protected int vD6DmgDice=0;
	protected int vD8DmgDice=0;
	protected int vD10DmgDice=0;
	protected int vD12DmgDice=0;
	

	protected boolean armor=false;
	protected boolean weapon=false;
	protected boolean jewellery=false;
	protected boolean relic=false;
	protected boolean symbol=false;
	
	private ArrayList<String> mods = new ArrayList<String>(); //like 20 increased damage /n 10 increased armor
	
	public Item(String name, int foundLevel, int drop_rarity) {
		this(name, foundLevel, drop_rarity, new float[] {1f, 1f, 1f, 1f, 1f, 1f} ,0);
	}
	
	public Item(String name, int foundLevel, int drop_rarity, float[] rarityMods){
		this(name, foundLevel, drop_rarity, rarityMods,0);

	}
	
	public Item(String name, int foundLevel, int drop_rarity, float[] rarityMods, int createRarity){
		this.name = name;
		this.foundLevel=foundLevel;
		
		Random gen = new Random();
		
		int item1Rarity;
		if(createRarity>0){ // 0 means item creation, so roll.
			item1Rarity = rollItemRarity(rarityMods);
			while(gen.nextFloat()*100 <= (1+(drop_rarity/100))*1.0f){
				int item2Rarity = rollItemRarity(rarityMods);
				System.out.println("rolled bonus..." + item1Rarity + " vs " + item2Rarity);
				item1Rarity = Math.max(item1Rarity, item2Rarity); //use the higher rarity of the 2.
			}
		} else
		{
			item1Rarity = createRarity;
		}
		
		
		rarity = item1Rarity;
		itemLevel += item1Rarity - 1;
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
	 * Relic(2)                      (Passive boosts)
	 * Symbol                        (Skill boosts)
	 * 
	 * //61, 9, 23, 7,1 
	//9d4, 1d6, 3d8, 1d10, 1d12
	//15-88,
	//8, 4, 2, 1, 0
	
	//1: normal            -- no affix                          -- item level + 0 (50)  x1.00
	//2: magic / enchanted -- 2  affix                          -- item level + 1 (85)  x1.25
	//3: rare              -- 4  affix                          -- item level + 2 (95)  x1.50
	//4: legendary / epic  -- 6  affix                          -- item level + 3 (98)  x1.75
	//5: unique            -- 4  unique-only affix's            -- item level + 4 (99)  x2.00
	//6: cursed            -- 4  affix + 4 unique-only affix's  -- item level + 5 (100) x3.00
	
	//Sacred gear -- Every gear has a chance of being part of a "set" that has random set-bonus affix's
	
	//item types
	//Helm, Chest, Gloves, Boots, Pants
	//Ring, Ring, Amulet, Relic
	*/

	


	private int rollItemRarity(float[] rarityMods){
		int itemRarity = 0;
		// base item rarity rolls
		int rNormal = 20000;         //1
		int rMagic = 4000;           //2
		int rRare = 800;             //3
		int rLeg = 160;               //4
		int rUnique = 32;             //5
		int rCursed = 3;              //6
		
		rNormal *= rarityMods[RARITY_WHITE];
		rMagic *= rarityMods[RARITY_BLUE];
		rRare *= rarityMods[RARITY_GREEN];
		rLeg *= rarityMods[RARITY_YELLOW];
		rUnique *= rarityMods[RARITY_ORANGE];
		rCursed *= rarityMods[RARITY_PURPLE];

		float NormalMultiplier = 1;
		float MagicMultiplier = 1;
		float RareMultiplier = 1;
		float LegMultiplier = 1;
		float UniqueMultiplier = 1;
		float CursedMultiplier = 1;
		

		rNormal *= NormalMultiplier;
		rMagic *= MagicMultiplier;
		rRare *= RareMultiplier;
		rLeg *= LegMultiplier;
		rUnique *= UniqueMultiplier;
		rCursed *= CursedMultiplier;

		int rTotal = rNormal + rMagic + rRare + rLeg + rUnique + rCursed;

		int rarityRoll = gen.nextInt(rTotal)+1;

		if (rarityRoll <= rNormal)
			itemRarity = 1;
		else if (rarityRoll <= rNormal + rMagic)
			itemRarity = 2;
		else if (rarityRoll <= rNormal + rMagic + rRare)
			itemRarity = 3;
		else if (rarityRoll <= rNormal + rMagic + rRare + rLeg)
			itemRarity = 4;
		else if (rarityRoll <= rNormal + rMagic + rRare + rLeg + rUnique)
			itemRarity = 5;
		else if (rarityRoll <= rNormal + rMagic + rRare + rLeg + rUnique + rCursed)
			itemRarity = 6;
		else
			itemRarity = 0;

		//System.out.println("Rarity: " + itemRarity);
		return itemRarity;
	}


	public void addMod(String mod){
		mods.add(mod);
	}

	public String getName() {
		return name;
	}




	public int getItemLevel() {
		return itemLevel;
	}




	public int getRarity() {
		return rarity;
	}




	public ArrayList<String> getMods() {
		return mods;
	}
}
