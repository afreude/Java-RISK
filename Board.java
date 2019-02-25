package risk;

/* Freudenthal, Anne
 * Project, CS 201, Fall 2008
 * Board.java
 * 
 * 
 * This class creates a board for the game.  This board displays the countries, the owners of the countries, and how many
 * battalions are in each country.  Adjacent countries on the board can attack each other.  Each country has a String 
 * representation (e.g. !A5), a row index, and a column index.  In addition, each country has a number (starting with 1) 
 * which is assigned based on the order they come in the file.  This appears on the board as well; to choose a country via
 * scanner, the player can simply type in the integer label of the country.
 * 
 * This class also makes four other grids: a character grid which stores the positions of the continents, a character grid
 * which stores the positions of the players in countries, an integer array which stores the positions of battalions/armies
 * in countries (i.e. it stores a number in the position of the country that corresponds to the number of battalions in that
 * country), and an integer array with the integer label of the country in the country's position.
 * 
 */

public class Board {
	
	private char[][] grids;
	private char[][] gridp;
	private int[][] gridb;
	private int[][] gridc;
	private String[][] grid;
	
	private int[][] adj;
	private int[][] occ;
	private int rows;
	private int columns;
	private int length;
	
	
	public Board(int i, int j) {
		rows = i;
		columns = j;
		
		grids = new char[rows][columns];
		gridp = new char[rows][columns];
		gridb = new int[rows][columns];
		gridc = new int[rows][columns];
		
		
		grid = new String[rows+2][columns+1];
			grid[0][0] = "   ";
			for (int m = 1; m < rows+2; m++) {
				grid[m][0] = "" + m + "  ";
			}
			for (int m = 1; m < columns+1; m++) {
				grid[0][m] = "   " + (char)(m + 64) + "   ";
			}
			
			for (int m = 1; m < rows+2; m++) {
				for (int n = 1; n < columns+1; n++) {
					grid[m][n] = "       ";
				}
			}
		
		
		length = Country.howMany;
		adj = new int[length][length];
		for (int k = 0; k < length; k++) {
			for (int l = 0; l < length; l++) {
				adj[k][l] = 0;
			}
		}
		
		occ = new int[length][length];
		for (int k = 0 ; k < length; k++) {
			for (int l = 0; l < length; l++) {
				occ[k][l] = 0;
			}
		}
	}
	
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}
	public int getLength() {
		return length;
	}
	//This fills the grid as it is called.
	public void fillGrid(int n, int i, int j, char name, char owner, int batt) {
		grids[i][j] = name;
		gridp[i][j] = owner;
		gridb[i][j] = batt;
		gridc[i][j] = n;
		
		
		grid[i][j + 1] = "" + n + " " + name + owner + batt;
	}
	
	public char[][] getGrids() {
		return grids;
	}
	public char[][] getGridp() {
		return gridp;
	}
	public int[][] getGridb() {
		return gridb;
	}
	public int[][] getGridc() {
		return gridc;
	}
	public String[][] getGrid() {
		return grid;
	}
	
	/* This makes an adjacency matrix -- e.g. if country i and country j are adjacent then the entry in row i and column j will be a 1
	*  and the entry in row j and column i will also be a 1.  Countries are "adjacent" to themselves; obviously one cannot attack
	*  oneself.
	*
	*  Also, this is a HUGE mess.  I'm sure there's a better way of doing it, but I don't know what it is.
	*/
	public void makeAdj() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				for (int n = 1; n < length + 1; n++) {
					
					if (gridc[i][j] == n) {
						for (int m = 1; m < length + 1; m++) {
							if ((i != 0) && (i != rows - 1) && (j == 0)) { //left side of array
								if (gridc[i][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][columns - 1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][columns - 1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][columns - 1] == m) {
									adj[n][m] = 1;
								} else if (gridc[i-1][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else adj[n-1][m-1] = 0;
							} else if ((i == 0) && (j == 0)) { //top left corner
								if (gridc[i][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][columns - 1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][columns - 1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else adj[n-1][m-1] = 0;
							} else if ((i != 0) && (i != rows - 1) && (j == columns - 1)) { //right side of array
								if (gridc[i][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][0] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][0] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][0] == m) {
									adj[n-1][m-1] = 1;
								} else adj[n-1][m-1] = 0;
							} else if ((i == 0) && (j == columns - 1)){ //top right corner of array
								if (gridc[i][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][0] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][0] == m) {
									adj[n-1][m-1] = 1;
								} else adj[n-1][m-1] = 0;
							} else if ((i == rows - 1) && (j == 0)){ // bottom left corner of array
								if (gridc[i][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][columns - 1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][columns - 1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else adj[n-1][m-1] = 0;
							} else if ((i == rows - 1) && (j == columns - 1)) { // top right corner of array
								if (gridc[i][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][0] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][0] == m) {
									adj[n-1][m-1] = 1;
								} else adj[n-1][m-1] = 0;
							} else if ((i == 0) && ((j != 0) && (j != columns - 1))) { // top side of array
								if (gridc[i][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else adj[n-1][m-1] = 0;
							} else if ((i == rows - 1) && ((j != 0) && (j != columns - 1))) { // bottom side of array
								if (gridc[i][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else adj[n-1][m-1] = 0;
							} else {									//everything else
								if (gridc[i][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i+1][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j-1] == m) {
									adj[n-1][m-1] = 1;
								} else if (gridc[i-1][j+1] == m) {
									adj[n-1][m-1] = 1;
								} else adj[n-1][m-1] = 0;
							}
						}
					}
				}
			}
		}
	}
	
	public int[][] getAdj() {
		return adj;
	}
	//Unnecessary, I just wanted a way to see it so I could make sure it was working right.
	public String adjToString() {
		String ugh = "";
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				ugh = ugh + "  " + adj[i][j];
			}
			ugh = ugh + "\r\n";
		}
		return ugh;
	}
	
	//This finds the countries which are adjacent to the country with label k.
	public int[] getAdj(int k) {
		int[] m = new int[9];
		int j = 0;
		
		for (int i = 0; i < length; i++) {
			if (adj[k][i] == 1) {
				m[j] = i;
				j++;
			}
		}
		
		return m;
	}
	
	// This makes a String of the countries which are adjacent to the country with the label k.
	public String getAdjString(int k) {
		String stuff = "";
		for (int i = 0; i < length; i++) {
			if (adj[k-1][i] == 1) {
				stuff = stuff + "  " + (i + 1);
			}
		}
		return stuff;
	}
	
	
	//This updates the grid after battles, etc.
	public void updateGrid(int i, int j, char owner, int batt) {
		gridp[i][j] = owner;
		gridb[i][j] = batt;
		
		if (grid[i][j+1].length() == 5) {
			grid[i][j+1] = grid[i][j+1].substring(0,3) + owner + batt;
		} else grid[i][j+1] = grid[i][j+1].substring(0,4) + owner + batt;
	}
	
	
	public String toString() {
		
		String stuff = "\r\n";
		for (int i = 0; i < rows + 1; i++) {
			for (int j = 0; j < columns + 1; j++) {
				if (grid[i][j].length() == 5) {
					stuff = stuff + "    " + grid[i][j];
				} else if (grid[i][j].length() == 6) {
					stuff = stuff + "   " + grid[i][j];
				} else stuff = stuff + "  " + grid[i][j];
			}
			stuff = stuff + "\r\n \r\n";
		}
		return stuff;
	}

}
