import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CombineData {

	public static void main(String[] args) {
		
		// Holds temporary data to be parsed from lines scanned by file scanner
		String tempLine = "";
		// Name of city
		String name = "";
		// State of city
		String state = "";
		// Median income of city
		String income = "";
		// Incremented after lines in a CSV is read. Used for ignoring lines that do not have relevant data.
		int lineCount = -1;
		// Incremented after commas are read. Used in determining data type.
		int commaCount = 0;
		// Holds all cities
		ArrayList<City> cities = new ArrayList<City>();
		// Object that contains useful methods
		Methods methods = new Methods();
		
		// Beginning of parsing File csv and adding cities to citiesCensus
		// CSV holds census data on median income.
		File csv = new File("twoColumnCensus.csv");
		try {
	    	Scanner fileScanner = new Scanner(csv);
	    	while (fileScanner.hasNextLine()) {
	    		tempLine = fileScanner.nextLine();
	    		lineCount++;
	    		if (lineCount > 1) {
	    			for (int i = 0; i < tempLine.length(); i++) {
	    				if (tempLine.charAt(i) == ',') {
	    					commaCount++;
	    				}
	    				if (commaCount == 1) {
	    					name += tempLine.charAt(i);
	    				}
	    				if (commaCount == 2) {
	    					state += tempLine.charAt(i);
	    				}
	    				if (commaCount == 3) {
	    					income += tempLine.charAt(i);
	    				}
	    			}// Ending bracket of for loop
	    			income = income.substring(1);
	    			name = name.substring(2);
	    			name = methods.fixSpecialNameCases(name);
	    			
	    			// Keeps state or states of cities while removing junk text
	    			state = state.substring(2);
	    			while (state.contains(" ")) {
	    				state = state.substring(0, state.length() - 1);
	    			}
	    			
	    			if (!tempLine.contains("&")) {
	    				// Values of -1 mean that there are no data for that data type.
	    				City city = new City(name, state, Integer.parseInt(income), -1, -1, -1);
	    				cities.add(city);
	    			}
	    			else {
	    				City city = new City(name, state, Integer.parseInt(income), -1, -1, -1);
	    				for (int i = 0; i < methods.decoupleCities(city).size(); i++) {
	    					cities.add(methods.decoupleCities(city).get(i));
	    				}
	    			}
	    			commaCount = 0;
		    		name = "";
		    		state = "";
		    		income = "";
	    		}
	    	}// Ending bracket of while loop
	    	fileScanner.close();
	    	lineCount = 0;
		  }// Ending bracket of try block
		  
		  catch (FileNotFoundException e) {
			  System.out.println("CSV file not found.");
			  e.printStackTrace();
		  }// Ending bracket of catch block
		// Ending of parsing File csv and adding cities to citiesCensus
		
		// Beginning of parsing File csv2 and adding cities to citiesCOL
		// CSV holding cost of living data
		File csv2 = new File("advisorsmith_cost_of_living_index.csv");
		// Holds cost of living of cities 
		ArrayList<City> citiesCOL = new ArrayList<City>();
		// Cost of living of city
		String costOfLiving = "";
		try {
	    	Scanner fileScanner = new Scanner(csv2);
	    	while (fileScanner.hasNextLine()) {
	    		tempLine = fileScanner.nextLine();
	    		lineCount++;
	    		if (lineCount > 1) {
	    			for (int i = 0; i < tempLine.length(); i++) {
	    				if (tempLine.charAt(i) == ',') {
	    					commaCount++;
	    				}
	    				if (commaCount == 0) {
	    					name += tempLine.charAt(i);
	    				}
	    				if (commaCount == 1) {
	    					state += tempLine.charAt(i);
	    				}
	    				if (commaCount == 2) {
	    					costOfLiving += tempLine.charAt(i);
	    				}
	    			}// Ending bracket of for loop
	    			name = methods.fixSpecialNameCases(name);
	    			City city = new City(name, state.substring(1), -1, Double.parseDouble(costOfLiving.substring(1)), -1, -1);
	    			citiesCOL.add(city);
	    			commaCount = 0;
		    		name = "";
		    		state = "";
		    		costOfLiving = "";
	    		}
	    	}// Ending bracket of while loop
	    	fileScanner.close();
	    	lineCount = 0;
		  }// Ending bracket of try block
		  
		  catch (FileNotFoundException e) {
			  System.out.println("CSV file not found.");
			  e.printStackTrace();
		  }// Ending bracket of catch block
		// Ending of parsing File csv2 and adding cities to citiesCOL
		
		// This sets COL to the cities array list.
		for (int i = 0; i < cities.size(); i++) {
			for (int j = 0; j < citiesCOL.size(); j++) {
				if (citiesCOL.get(j).getName().equals(cities.get(i).getName())) {
					if (citiesCOL.get(j).getState().equals(cities.get(i).getState())) {
						cities.get(i).setCostOfLiving(citiesCOL.get(j).getCostOfLiving());
						citiesCOL.remove(j);
					}
				}
			}// Ending bracket of inner for loop
		}// Ending bracket of outer for loop
		
//		System.out.println("The following loop was a test to see what cities were not added into the cities array list from the COL dataset.");
//		System.out.println("This is mostly due to unusual names in the Census dataset, and those names are changed when being added to cities array list with hard coding in the fixSpecialNameCases method.");
//		System.out.println("The rest of the cities on the COL dataset not added, were simply not on the Census dataset. Additionally, these remaining cities were not on the FBI dataset.");
//		for (int i = 0; i < citiesCOL.size(); i++) {
//			System.out.println(citiesCOL.get(i).toCSVRow());
//		}
		
		// Beginning of parsing File csv3 and adding cities to citiesCrimePopulation
		// CSV holding crime and population data
		File csv3 = new File("crime by city ucr fbi 2010.csv");
		// Holds crime and population of cities. 
		ArrayList<City> citiesCrimePopulation = new ArrayList<City>();
		// Population of city
		String population = "";
		// String to hold crime values of a city to be totaled up later. 
		String crime = "";
		// Holds temporary data of state for csv data on crime where state is not listed on every line
		String tempState = "";
		try {
	    	Scanner fileScanner = new Scanner(csv3);
	    	while (fileScanner.hasNextLine()) {
	    		tempLine = fileScanner.nextLine();
	    		lineCount++;
	    		if (lineCount > 4) {
	    			for (int i = 0; i < tempLine.length(); i++) {
	    				if (tempLine.charAt(i) == ',') {
	    					commaCount++;
	    				}
	    				if (commaCount == 1) {
	    					name += tempLine.charAt(i);
	    				}
	    				if (commaCount == 2) {
	    					population += tempLine.charAt(i);
	    				}
	    				if (commaCount >= 3) {
	    					crime += tempLine.charAt(i);
	    				}
	    				if (tempLine.charAt(0) != ',') {
	    					if (commaCount == 0) {
	    						tempState += tempLine.charAt(i);
	    					}
	    					if (commaCount == 1) {
	    						if (!tempState.contentEquals("")) {
	    							state = tempState;
	    						}
	    						tempState = "";
	    					}
	    				}// Ending bracket of (tempLine.charAt(0) != ',')
	    			}// Ending bracket of for loop
	    			
	    			if (!population.equals(",")) {
	    				name = name.substring(1);
	    				name = methods.removeNumberFromName(name);
	    				name = methods.fixSpecialNameCases(name);
	    				
	    				City city = new City(name, methods.getStateAbrev(state), -1, -1, Integer.parseInt(population.substring(1)), 
	    						methods.getTotalCrime(crime.substring(1)));
	    				citiesCrimePopulation.add(city);
	    			}
	    			commaCount = 0;
	    			name = "";
	    			population = "";
	    			crime = "";
	    		}// Ending bracket of if (lineCount > 4) 
	    	}// Ending bracket of while loop
	    	fileScanner.close();
	    	lineCount = 0;
	    	state = "";
		  }// Ending bracket of try block
		  
		  catch (FileNotFoundException e) {
			  System.out.println("CSV file not found.");
			  e.printStackTrace();
		  }// Ending bracket of catch block
		// Ending of parsing File csv3 and adding cities to citiesCrimePopulation
		
		// This sets crime and population to the cities array list.
		for (int i = 0; i < cities.size(); i++) {
			for (int j = 0; j < citiesCrimePopulation.size(); j++) {
				if (citiesCrimePopulation.get(j).getName().equals(cities.get(i).getName())) {
					if (citiesCrimePopulation.get(j).getState().equals(cities.get(i).getState())) {
						cities.get(i).setPopulation(citiesCrimePopulation.get(j).getPopulation());
						cities.get(i).setCrime(citiesCrimePopulation.get(j).getCrime());
						citiesCrimePopulation.remove(j);
					}
				}
			}// Ending bracket of inner for loop
		}// Ending bracket of outer for loop		
		
//		System.out.println("The following was a test to see what crime and popluation statistics were not added to the cities in the cities array list.");
//		System.out.println("Sometimes the names of counties were added with a dash to the name of the city and special cases to names were updated.");
//		for (int i = 0; i < citiesCrimePopulation.size(); i++) {
//			if (citiesCrimePopulation.get(i).getName().contains("-")) {
//				System.out.println(citiesCrimePopulation.get(i).toCSVRow());
//			}
//		}
		

		// Beginning of parsing File csv4 and updating the cities' state COLs
		// CSV holding cost of living data for states
		File csv4 = new File("meric_mo_gov_COL_by_state.csv");

		String stateCOL = "";
		try {
	    	Scanner fileScanner = new Scanner(csv4);
	    	while (fileScanner.hasNextLine()) {
	    		tempLine = fileScanner.nextLine();
	    		lineCount++;
	    		if (lineCount > 1) {
	    			for (int i = 0; i < tempLine.length(); i++) {
	    				if (tempLine.charAt(i) == ',') {
	    					commaCount++;
	    				}
	    				if (commaCount == 1) {
	    					state += tempLine.charAt(i);
	    				}
	    				if (commaCount == 2) {
	    					stateCOL += tempLine.charAt(i);
	    				}
	    			}// Ending bracket of for loop
	    			
	    			// trim.() does not appear to work on parsed states. Probably due special space character
	    			state = methods.getStateAbrev(state.substring(1, state.length() -1).toUpperCase());
	    			
	    			for (int j = 0; j < cities.size(); j++) {
	    				if (cities.get(j).getState().equals(state)) {
	    					cities.get(j).setStateCOL(Double.parseDouble(stateCOL.substring(1)));
	    				}
	    			}// Ending bracket of for loop
	    			for (int j = 0; j < citiesCOL.size(); j++) {
	    				if (citiesCOL.get(j).getState().equals(state)) {
	    					citiesCOL.get(j).setStateCOL(Double.parseDouble(stateCOL.substring(1)));
	    				}
	    			}// Ending bracket of for loop
	    			for (int j = 0; j < citiesCrimePopulation.size(); j++) {
	    				if (citiesCrimePopulation.get(j).getState().equals(state)) {
	    					citiesCrimePopulation.get(j).setStateCOL(Double.parseDouble(stateCOL.substring(1)));
	    				}
	    			}// Ending bracket of for loop
	    			
	    			commaCount = 0;
		    		state = "";
		    		stateCOL = "";
	    		}
	    	}// Ending bracket of while loop
	    	fileScanner.close();
	    	lineCount = 0;
		  }// Ending bracket of try block
		  
		  catch (FileNotFoundException e) {
			  System.out.println("CSV file not found.");
			  e.printStackTrace();
		  }// Ending bracket of catch block
		// Ending of parsing File csv4
		
		
		// Outputs combined results of cities to CSV
		try {
            FileWriter fw = new FileWriter("CitiesCombinedData.csv");
            fw.write("Name,State,EconomyRanking,Economy,SafetyRanking,Safety,Magnitude,Income,CostOfLiving,StateCOL,Crime,Population,RelocationNumber\n");
            for (int i = 0; i < cities.size(); i++) {
            	fw.write(cities.get(i).toCSVRow() + "\n");
    		}
            System.out.println("Successfully created CitiesCombinedData.csv");
            fw.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
		
		// Outputs leftover cities in COL statistics to CSV
		try {
            FileWriter fw = new FileWriter("LeftoverCitiesCOLStats.csv");
            fw.write("Name,State,EconomyRanking,Economy,SafetyRanking,Safety,Magnitude,Income,CostOfLiving,StateCOL,Crime,Population,RelocationNumber\n");
            for (int i = 0; i < citiesCOL.size(); i++) {
            	fw.write(citiesCOL.get(i).toCSVRow() + "\n");
    		}
            System.out.println("Successfully created LeftoverCitiesCOLStats.csv");
            fw.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
		
		// Outputs leftover cities in FBI statistics to CSV
		try {
            FileWriter fw = new FileWriter("LeftoverCitiesFBIStats.csv");
            fw.write("Name,State,EconomyRanking,Economy,SafetyRanking,Safety,Magnitude,Income,CostOfLiving,StateCOL,Crime,Population,RelocationNumber\n");
            for (int i = 0; i < citiesCrimePopulation.size(); i++) {
            	fw.write(citiesCrimePopulation.get(i).toCSVRow() + "\n");
    		}
            System.out.println("Successfully created LeftoverCitiesFBIStats.csv");
            fw.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
		
	}

}
