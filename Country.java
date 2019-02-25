package risk;

/* Freudenthal, Anne
 * Project, CS 201, Fall 2008
 * Country.java
 * 
 * 
 * This class creates a country object.  Each country has a name, an integer label for the computer player, a continent name,
 * a continent symbol, an owner, a row index (for position on board, to display on board), a column letter index (for position
 * on board), a column number index (to display on board), the number of battalions in the country, and a static int to keep 
 * track of how many countries have been created.
 * 
 */

public class Country {
	
	private String countryName;			//country name
	private int ith;					//int label for country
	private String continentName;		//name of continent the country 
	private char continentSymbol;		//symbol of country's continent
	private char owner;					//owner of continent
	
	private int rowIndex;				//row index of continent on board
	private char columnLetter;			//column index (letter) of continent on board
	private int columnIndex;			//column index (number) of continent on board
	
	private int battalions;				//number of battalions currently in country
	
	static int howMany;			//how many countries there are in this game
	
	
	
	// This makes a country object.  Each country has a name, an integer representation, a continent, a continent symbol, an
	// owner, a row index (integer), a column letter (character), a column index (integer), and the number of armies (battalions)
	// in the country.
	public Country(String countn, int i, String contn, char sym, char own, int ri, char cl, int b) {
		countryName = countn;
		ith = i;
		continentName = contn;
		continentSymbol = sym;
		owner = own;
		
		rowIndex = ri;
		columnLetter = cl;
		columnIndex = (char)(cl) - 65;
		
		battalions = b;
		
		howMany++;
	}
	
	// This returns the name of the country.
	public String getCountryName() {
		return countryName;
	}
	
	// This returns the integer representation of the country.
	public int getith() {
		return ith;
	}
	
	// This returns the name of the continent the country is on.
	public String getContinentOn() {
		return continentName;
	}
	
	// This returns the symbol of the continent the country is on.
	public char getContinentSym() {
		return continentSymbol;
	}
	
	// This returns the player currently occupying the country.
	public char getOwner() {
		return owner;
	}
	
	// This returns the row index (integer) of the country.
	public int getRowIndex() {
		return rowIndex;
	}
	
	// This returns the letter associated with the column index.
	public char getColumnLetter() {
		return columnLetter;
	}
	
	// This 
	public int getColumnIndex() {
		return columnIndex;
	}
	
	public int getBattalions() {
		return battalions;
	}
	
	//-------------------------------------------
	
		//unnecessary
		public String setCountryName(String countn) {
			if ((countn.equals("")) || (countn.equals(null))) {
				System.out.println("Invalid Country Name.");
			} else countryName = countn;
			return countryName;
		}
		
		//unnecessary
		public int setith(int i) {
			if (i < 0) {
				System.out.println("Invalid Country Number.");
			} else ith = i;
			return ith;
		}
		
		//unnecessary
		public String setContinentOn(String contn) {
			if ((contn.equals("")) || (contn.equals(null))) {
				System.out.println("Invalid Continent Name.");
			} else continentName = contn;
			return continentName;
		}
		
		//unnecessary
		public char setContinentSym(char sym) {
			continentSymbol = sym;
			return continentSymbol;
		}
	
	//this will be used to update the owner of the country after a battle
	public char setOwner(char own) {
		owner = own;
		return owner;
	}
		
		//unnecessary
		public int setRowIndex(int ri) {
			if ((ri < 0) || (ri > 9)) {
				System.out.println("Invalid Row Index.");
			} else rowIndex = ri;
			return rowIndex;
		}
	
		//unnecessary
		public char setColumnLetter(char cl) {
			columnLetter = cl;
			setColumnIndex((int)(cl) - 65);
			return columnLetter;
		}
		
		//unnecessary
		public int setColumnIndex(int ci) {
			columnIndex = ci;
			return columnIndex;
		}
	
	//this will be updated during battles
	public int setBattalions(int b) {
		if (battalions < 0) {
			System.out.println("Invalid Number of Battalions.");
		} else battalions = b;
		return battalions;
	}
	
	
	public String toString() {
		String stuff = "\t Country name: " + countryName + "\r\n \t Continent: " + continentName
						+ "\r\n \t Current Owner: " + owner + "\r\n \t Number of Battalions Currently in " + countryName + ": " + battalions;
		return stuff;
	}

}
