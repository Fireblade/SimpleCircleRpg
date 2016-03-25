package mclama.com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import mclama.com.entity.Player;

import static mclama.com.util.Globals.*;

public class Utility {
	
	public static DecimalFormat df = new DecimalFormat("#.##");
	
	/**
	 * Returns the required experience for the next level up
	 * @param xp
	 * @param level
	 * @return
	 */
	public static double experienceForNextlevel(int level){
		return ((250 * level) * Math.pow(Math.max(1.15 - level/500, (1.05) ), level));
	}
	
    public static boolean directoryExists(String directory)
    {
        // Check if the directory exists, if not, create it
        boolean result = true;
        File dirTest = new File(directory);
        if (!dirTest.exists())
        {
            try
            {
                dirTest.mkdir();
                result = true;
            } catch (SecurityException e)
            {
                e.printStackTrace();
            }

            // Failed to create directory!
            if (!result)
            {
                System.out.println("Failed to create directory! " + directory);
            }
        }

        return result;
    }
	
	public static void saveCharacter(){
		if(gameIsHosting){ //Temp so i can see whats going on
			//File file = new File("Data/Characters/" + myPlayer.name + ".txt");
			File file = new File("Data/Characters/playersave.txt");
			try {
				PrintWriter out = new PrintWriter(file);
				
				out.println("Name :: " + myPlayer.name);
				out.println("Level :: " + myPlayer.getLevel());
				out.println("Experience :: " + myPlayer.getExperience());
				out.println("Total Experience :: " + myPlayer.getTotalExperience());
				out.println("Skill Points :: " + "0");
				out.println("Skill Points Total:: " + "0");
				out.println("Skills Unlocked :: " + "strength_5, dexterity_1, melee splash_10");
				out.println("Equipped Gear :: " + "][slot_weapon, weapon, damage_49-60, attack speed_1.5, mods_none][");
				out.println("Inventory :: " + "chest, armor_5][relic, stength_12][relic, mod_5% increased damage");
				
				out.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static Player loadCharacter(String charactername) {
		System.out.print("Loading character... " + charactername);
		Player player = new Player();
		File file = new File("Data/Characters/" + charactername + ".txt");
		if(file.exists()){
			try (BufferedReader br = new BufferedReader(new FileReader(file));)
			{
				try { /*player.name = */br.readLine();}catch (Exception e) {} //ignore name till further update
				try { player.setLevel(Integer.parseInt(br.readLine().split(" :: ")[1]));}catch (Exception e) {}
				try { player.setExperience(Double.parseDouble(br.readLine().split(" :: ")[1]));}catch (Exception e) {}
				try { player.setTotalExperience(Double.parseDouble(br.readLine().split(" :: ")[1]));}catch (Exception e) {}
				try { player.setSkillPoints(Integer.parseInt(br.readLine().split(" :: ")[1]));}catch (Exception e) {}
				try { player.setTotalSkillPoints(Integer.parseInt(br.readLine().split(" :: ")[1]));}catch (Exception e) {}
				
				
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		System.out.print( "   ...done.\n");
		return player;
	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		double xDistanceFromTarget = Math.abs(x1 - x2);
		double yDistanceFromTarget = Math.abs(y1 - y2);
		return xDistanceFromTarget + yDistanceFromTarget;
	}
	
	public static float distanceX(double d, double angle){
		return (float) (d * Math.sin(Math.toRadians(angle)));
	}
	public static float distanceY(double distance, double angle){
		return (float) (distance * Math.cos(Math.toRadians(angle)));
	}

}
