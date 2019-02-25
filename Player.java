package risk;

/* Freudenthal, Anne
 * Project, CS 201, Fall 2008
 * Player.java
 * 
 * 
 * This class plays the game against a human opponent.
 * 
 */


import java.util.*;

public class Player {
	private char name = 'Q';
	Random r = new Random();
	private int[] countries;		// This is an array of countries the computer occupies; if it occupies the country the space in the array is 1, otherwise 0.
	
	private double[][] occupancy;	// This makes an array of ratios -- it keeps track of how many people are in the computer's countries compared to the other
									// players' countries.  The ratio is weighted so that the computer knows when attacking is possibly beneficial and when it
									// isn't.
	
	// This method just gives the computer a name so it can make sure it's doing the right thing.
	public void makeName(char a) {
		name = a;
	}
	
	public char getName() {
		return name;
	}
	
	// This method chooses the countries at the beginning of the game.
	public int compChooseCountries(int k) {
		int countryChoice = r.nextInt(k) + 1;
		return countryChoice;
	}
	
	
	// This method fills the countries array.
	public void fillCountriesArray(char[][] gridp, int[][] gridc, int b, int d, int k) {
		countries = new int[k];
		for (int i = 0; i < b; i++) {
			for (int j = 0; j < d; j++) {
				if (gridp[i][j] == name) {
					countries[gridc[i][j] - 1] = 1;
				} else if (gridc[i][j] < 0) countries[gridc[i][j] - 1] = 0;
			}
		}
	}
	
	
	// This updates the countries array.
	public void updateCountriesArray(int i, int j) {
		countries[i] = j;
	}


	// This just makes the occupancy array.
	public void makeOccupancy(int a) {
		occupancy = new double[a][a];
	}
	
	
	// This makes an array with ratios of the computer's armies to the other players' armies.  It also updates the array
	// at the beginning of every time the computer plays a turn and every time the computer's turn changes the values.
	// Basically, the value in the occupancy is a function of the adjacency of the countries, who owns the countries, and
	// how many armies are in the countries.
	public void occupancy(int[][] adj, Country[] c) {
		occupancy = new double[countries.length][countries.length];
		
		for (int i = 0; i < countries.length; i++) {
			for (int j = 0; j < countries.length; j++) {
				occupancy[i][j] = ((double)adj[i][j])*((double)countries[i])*(((double)countries[j] + 1)%2)*((double)c[i].getBattalions() - 1)/((double)c[j].getBattalions() + 1);
			}
		}
	}

	
	// This picks a country at the beginning of the game.
	public int pickCountry(char[][] own, int[][] rep, int rows, int cols) {
		boolean a = true;
		int countryChoice = 0;
		while (a) {
			countryChoice = r.nextInt(countries.length) + 1;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if ((own[i][j] == name) && (rep[i][j] == countryChoice)){
						a = false;
					}
				}
			}
		}
		return countryChoice;
	}
	
	
	// This places armies at the beginning of the game.
	public int pickArmies(int k) {
		int m = Math.max(k/2, 1);
		int n = r.nextInt(m) + 1;
		return n;
	}
	
	
	// This chooses where to place the reinforcements at the beginning of the turn.
	// This is not an efficient way of doing this -- that is, it places one reinforcement in a country every
	// time this method is called.  That said, it's only going to have so many reinforcements to place at every
	// turn and the time won't be noticeable to the human opponent.  Allocating reinforcements based on ratios
	// makes it difficult to gauge (without directly calculating the difference) how the changed number of armies
	// in a country will change the ratios.  Therefore the safest way to do this -- as far as not using all of 
	// the reinforcements prematurely goes -- is to place only one reinforcement at a time, then recalculate the
	// occupancy matrix and decide where to place the next reinforcement based on the updated ratios.
	public int placeReinforcements(int[] on, Country[] c2) {
		int countryChoice = 0;
		double k = 1000;
		
		for (int i = 0; i < c2.length; i++) {
			for (int j = 0; j < c2.length; j++) {
				if ((occupancy[i][j] < k) && (occupancy[i][j] != 0)) {
					k = occupancy[i][j];
					countryChoice = i + 1;
				}
			}
		}
		return countryChoice;
	}
	

	public boolean attack() {
		boolean attack = false;
		for (int i = 0; i < countries.length; i++) {
			for (int j = 0; j < countries.length; j++) {
				if (occupancy[i][j] > .5) {
					attack = true;
				}
			}
		}
		return attack;
	}
	
	public int attackFrom(int[] on, Country[] c2) {
		int countryChoice = 0;
		double x = 0;
		for (int i = 0; i < c2.length; i++) {
			for (int j = 0; j < c2.length; j++) {
				if (occupancy[i][j] > x) {
					x = occupancy[i][j];
					countryChoice = i + 1;
				}
			}
		}
		return countryChoice;
	}

	public int attackTo(int[] on, Country[] c2) {
		int countryChoice = 0;
		double x = 0;
		for (int i = 0; i < c2.length; i++) {
			for (int j = 0; j < c2.length; j++) {
				if (occupancy[i][j] > x) {
					x = occupancy[i][j];
					countryChoice = j + 1;
				}
			}
		}
		return countryChoice;
	}
}
