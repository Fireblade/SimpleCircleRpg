package mclama.com.util;

import static mclama.com.util.Globals.*;

import java.util.ArrayList;

import mclama.com.item.Item;

public class Console {
	
	private static int show_lines=20;
	
	private static ArrayList<String> logs = new ArrayList<String>();
	private static String filter = "";
	
	private static String consoleLine = "";
	
	
	public static void conAdd(String text){
		logs.add(text);
	}
	
	public static void conLineAddChar(char text){
		consoleLine += text;
	}
	
	public static void conLineBackspace() {
		if (consoleLine != null && consoleLine.length() > 0) {
			consoleLine = consoleLine.substring(0, consoleLine.length() - 1);
		}
	}
	
	public static boolean conCommandEntered(){
		boolean isCommand=false;
		String[] command = consoleLine.split(" ");
		String additional = "";
		switch(command[0].toLowerCase()){
		case "filter":
			if(command.length>1){
				setFilter(consoleLine.replace("filter " , ""));
			}
			else setFilter("");
			isCommand=true;
			break;
		case "give":
			if(command.length==2){
				if (command[1].equals("itemdrop")) {
					Item iDrop = new Item("noname", myPlayer.getLevel(), 0, 1.0f);
					currentLevel.addNewItemDrop(iDrop, myPlayer.getX(), myPlayer.getY());
					
				}
				isCommand = true;
				
			}
			break;
		case "developer":
			if(command.length==2){
				if(command[1].matches("0|false")){
					gShowDeveloperConsole = false;
				}
				else if(command[1].matches("1|true")){
					gShowDeveloperConsole = true;
				}
				isCommand=true;
			}else additional = "- (1|0) toggles a small developer console view";
			break;
		}
		logs.add(" > " + consoleLine + " " + additional);
		consoleLine = "";
		return isCommand;
	}
	
	public static ArrayList<String> conGetConsole(){
		return conGetConsole(show_lines);
	}
	
	public static ArrayList<String> conGetConsole(int count){
		ArrayList<String> logView = new ArrayList<String>();
		int Remaining = count;
		for(int i=logs.size()-1; i>(-1); i--){
			String line = logs.get(i);
			
			if(filteredLine(line)){
				logView.add(line);
				Remaining--;
			}
			if(Remaining <= 0){
				break;
			}
		}
		
		return logView;
	}

	private static boolean filteredLine(String line) {
		if(filter.equals("")) return true;
		String[] textFilter = filter.split(",");
		for(int i=0; i<textFilter.length; i++){
			if(line.toLowerCase().contains(textFilter[i].toLowerCase()))
				return true;
		}
		
		return false;
	}
	
	public static void setFilter(String text){
		filter = text;
	}

	public static String conGetConsoleLine() {
		return consoleLine;
	}

}
