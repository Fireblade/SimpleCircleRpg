package mclama.com.util;

import java.text.DecimalFormat;

public class Utility {
	
	public static DecimalFormat df = new DecimalFormat("#.##");
	
	public static int getNextLevel(double xp){
		int level = 0;
		
		int reqxp=1;
//		while(xp>reqxp){
//			level++;
//			reqxp = (int) ((8*(level-1)*8) + (reqxp*1.50));
//			System.out.println(level + " .. reqXp: " + reqxp + " .. xpReward: " + (level*level));
//		}
		
		return level;
	}

}
