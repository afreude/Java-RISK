package risk;

/* Freudenthal, Anne
 * Project, CS 201, Fall 2008
 * Continent.java
 * 
 * 
 * This class creates Continent objects.
 * 
 */


public class Continent {
	
	private String name;
	private char symbol;
	private int worth;
	static int howMany = 0;
	
	public Continent(String n, char s, int w) {
		name = n;
		symbol = s;
		worth = w;
		howMany++;
	}
	
	public String getName() {
		return name;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public int getWorth() {
		return worth;
	}
	
	// Unnecessary.
	public String setName(String n) {
		if ((n.equals("")) || (n.equals(null))) {
			System.out.println("Invalid Continent Name.");
		} else name = n;
		return name;
	}
	
	// Unnecessary.
	public char setSymbol(char s) {
		symbol = s;
		return symbol;
	}
	
	// Unnecessary.
	public int setWorth(int w) {
		if (w < 0) {
			System.out.println("Invalid Continent Value.");
		} else worth = w;
		return worth;
	}
	
	
	public String toString() {
		String stuff = "\t Continent Name: " + name + "\r\n \t Continent Symbol: " + symbol + "\r\n \t Continent Value: " + worth;
		return stuff;
	}
	
	
	
	

}
