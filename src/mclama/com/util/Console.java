package mclama.com.util;

import java.util.ArrayList;

public class Console {
	
	private static int show_lines=20;
	
	private static ArrayList<String> logs = new ArrayList<String>();
	private static String filter = "";
	
	private static String consoleLine = "";
	
	
	public static void con(String text){
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
		// Check if command, if command return true.
		logs.add(" > " + consoleLine);
		consoleLine = "";
		return false;
	}
	
	public static ArrayList<String> conGetConsole(){
		ArrayList<String> logView = new ArrayList<String>();
		int Remaining = show_lines;
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
		String[] textFilter = line.split(",");
		for(int i=0; i<textFilter.length; i++){
			if(line.contains(textFilter[i]))
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
