package mclama.com.util;

import java.util.ArrayList;

public class Console {
	
	private static int show_lines=20;
	
	private static ArrayList<String> logs = new ArrayList<String>();
	private static String filter = "";
	
	
	public static void con(String text){
		logs.add(text);
	}
	
	public static ArrayList<String> conGetConsole(){
		ArrayList<String> logView = new ArrayList<String>();
		
		for(int i=logs.size(); i>0; i--){
			String line = logs.get(i);
			
			if(filteredLine(line)){
				logView.add(line);
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
	

}
