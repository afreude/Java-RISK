package risk;

/* Freudenthal, Anne
 * Project, CS 201, Fall 2008
 * Game.java
 * 
 * 
 * This is the class that runs the whole thing.  It prompts the players for a file name, whether they want the computer
 * to play or not, and how many players total are playing.  After that it runs the game based on how many are playing.
 * 
 */

import java.io.*;

public class Game {
	
	public static void main(String[] args) throws IOException {
		PlayGame pg = new PlayGame();
		
		pg.setUpGame();
		
		pg.computer();
		
		pg.players();
		
		pg.rules();
		
		pg.chooseCountries();
		
		pg.fillCountries();
		
		pg.restOfGame();
		
		
	}

}
