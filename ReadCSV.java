import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class ReadCSV {
	
	public static void main(String[] args) throws IOException {
	  Methods methods = new Methods();
		
	  // File to be parsed
	  File csv = new File("WaterAtlas-OneLake.csv");
	  
	  File parameterList = new File("count_of_unique_parameters.txt");
	 
	  ArrayList<String> data = new ArrayList<String>();
	  
	  ArrayList<String> parameters = new ArrayList<String>();
	  // For holding temporary string data from the nextLine() function 
	  String tempLine = "";
	  
	  int lineCount = 0;
	  
	  String[] cmd = new String[]{"/bin/sh", "list_parameters.sh"};
	  Process pr = Runtime.getRuntime().exec(cmd);
	  
	  try {
		
    	Scanner fileScanner = new Scanner(csv);
    	
    	while (fileScanner.hasNextLine()) {
    		tempLine = fileScanner.nextLine();
    		data.add(tempLine);
    		lineCount++;
    	}// Ending bracket of while loop
    	
    	lineCount = 0;
    	tempLine = "";
    	
    	fileScanner.close();
    	
	  }// Ending bracket of try block
	  
	  catch (FileNotFoundException e) {
		  System.out.println("CSV file not found.");
		  e.printStackTrace();
	  }// Ending bracket of catch block
	  
	  try {
			
    	Scanner fileScanner = new Scanner(parameterList);
    	
    	while (fileScanner.hasNextLine()) {
    		tempLine = fileScanner.nextLine();
    		parameters.add(tempLine);
    		lineCount++;
    	}// Ending bracket of while loop
    	
    	lineCount = 0;
    	tempLine = "";
    	
    	fileScanner.close();
	    	
	  }// Ending bracket of try block
	  
	  catch (FileNotFoundException e) {
		  System.out.println("TXT file not found.");
		  e.printStackTrace();
	  }// Ending bracket of catch block
	  
	  for (int i = 0; i < parameters.size(); i++) {
		  methods.siftData(parameters.get(i), csv, i);
	  }
	  
	  
	}// Ending bracket of method main
  
}// Ending bracket of class ReadCSV

