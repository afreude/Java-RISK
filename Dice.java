package risk;

/* Freudenthal, Anne
 * Project, CS 201, Fall 2008
 * Dice.java
 * 
 * 
 * This class rolls dice.
 * 
 */

import java.util.*;

public class Dice {
	
	public int rollDie() {
		Random r = new Random();
		return r.nextInt(6);
	}
	
	

}
