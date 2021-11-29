import java.io.File;
import java.util.ArrayList;

public class Methods {
	
	public String getStateAbrev(String name) {
		String rv = "null";
		if(name.equals("ALABAMA")) {
			rv = "AL";
		}
		if(name.equals("ALASKA")) {
			rv = "AK";
		}
		if(name.equals("ARIZONA")) {
			rv = "AZ";
		}
		if(name.equals("ARKANSAS")) {
			rv = "AR";
		}
		if(name.equals("CALIFORNIA")) {
			rv = "CA";
		}
		if(name.equals("COLORADO")) {
			rv = "CO";
		}
		if(name.equals("CONNECTICUT")) {
			rv = "CT";
		}
		if(name.equals("DELAWARE")) {
			rv = "DE";
		}
		if(name.equals("DISTRICT OF COLUMBIA")) {
			rv = "DC";
		}
		if(name.equals("FLORIDA")) {
			rv = "FL";
		}
		if(name.equals("GEORGIA")) {
			rv = "GA";
		}
		if(name.equals("HAWAII")) {
			rv = "HI";
		}
		if(name.equals("IDAHO")) {
			rv = "ID";
		}
		if(name.equals("ILLINOIS")) {
			rv = "IL";
		}
		if(name.equals("INDIANA")) {
			rv = "IN";
		}
		if(name.equals("IOWA")) {
			rv = "IA";
		}
		if(name.equals("KANSAS")) {
			rv = "KS";
		}
		if(name.equals("KENTUCKY")) {
			rv = "KY";
		}
		if(name.equals("LOUISIANA")) {
			rv = "LA";
		}
		if(name.equals("MAINE")) {
			rv = "ME";
		}
		if(name.equals("MARYLAND")) {
			rv = "MD";
		}
		if(name.equals("MASSACHUSETTS")) {
			rv = "MA";
		}
		if(name.equals("MICHIGAN")) {
			rv = "MI";
		}
		if(name.equals("MINNESOTA")) {
			rv = "MN";
		}
		if(name.equals("MISSISSIPPI")) {
			rv = "MS";
		}
		if(name.equals("MISSOURI")) {
			rv = "MO";
		}
		if(name.equals("MONTANA")) {
			rv = "MT";
		}
		if(name.equals("NEBRASKA")) {
			rv = "NE";
			}
		if(name.equals("NEVADA")) {
			rv = "NV";
		}
		if(name.equals("NEW HAMPSHIRE")) {
			rv = "NH";
		}
		if(name.equals("NEW JERSEY")) {
			rv = "NJ";
		}
		if(name.equals("NEW MEXICO")) {
			rv = "NM";
		}
		if(name.equals("NEW YORK")) {
			rv = "NY";
		}
		if(name.equals("NORTH CAROLINA")) {
			rv = "NC";
		}
		if(name.equals("NORTH DAKOTA")) {
			rv = "ND";
		}
		if(name.equals("OHIO")) {
			rv = "OH";
		}
		if(name.equals("OKLAHOMA")) {
			rv = "OK";
		}
		if(name.equals("OREGON")) {
			rv = "OR";
		}
		if(name.equals("PENNSYLVANIA")) {
			rv = "PA";
		}
		if(name.equals("RHODE ISLAND")) {
			rv = "RI";
		}
		if(name.equals("SOUTH CAROLINA")) {
			rv = "SC";
		}
		if(name.equals("SOUTH DAKOTA")) {
			rv = "SD";
		}
		if(name.equals("TENNESSEE")) {
			rv = "TN";
		}
		if(name.equals("TEXAS")) {
			rv = "TX";
		}
		if(name.equals("UTAH")) {
			rv = "UT";
		}
		if(name.equals("VERMONT")) {
			rv = "VT";
		}
		if(name.equals("VIRGINIA")) {
			rv = "VA";
		}
		if(name.equals("WASHINGTON")) {
			rv = "WA";
		}
		if(name.equals("WEST VIRGINIA")) {
			rv = "WV";
		}
		if(name.equals("WISCONSIN")) {
			rv = "WI";
		}
		if(name.equals("WYOMING")) {
			rv = "WY";
		}
		
		return rv;
	}
	
	public String fixSpecialNameCases(String inputName) {
		String rv = inputName;
		switch(inputName) {
			case "Louisville/Jefferson County":
				rv = "Louisville";
				break;
			case "Urban Honolulu":
				rv = "Honolulu";
				break;
			case "Athens-Clarke County":
				rv = "Athens";
				break;
			case "Augusta-Richmond County":
				rv = "Augusta";
				break;
			// Fayette is the county Lexington is in.
			case "Lexington-Fayette":
				rv = "Lexington";
				break;
			case "Nashville-Davidson":
				rv = "Nashville";
				break;
			// Greenfield, MA is listed as Greenfield Town in the COL dataset
			case "Greenfield Town":
				rv = "Greenfield";
				break;
			case "Vero Beach South":
				rv = "Vero Beach";
				break;
				
			// Start of special cases in the FBI dataset.
			// Chatham is the county Savannah is in.
			case "Savannah-Chatham Metropolitan":
				rv = "Savannah";
				break;
			// Mecklenburg is the county Charlotte is in.
			case "Charlotte-Mecklenburg":
				rv = "Charlotte";
				break;
			// Snowflake, AZ borders Taylor AZ, and the FBI dataset has both statistics for the cities combined.
			// Taylor, AZ is excluded because only the FBI dataset mentions it.
			case "Snowflake-Taylor":
				rv = "Snowflake";
				break;
			case "East Aurora-Aurora Town":
				rv = "East Aurora";
				break;
			// Newberg and Dundee are both in Oregon and are close to each other. The FBI dataset has both
			// statistics for the cities combined. Dundee is excluded because only the FBI dataset mentions it.
			case "Newberg-Dundee":
				rv = "Newberg";
				break;
		}
		return rv;
	}
	
	public ArrayList<City> decoupleCities(City coupledCities) {
		ArrayList<City> rv = new ArrayList<City>();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> states = new ArrayList<String>();
		int loopLength = coupledCities.getName().length();
		String coupledName = coupledCities.getName();
		String tempString = "";
		
		if (coupledCities.getName().contains("&")) {
			for (int i = 0; i < loopLength; i++) {
				if (coupledName.charAt(i) != '&') {
					tempString+= coupledName.charAt(i);
				}
				if (i == loopLength - 1 || coupledName.charAt(i) == '&') {
					tempString = fixSpecialNameCases(tempString);
					names.add(tempString);
					tempString = "";
				}
			}
		}
		else {
			names.add(coupledCities.getName());
		}
		
		if (coupledCities.getState().contains("&")) {
			String coupledState = coupledCities.getState();
			loopLength = coupledCities.getState().length();
			for (int i = 0; i < loopLength; i++) {
				if (coupledState.charAt(i) != '&') {
					tempString+= coupledState.charAt(i);
				}
				if (i == loopLength - 1 || coupledState.charAt(i) == '&') {
					states.add(tempString);
					tempString = "";
				}
			}
		}
		else {
			states.add(coupledCities.getState());
		}
		
		for (int i = 0; i < names.size(); i++) {
			for (int j = 0; j < states.size(); j++) {
				City city = new City(names.get(i), states.get(j), coupledCities.getIncome(), -1, -1, -1);
				rv.add(city);
			}
		}
		
		return rv;
	}
	
	public String removeNumberFromName(String inputName) {
		String rv = inputName;
		if (inputName.charAt(inputName.length()-1) == '0' || 
				inputName.charAt(inputName.length()-1) == '1' || 
				inputName.charAt(inputName.length()-1) == '2' ||
				inputName.charAt(inputName.length()-1) == '3' || 
				inputName.charAt(inputName.length()-1) == '4' || 
				inputName.charAt(inputName.length()-1) == '5' ||
				inputName.charAt(inputName.length()-1) == '6' ||
				inputName.charAt(inputName.length()-1) == '7' || 
				inputName.charAt(inputName.length()-1) == '8' ||
				inputName.charAt(inputName.length()-1) == '9') {
			rv = rv.substring(0, rv.length() - 1);
			
		}
		return rv;
	}
	
	public int getTotalCrime(String crimeValues) {
		// Total crime to return
		int rv = 0;
		// Temporary string value holding individual types of crime
		String tempNum = "";
		//,135,0,11,13,111,1251,221,979,51,8
		for (int i = 0; i < crimeValues.length(); i++) {
			if (crimeValues.charAt(i) == ',' && !tempNum.equals("")) {
				if (!tempNum.equals(",")) {
					rv += Integer.parseInt(tempNum);
					tempNum = "";
				}
			}
			else {
				tempNum += crimeValues.charAt(i);

			}
		}
		if (!tempNum.equals("") ) {
			rv += Integer.parseInt(tempNum);
		}
		return rv;
	}

}
