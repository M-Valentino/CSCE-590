import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Methods {

	public void siftData(String parameterName, File csv, int number) throws FileNotFoundException {
		
		ArrayList<String> data = new ArrayList<String>();
		
		// For holding temporary string data from the nextLine() function 
		String tempLine = "";
		
		Scanner fileScanner = new Scanner(csv);
		
		boolean canAddHeader = true;
		
		while (fileScanner.hasNextLine()) {
			tempLine = fileScanner.nextLine();
			data.add(tempLine.toUpperCase());
			
		}// Ending bracket of while loop
		
		tempLine = "";
		
		fileScanner.close();
		
		PrintWriter writer = new PrintWriter(System.out);
		writer = new PrintWriter(new File(number + ".csv")); 
		
		for (int i = 0; i < data.size(); i++) {
			if (canAddHeader == true) {
				writer.write("﻿WBodyID,WaterBodyName,DataSource,StationID,StationName,Actual_StationID,Actual_Latitude,Actual_Longitude,DEP_WBID,SampleDate,ActivityDepth,DepthUnits,Parameter,Characteristic,Sample_Fraction,Result_Value,Result_Unit,QACode,Result_Comment,Original_Result_Value,Original_Result_Unit" + "\n");
				canAddHeader = false;
			}
			
			
			if (data.get(i).contains(parameterName)) {
				writer.write(data.get(i) + "\n");
			}// Ending bracket of if
		}// Ending bracket of for loop
		
		writer.flush();  
		writer.close();
		
		PrintWriter writerPython = new PrintWriter(System.out);
		writerPython = new PrintWriter(new File(number + ".py"));
		writerPython.write("import pandas as pd\n" + 
				"import plotly.express as px\n" + 
				"\n" + 
				"df = pd.read_csv('" + number + ".csv')\n" + 
						"\n" + 
						"fig = px.line(df, x = 'SampleDate', y = 'Result_Value', title = \"" + parameterName + "\")\n" + 
								"fig.show()");
		
		writerPython.flush();  
		writerPython.close();
	}// Ending bracket of method siftData
	

	
	
}// Ending bracket of class Methods
