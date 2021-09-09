import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadCSV {
	
	public static void main(String[] args) {
	  
	  // File to be parsed
	  File csv = new File("DataDownLoad-1111.csv");
	  // Text data from csv file to be filtered and dumped to
	  String data = "";
	  // For holding temporary string data from the nextLine() function 
	  String temp = "";
	  
	  
	  try {
		  
    	Scanner fileScanner = new Scanner(csv);
    	
    	while (fileScanner.hasNextLine()) {
    		
    		temp = fileScanner.nextLine();
    		
    		/* Only data from Istokpoga lake is being analyzed. Rows of data from another body
    		 * of water made its way into the csv so it has to be filtered out. 
    		 */
    		if (temp.contains("Istokpoga")) {
    			data += temp + "\n";
    		}// Ending bracket of if
    		
    	}// Ending bracket of while loop
    	
    	fileScanner.close();
    	
	  }// Ending bracket of try block
	  
	  catch (FileNotFoundException e) {
		  System.out.println("File not found.");
		  e.printStackTrace();
	  }// Ending bracket of catch block
	  
	  System.out.println(data);
	  
	}// Ending bracket of method main
  
}// Ending bracket of class ReadCSV
