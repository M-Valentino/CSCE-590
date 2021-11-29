public class City {
	
	private int income;
	private double costOfLiving;
	private int incomePerCOL = -1;
	private int population;
	private int crime;
	private int safety = -1;
	private int safetyRanking = -1;
	private int economyRanking = -1;
	private int magnitude = -1;
	private int relocationNumber = -1;
	private String name = "";
	private String state = "";
	private double stateCOL = -1;
	
	public City(String name, String state, int income, double costOfLiving, int population, int crime) {
		this.income = income;
		this.name = name;
		this.state = state;
		this.costOfLiving = costOfLiving;
		this.population = population;
		this.crime = crime;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getState() {
		return this.state;
	}
	
	public int getIncome() {
		return this.income;
	}
	
	public double getCostOfLiving() {
		return this.costOfLiving;
	}
	
	public int getIncomePerCOL() {
		return this.incomePerCOL;
	}
	
	public int getSafety() {
		return this.safety;
	}
	
	public int getPopulation() {
		return this.population;
	}
	
	public int getCrime() {
		return this.crime;
	}
	
	public void setStateCOL(double stateCOL) {
		this.stateCOL = stateCOL;
	}
	
	public void setCostOfLiving(double newCOL) {
		this.costOfLiving = newCOL;
	}
	
	public void setPopulation(int population) {
		this.population = population;
	}
	
	public void setCrime(int crime) {
		this.crime = crime;
	}
	
	public String toCSVRow() {
		return name + "," + state + "," + economyRanking + "," + incomePerCOL + "," + safetyRanking + "," + safety + "," + magnitude +
				"," + income + "," + costOfLiving + "," + stateCOL + "," + crime + "," + population + "," + relocationNumber;
	}
	
	public String toString() {
		return name + " ";
	}

}
