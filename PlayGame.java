package risk;

/* Freudenthal, Anne
 * Project, CS 201, Fall 2008
 * PlayGame.java
 * 
 * 
 * This class runs the game.
 * 
 */


import java.util.*;
import java.io.*;

public class PlayGame {
	Scanner in = new Scanner(System.in);
	
	private String[][] conts;
	private String[][] counts;
	
	private Continent[] continents;
	private Country[] countries;
	private char[] people;

	private boolean comp;
	private int first = 0;
	private int many;
	private int[] armies;
	private int k = 0;
	
	Dice die = new Dice();
	Board b;
	Player computer = new Player();
	
	
	// This reads the file and sets up the board, etc.
	public void setUpGame() throws IOException {
		System.out.print("Please enter the file name: ");
		String filename = in.nextLine();
		
		File f = new File();
		f.readFile(filename);
			conts = f.getContinents();
			counts = f.getCountries();
			
			continents = new Continent[f.a];
			countries = new Country[f.b];
			
			for (int i = 0; i < f.a; i++) {
				continents[i] = new Continent(conts[i][0], conts[i][1].charAt(0), Integer.parseInt(conts[i][2]));
			}
			
			int cols = 0;
			int rows = 0;
			for (int i = 0; i < f.b; i++) {
				countries[i] = new Country(counts[i][0], Integer.parseInt(counts[i][1]), counts[i][2], counts[i][3].charAt(0), '_', 
													Integer.parseInt(counts[i][4]), counts[i][5].charAt(0), 0);
				if (((int)(counts[i][5].charAt(0)) - 65) > cols) {
					cols = (int)(counts[i][5].charAt(0)) - 64;
				}
				if (Integer.parseInt(counts[i][4]) > rows) {
					rows = Integer.parseInt(counts[i][4]);
				}
			}
			
			b = new Board(rows + 1, cols + 1);
			for (int i = 0; i < f.b; i++) {
				b.fillGrid(countries[i].getith(), countries[i].getRowIndex(), countries[i].getColumnIndex(), countries[i].getContinentSym(), '_', 0);
			}
			b.makeAdj();
	}
	
	// This decides if the computer is playing or not.
	public void computer() {
		System.out.print("Do you want the computer to play as a player? (Y/N): ");
		String yes = in.nextLine();
		
		if ((yes.equals("Y")) || (yes.equals("y")) || (yes.equals("Yes")) ||(yes.equals("yes")) || (yes.equals("YEs")) || (yes.equals("YES"))) {
			comp = true;
		} else comp = false;
	}
	
	// This makes a character array that keeps track of the players
	public void players() {
		System.out.print("How many players including the computer? (2-6): ");
		String number = in.nextLine();
		boolean a = true;
		int p = -3;
		while (a) {
			p = -3;
			try {
				p = Integer.parseInt(number);
				if ((p < 2) || (p > 6)) {
					System.out.println("Invalid number of players; number must be between two and six.");
					System.out.print("How many players including the computer? (2-6): ");
					number = in.nextLine();
				} else {
					a = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid number of players.  Please enter an integer between two and six. ");
				System.out.print("How many players including the computer? (2-6): ");
				number = in.nextLine();
			}
		}
		
		many = p;
		
		switch (many) {
		case 2: {
				first = die.rollDie()%2;
				people = new char[2];
				people[first] = 'A';
				people[(1 + first)%2] = 'B';
				armies = new int[many];
				for (int i = 0; i < many; i++) {
					armies[i] = 40;
				}
			} break;
		case 3: {
				first = die.rollDie()%3;
				people = new char[3];
				people[first] = 'A';
				people[(1 + first)%3] = 'B';
				people[(2 + first)%3] = 'C';
				armies = new int[many];
				for (int i = 0; i < many; i++) {
					armies[i] = 35;
				}
			} break;
		case 4: {
				first = die.rollDie();
				while (first > 3) {
					first = die.rollDie();
				}
				people = new char[4];
				people[first] = 'A';
				people[(1 + first)%4] = 'B';
				people[(2 + first)%4] = 'C';
				people[(3 + first)%4] = 'D';
				armies = new int[many];
				for (int i = 0; i < many; i++) {
					armies[i] = 30;
				}
			} break;
		case 5: {
				first = die.rollDie();
				while (first > 4) {
					first = die.rollDie();
				}
				people = new char[5];
				people[first] = 'A';
				people[(1 + first)%5] = 'B';
				people[(2 + first)%5] = 'C';
				people[(3 + first)%5] = 'D';
				people[(4 + first)%5] = 'E';
				armies = new int[many];
				for (int i = 0; i < many; i++) {
					armies[i] = 25;
				}
			}	break;
		case 6: {
				first = die.rollDie();
				people = new char[6];
				people[first] = 'A';
				people[(1 + first)%6] = 'B';
				people[(2 + first)%6] = 'C';
				people[(3 + first)%6] = 'D';
				people[(4 + first)%6] = 'E';
				people[(5 + first)%6] = 'F';
				armies = new int[many];
				for (int i = 0; i < many; i++) {
					armies[i] = 20;
				}
			} break;
		default: {
				many = 3;
				first = die.rollDie()%3;
				people = new char[many];
				people[first] = 'A';
				people[(1 + first)%3] = 'B';
				people[(2 + first)%3] = 'C';
				armies = new int[many];
				for (int i = 0; i < many; i++) {
					armies[i] = 35;
				}
			}
		}
		System.out.println("Player " + (char)(first + 65) + " goes first.");
		if (comp) computer.makeName(people[(first - 1 + many)%many]);
	}
	
	// This displays the rules.  Sort of.  Basic rules anyway.
	public void rules() {
		System.out.println("\r\n");
		System.out.println("To choose a country, type the integer representation of the country (the first integer in the");
		System.out.println("country's spot on the board). \r\n");
		System.out.println("To attack a country, the country you are attacking must be adjacent on the board to the country");
		System.out.println("you are attacking from. \r\n");
		System.out.println("At the beginning of each turn, you will receive one reinforcement for every three countries you");
		System.out.println("occupy.  This does not take into account any remainder; e.g. if you occupy seven countries, you");
		System.out.println("will receive two reinforcements at each turn. \r\n");
		System.out.println("If you occupy an entire continent, you will receive extra reinforcements at each turn.  This");
		System.out.println("number varies depending on the continent you occupy. \r\n");
	}
	
	// This lets the players choose which countries they want to start in.
	public void chooseCountries() {
		boolean a = true;
		String stuff;
		int l;
		System.out.println(b.toString());
		for (int i = 0; i < Country.howMany;  i++) {
			System.out.println("Player " + people[k] + "'s turn.");
			while ((a) && (people[k] != computer.getName())) {
					System.out.print("Player " + people[k] + ", please choose a country to place an army in: ");
					stuff = in.nextLine();
					try {
						l = Integer.parseInt(stuff);
						if ((l > 0) && (l < Country.howMany + 1)) {
							if (countries[l-1].getOwner() != '_') {
								System.out.println("Someone is already in that country, please pick an unoccupied country.");
							} else {
								countries[l-1].setOwner(people[k]);
								countries[l-1].setBattalions(1);
								armies[k] = armies[k] - 1;
								b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], 1);
								System.out.println(b.toString());
								a = false;
							}
						} else System.out.println("Invalid number.  Please enter a number between 1 and " + Country.howMany + ".");
					} catch (NumberFormatException e) {
						System.out.println("You must enter the integer representation of the country.  Please try again.");
					}
				
			}
			
			while ((a) && (comp) && (people[k] == computer.getName())) {
				l = computer.compChooseCountries(Country.howMany);
				if (countries[l-1].getOwner() == '_') {
					countries[l-1].setOwner(people[k]);
					countries[l-1].setBattalions(1);
					armies[k] = armies[k] - 1;
					b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], 1);
					System.out.println(b.toString());
					a = false;
				}
			}
			
			a = true;
			k = (k + 1)%many;
		}
		if (comp) computer.fillCountriesArray(b.getGridp(), b.getGridc(), b.getRows(), b.getColumns(), Country.howMany);
	}
		
	// This lets the players fill their countries with their remaining armies.
	public void fillCountries() {
		String stuff;
		int l;
		int m;
		
		for (int i = 0; i < people.length; i++) {
			System.out.println("Player " + people[k] + "'s turn.");
			while ((armies[k] != 0) && (people[k] != computer.getName())) {
					System.out.println("Player " + people[k] + ", you have " + armies[k] + " armies left to place.");
					System.out.print("Please choose a country to place armies in: ");
					stuff = in.nextLine();
					try {
						l = Integer.parseInt(stuff);
						if ((l > 0) && (l <= Country.howMany)) {
							if (countries[l-1].getOwner() != people[k]) {
								System.out.println("This isn't your country!  Please choose your own country.");
							} else {
								System.out.print("Please choose a number of armies to put in your country: ");
								stuff = in.nextLine();
								try {
									m = Integer.parseInt(stuff);
									if ((m > 0) && (m < armies[k] + 1)) {
										armies[k] = armies[k] - m;
										countries[l-1].setBattalions((countries[l-1].getBattalions() + m));
										b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], countries[l-1].getBattalions());
										System.out.println(b.toString());
									} else {
										System.out.println("You must choose a number between 1 and " + armies[k] + ".");
									}
								} catch (NumberFormatException e) {
									System.out.println("Please choose an integer number of armies to put in your country.");
								}
							}
						}
					} catch (NumberFormatException e) {
						System.out.println("You must enter the integer representation of the country.  Please try again.");
					}

			}
			
			while ((armies[k] != 0) && (comp) && (people[k] == computer.getName())) {
				l = computer.pickCountry(b.getGridp(), b.getGridc(), b.getRows(), b.getColumns());
				m = computer.pickArmies(armies[k]);
				
				armies[k] = armies[k] - m;
				countries[l-1].setBattalions(countries[l-1].getBattalions() + m);
				b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], countries[l-1].getBattalions());
				System.out.println(b.toString());
			}
			k = (k + 1)%many;
		}	
	}
	
	// This makes an array of how many countries are on a continent.
	public int[] makeOn() {
		int[] on = new int[Continent.howMany];
		for (int p = 0; p < Continent.howMany; p++) {
			for (int i = 0; i < b.getRows(); i++) {
				for (int j = 0; j < b.getColumns(); j++) {
					if (b.getGrids()[i][j] == continents[p].getSymbol()) {
						on[p] = on[p] + 1;
					}
				}
			}
		}
		return on;
	}
	
	// This awards reinforcements, allows attacking, and lets players fortify new territories.
	// Also, this is a ridiculous huge mess.  I might fix it if I have time.  It does what it's supposed to though.
	public void restOfGame() {
		int[] on = makeOn();
		boolean a = true;
		boolean c = true;
		boolean d = true;
		int f = 0;
		String stuff;
		int l;
		int m;
		
		int x; // How many countries a player owns.
		int y; // How many countries in a given continent a player owns.
		
		int z1;
		int z2;
		
		int s;
		int t;
		int u;
		int v;
		int w;
		
		while (a) {
			stuff = "no";
			d = true;
			c = true;
			// Award reinforcements

				// This figures out how many countries a player occupies.
				x = 1;
				if (f > many - 1) {
					x = 0;
					for (int i = 0; i < b.getRows(); i++) {
						for (int j = 0; j < b.getColumns(); j++) {
							if (b.getGridp()[i][j] == people[k]) {
								x++;
							}
						}
					}
					if (x == Country.howMany) {
						a = false;
						c = false;
						d = false;
					}
				
					armies[k] = armies[k] + x/3;
			
			
					// This figures out how many total continents a player occupies.
					for (int p = 0; p < Continent.howMany; p++) {
						y = 0;
						for (int i = 0; i < b.getRows(); i++) {
							for (int j = 0; j < b.getColumns(); j++) {
								if ((b.getGrids()[i][j] == continents[p].getSymbol()) && (b.getGridp()[i][j] == people[k])) {
									y++;
								}
							}
						}
						if (y == on[p]) armies[k] = armies[k] + continents[p].getWorth();
					}
				
					if ((x != 0) && (armies[k] < 3)) armies[k] = 3; 
					if (x == 0) c = false;
				}
				f++;
			
				//-------------------------------------------------------------------------------------
			
				// Place reinforcements
			
				while ((armies[k] != 0) && (people[k] != computer.getName())) {
					System.out.println("Player " + people[k] + "'s turn. \r\n");
					System.out.println("Player " + people[k] + " has " + armies[k] + " armies left to place.");
					System.out.print("Please choose a country to place armies in: ");
					stuff = in.nextLine();
					try {
						l = Integer.parseInt(stuff);
					
						if ((l > 0) && (l <= Country.howMany)) {
							if (countries[l-1].getOwner() == people[k]) {
								System.out.print("Please enter how many armies you want to place in this country: ");
								stuff = in.nextLine();
								try {
									m = Integer.parseInt(stuff);
									if ((m > 0) && (m < armies[k] + 1)) {
										armies[k] = armies[k] - m;
										countries[l-1].setBattalions((countries[l-1].getBattalions() + m));
										b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], countries[l-1].getBattalions());
										System.out.println(b.toString()); 
									} else {
										System.out.println("Please enter a number between 1 and " + armies[k] + ".");
									}
								} catch (NumberFormatException e) {
									System.out.println("Please enter an integer number of armies.");
								}
							} else {
								System.out.println("Not your country!  Please choose your own country!");
							}
						} else {
							System.out.println("Please enter a number between 1 and " + Country.howMany + ".");
						}
					} catch (NumberFormatException e) {
						System.out.println("Please enter the integer representation of the country.");
					}
				
				}
				
				while ((armies[k] != 0) && (comp) && (people[k] == computer.getName())) {
					l = computer.placeReinforcements(on, countries);
					m = 1;
					armies[k] = armies[k] - m;
					countries[l-1].setBattalions(countries[l-1].getBattalions() + m);
					if (comp) computer.occupancy(b.getAdj(), countries);
					b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], countries[l-1].getBattalions());
				}
				System.out.println(b.toString());
			
				//--------------------------------------------------------------------------------------
			
				// Option to attack
				while ((c) && (people[k] != computer.getName())) {
					stuff = "no";
					System.out.print("Player " + people[k] + ", would you like to attack? (Y/N): ");
					stuff = in.nextLine();
					if ((stuff.equals("Y")) || (stuff.equals("y")) || (stuff.equals("Yes")) || (stuff.equals("yes")) || (stuff.equals("YEs")) || (stuff.equals("YES"))) {
						c = true;
						d = true;
						stuff = "no";
						a = true;
					} else {
						c = false;
						d = false;
					}
				
					while (d) {
						System.out.print("Please enter the country you'd like to attack from: ");
						stuff = in.nextLine();
						try {
							l = Integer.parseInt(stuff);
							if ((l > 0) && (l < Country.howMany)) {
								if ((countries[l-1].getOwner() == people[k]) && (countries[l-1].getBattalions() > 1)) {
									System.out.println("Countries you can attack: " + b.getAdjString(l));
									System.out.print("Please enter the country you'd like to attack: ");
									stuff = in.nextLine();
									try {
										m = Integer.parseInt(stuff);
										if ((m > 0) && (m < Country.howMany) && (b.getAdj()[l-1][m-1] == 1)) {
											if (countries[m-1].getOwner() != people[k]) {
												stuff = "yes";
												while ((stuff.equals("Y")) || (stuff.equals("y")) || (stuff.equals("YES")) || (stuff.equals("YEs")) || (stuff.equals("Yes")) || (stuff.equals("yes"))) {
													switch (countries[l-1].getBattalions()) {
													case 2: {
														u = die.rollDie() + 1;
														v = 0;
														w = 0;
													} break;
													case 3: {
														u = die.rollDie() + 1;
														v = die.rollDie() + 1;
														w = 0;
													} break;
													default: {
														u = die.rollDie() + 1;
														v = die.rollDie() + 1;
														w = die.rollDie() + 1;
													}
													}
												
													switch (countries[l-1].getBattalions()) {
													case 1: {
														s = die.rollDie() + 1;
														t = 0;
													} break;
													default: {
														s = die.rollDie() + 1;
														t = die.rollDie() + 1;
													}
													}
											
													z1 = 0; // Attacker
													z2 = 0; // Defender
												
													z1 = Math.max(Math.max(u, v), w);
													z2 = Math.max(s, t);
													System.out.println("Player " + people[k] + " rolled a " + z1 + ".");
													System.out.println("Player " + countries[m-1].getOwner() + " rolled a " + z2 + ".");
												
													if (z1 > z2) {
														System.out.println("Player " + countries[m-1].getOwner() + " loses.");
														countries[m-1].setBattalions(countries[m-1].getBattalions() - 1);
														b.updateGrid(countries[m-1].getRowIndex(), countries[m-1].getColumnIndex(), countries[m-1].getOwner(), countries[m-1].getBattalions() - 1);
														if (countries[m-1].getBattalions() == 0) {
															System.out.println("Player " + people[k] + " wins.");
															countries[m-1].setOwner(people[k]);
															countries[m-1].setBattalions(1);
															countries[l-1].setBattalions(countries[l-1].getBattalions() - 1);
															stuff = "no";
															d = false;
														}
													} else {
														System.out.println("Player " + people[k] + " loses.");
														countries[l-1].setBattalions(countries[l-1].getBattalions() - 1);
														b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], countries[l-1].getBattalions());
														if (countries[l-1].getBattalions() == 1) {
															System.out.println("Player " + people[k] + " is done attacking.");
															stuff = "no";
															d = false;
														}
													}
												
													z1 = Math.max(Math.min(Math.max(u, v), w), Math.max(Math.min(Math.max(u, w), v), Math.min(Math.max(w, v), u)));
													z2 = Math.min(s, t);
													if ((countries[m-1].getOwner() != people[k]) && (countries[l-1].getBattalions() != 1)) {
														if ((z1 != 0) && (z2 != 0)) {
															if (z1 > z2) {
																System.out.println("Player " + people[k] + " rolled a " + z1 + ".");
																System.out.println("Player " + countries[m-1].getOwner() + " rolled a " + z2 + ".");
																System.out.println("Player " + countries[m-1].getOwner() + " loses.");
																countries[m-1].setBattalions(countries[m-1].getBattalions() - 1);
																b.updateGrid(countries[m-1].getRowIndex(), countries[m-1].getColumnIndex(), countries[m-1].getOwner(), countries[m-1].getBattalions());
																if (countries[m-1].getBattalions() == 0) {
																	System.out.println("Player " + people[k] + " wins.");
																	countries[m-1].setOwner(people[k]);
																	countries[m-1].setBattalions(1);
																	countries[l-1].setBattalions(countries[l-1].getBattalions() - 1);
																	b.updateGrid(countries[m-1].getRowIndex(), countries[m-1].getColumnIndex(), people[k], countries[m-1].getBattalions());
																	stuff = "no";
																	d = false;
																}
																System.out.println(b.toString());
															} else {
																System.out.println("Player " + people[k] + " rolled a " + z1 + ".");
																System.out.println("Player " + countries[m-1].getOwner() + " rolled a " + z2 + ".");
																System.out.println("Player " + people[k] + " loses.");
																countries[l-1].setBattalions(countries[l-1].getBattalions() - 1);
																b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], countries[l-1].getBattalions());
																if (countries[l-1].getBattalions() == 1) {
																	System.out.println("Player " + people[k] + " is done attacking.");
																	System.out.println(b.toString());
																	stuff = "no";
																	d = false;
																}	
																System.out.println(b.toString());
															}	
														}
													}
												
													if (!stuff.equals("no")) {
														System.out.print("Would you like to keep attacking?: ");
														stuff = in.nextLine();
														if ((stuff.equals("Y")) || (stuff.equals("y")) || (stuff.equals("YES")) || (stuff.equals("YEs")) || (stuff.equals("Yes")) || (stuff.equals("yes"))) {
															d = true;
														} else d = false;
													}
												}
											
											} else System.out.println("You can't attack your own country!  Try again.");
										} else if (b.getAdj()[l-1][m-1] == 0) {
											System.out.println("You can't attack a country that's not adjacent!");
										} else System.out.println("Invalid number.  Please enter the integer representation of the country.");
									} catch (NumberFormatException e) {
										System.out.println("Please enter the integer representation of the country you'd like to attack.");
									}
								
								} else if (countries[l-1].getOwner() == people[k]) {
									System.out.println("You don't have enough armies to attack from this country.  Please choose a different country.");
									d = false;
								} else System.out.println("That's not your country!  Choose your own country.");
								
							} else System.out.println("Invalid number.  Please enter an integer between 1 and " + Country.howMany + ".");
						} catch (NumberFormatException e) {
							System.out.println("Please enter the integer representation of the country.");
						}
					
					}
				}
				
				while ((c) && (comp) && (people[k] == computer.getName())) {
					computer.fillCountriesArray(b.getGridp(), b.getGridc(), b.getRows(), b.getColumns(), Country.howMany);
					computer.occupancy(b.getAdj(), countries);
					d = computer.attack();
					c = d;
					while (d) {
						l = computer.attackFrom(on, countries);
						m = computer.attackTo(on, countries);
						
						switch (countries[l-1].getBattalions()) {
						case 2: {
							u = die.rollDie() + 1;
							v = 0;
							w = 0;
						} break;
						case 3: {
							u = die.rollDie() + 1;
							v = die.rollDie() + 1;
							w = 0;
						} break;
						default: {
							u = die.rollDie() + 1;
							v = die.rollDie() + 1;
							w = die.rollDie() + 1;
						}
						}
					
						switch (countries[l-1].getBattalions()) {
						case 1: {
							s = die.rollDie() + 1;
							t = 0;
						} break;
						default: {
							s = die.rollDie() + 1;
							t = die.rollDie() + 1;
						}
						}
				
						z1 = 0; // Attacker
						z2 = 0; // Defender
					
						z1 = Math.max(Math.max(u, v), w);
						z2 = Math.max(s, t);
						System.out.println("Player " + people[k] + " rolled a " + z1 + ".");
						System.out.println("Player " + countries[m-1].getOwner() + " rolled a " + z2 + ".");
						
						if (z1 > z2) {
							System.out.println("Player " + countries[m-1].getOwner() + " loses.");
							countries[m-1].setBattalions(countries[m-1].getBattalions() - 1);
							b.updateGrid(countries[m-1].getRowIndex(), countries[m-1].getColumnIndex(), countries[m-1].getOwner(), countries[m-1].getBattalions());
							if (countries[m-1].getBattalions() == 0) {
								System.out.println("Player " + people[k] + " wins.");
								countries[m-1].setOwner(people[k]);
								countries[m-1].setBattalions(1);
								countries[l-1].setBattalions(countries[l-1].getBattalions() - 1);
								stuff = "no";
								d = false;
							}
						} else {
							System.out.println("Player " + people[k] + " loses.");
							countries[l-1].setBattalions(countries[l-1].getBattalions() - 1);
							b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], countries[l-1].getBattalions());
							if (countries[l-1].getBattalions() == 1) {
								System.out.println("Player " + people[k] + " is done attacking.");
								stuff = "no";
								d = false;
							}
						}
					
						z1 = Math.max(Math.min(Math.max(u, v), w), Math.max(Math.min(Math.max(u, w), v), Math.min(Math.max(w, v), u)));
						z2 = Math.min(s, t);
						if ((z1 != 0) && (z2 != 0) && (d)) {
							if (z1 > z2) {
								System.out.println("Player " + people[k] + " rolled a " + z1 + ".");
								System.out.println("Player " + countries[m-1].getOwner() + " rolled a " + z2 + ".");
								System.out.println("Player " + countries[m-1].getOwner() + " loses.");
								countries[m-1].setBattalions(countries[m-1].getBattalions() - 1);
								b.updateGrid(countries[m-1].getRowIndex(), countries[m-1].getColumnIndex(), countries[m-1].getOwner(), countries[m-1].getBattalions());
								if (countries[m-1].getBattalions() == 0) {
									System.out.println("Player " + people[k] + " wins.");
									countries[m-1].setOwner(people[k]);
									countries[m-1].setBattalions(1);
									countries[l-1].setBattalions(countries[l-1].getBattalions() - 1);
									b.updateGrid(countries[m-1].getRowIndex(), countries[m-1].getColumnIndex(), people[k], countries[m-1].getBattalions());
									stuff = "no";
									d = false;
								}
								System.out.println(b.toString());
							} else {
								System.out.println("Player " + people[k] + " rolled a " + z1 + ".");
								System.out.println("Player " + countries[m-1].getOwner() + " rolled a " + z2 + ".");
								System.out.println("Player " + people[k] + " loses.");
								countries[l-1].setBattalions(countries[l-1].getBattalions() - 1);
								b.updateGrid(countries[l-1].getRowIndex(), countries[l-1].getColumnIndex(), people[k], countries[l-1].getBattalions());
								if (countries[l-1].getBattalions() == 1) {
									System.out.println("Player " + people[k] + " is done attacking.");
									System.out.println(b.toString());
									stuff = "no";
									d = false;
								}	
								System.out.println(b.toString());
							}	
						}
					}
				}
			
				//-------------------------------------------------------------------------------------
				// Give player the option to move armies from one country to one adjacent country.
				

				/*
				 * I really don't have the time or motivation to do this part.  I can't think of a reasonable way to make it work
				 * at the moment, so it's not going to happen.  Sorry.
				 * 
				 */
				
				//--------------------------------------------------------------------------------------
				// Fix everything up and go onto the next turn.
				System.out.println("\r\n \r\n" + b.toString());
				if (comp) computer.occupancy(b.getAdj(), countries);
				c = true;
				d = true;
				k = (k + 1)%many;
		}
		
		
	}





}


