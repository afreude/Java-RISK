package risk;

/* Freudenthal, Anne
 * Project, CS 201, Fall 2008
 * File.java
 * 
 * 
 * This class reads the file with the continents and countries and creates two string arrays with the information about 
 * the continents and the countries in them.  The continent array has the name of the continent, the symbol for that
 * continent, and the value of occupying that continent.  The country array has the name of the country, the integer label 
 * of the continent, the continent, the continent symbol, the row index, and the column index.
 * 
 */

import java.util.*;
import java.io.*;

public class File {
	String[][] continents;
	String[][] countries;
	int a;
	int b;
	
	public void readFile(String filename) throws IOException {
		String line;
		
		Scanner file = new Scanner(new FileReader(filename));
		
		line = file.nextLine();
		line = line.substring(line.indexOf(",") + 1, line.length());
		a = Integer.parseInt(line);
		
		continents = new String[a][3];
		
		for (int i = 0; i < a; i++) {
			line = file.nextLine();
			continents[i][0] = line.substring(0, line.indexOf(",")); //continent name
			
			line = line.substring(line.indexOf(",") + 1, line.length());
			continents[i][1] = line.substring(0, line.indexOf(",")); //continent label
			
			continents[i][2] = line.substring(line.indexOf(",") + 1, line.length()); //continent worth
		}
		
		line = file.nextLine();
		line = line.substring(line.indexOf(",") + 1, line.length());
		b = Integer.parseInt(line);
		
		countries = new String[b][6];
		
		for (int i = 0; i < b; i++) {
			line = file.nextLine();
			countries[i][0] = line.substring(0, line.indexOf(",")); //country name
			
			countries[i][1] = "" + (i + 1); //country integer label
			
			line = line.substring(line.indexOf(",") + 1, line.length());
			countries[i][2] = line.substring(0, line.indexOf(",")); //country continent
			
			countries[i][3] = ""; //continent symbol
			for (int j = 0; j < a; j++) {
				if (countries[i][2].equals(continents[j][0])) {
					countries[i][3] = continents[j][1];
				}
			}
			
			line = line.substring(line.indexOf(",") + 1, line.length());
			countries[i][4] = line.substring(0, line.indexOf(",")); //row index
			
			line = line.substring(line.indexOf(",") + 1, line.length());
			countries[i][5] = line; //column index
		}
		
	}
	
	public String[][] getContinents() {
		return continents;
	}
	
	public String[][] getCountries() {
		return countries;
	}
	

}
