/*

Final Project
David Lan and Bill Ge
ICS3U
Ms. Strelkovska
December 8, 2016

*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Game extends JFrame implements ActionListener{
	
    // Variables
	Container c;
	JPanel menu, game, plField, emenField, statPanel;
	CardLayout cards;
	JLabel name, a,b,cc;
	JLabel  plLbls[][]=new JLabel [9][9];
	JButton play,info,stats, back;
	JButton enemBtns[][] = new JButton [9][9];
	ImageIcon water = new ImageIcon(getClass().getResource("pictures\\water.png"));
	ImageIcon ship = new ImageIcon(getClass().getResource("pictures\\ship.png"));
	ImageIcon up = new ImageIcon(getClass().getResource("pictures\\up.png"));
	ImageIcon left = new ImageIcon(getClass().getResource("pictures\\left.png"));
	ImageIcon right = new ImageIcon(getClass().getResource("pictures\\right.png"));
	ImageIcon down = new ImageIcon(getClass().getResource("pictures\\down.png"));
	ImageIcon explosion = new ImageIcon(getClass().getResource("pictures\\Explosion!.png"));
	ImageIcon miss = new ImageIcon(getClass().getResource("pictures\\miss.png"));
	
	static String[][]playerField = new String[9][9];
	static String[][]enemyField = new String[9][9];
	static ArrayList <Integer> nextTurn = new ArrayList<Integer>();
	static int nextX = 0;
	static int nextY = 0;
	static int randX = -1, randY = -1;
	static int eLives = 0;
	static int pLives=0;
	static int count = 0;
	static int wins = 0, losses = 0;
	static int attack = 0;
	static boolean enemyTurn = false;
	static boolean hit = false;
	static boolean horizontal = false;
	
	// Method to fill the boards
	public static void fillBoards(String arr[][], String arr2[][]){
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				arr[i][j] = ". ";
			}
		}
		
		// Fill boards
		for(int i = 0; i < arr2.length; i++){
			for(int j = 0; j < arr2[i].length; j++){
				arr2[i][j] = ". ";
			}
		}
	}
	
	// Method to check if a ship can fit in a space
	public static boolean check (int direct, String arr[][], int x, int y, int size){
		boolean temp = true;
		if(direct == 0){
			for(int i = 0; i < size; i++){
				if(!arr[x][y+i].equals(". "))
					temp = false;
			}
		}
		else{
		
			for(int i = 0; i < size; i++){
				if(!arr[x+i][y].equals(". "))
					temp = false;
			}
		}
		return temp;
	}
	
	// Method to put ships on the field
	public static void populateField(String arr[][]){
		
		// Carrier
		
		// Generate a random direction
		int direction = (int)(Math.random() * 2);
		
		// Generate the coordinate of the ship's head
		int row = (int)(Math.random() * 5);
		int col = (int)(Math.random() * 5);
		
		// Create the symbol for the head of the ship
		if(direction == 1)
			arr[row][col] = "1 ";
		else
			arr[row][col] = "0 ";
		
		// Fill the board with the ship
		for(int i = 1; i < 5; i++){
			if(direction == 1){
				if(i == 4)
					arr[row + i][col] = "2 ";
				else
					arr[row + i][col] = "C ";
			}
			else{
				if(i == 4)
					arr[row][col + i] = "3 ";
				else
					arr[row][col + i] = "C ";
			}
		}
		
		// Battleship
		
		// Loop to check if the ship doesn't hit anything
		do{
			
			// Generate a random direction
			direction = (int)(Math.random() * 2);
			
			// Generate the coordinate of the ship's head
			row = (int)(Math.random() * 6);
			col = (int)(Math.random() * 6);
			
		}while(!check(direction, arr, row, col, 4));
		
		// Create the symbol
		if(direction == 1)
			arr[row][col] = "1 ";
		else
			arr[row][col] = "0 ";
		
		// Fill the board with the ship
		for(int i = 1; i < 4; i++){
			if(direction == 1){
				if(i == 3)
					arr[row + i][col] = "2 ";
				else
					arr[row + i][col] = "B ";
			}
			else{
				if(i == 3)
					arr[row][col+i] = "3 ";
				else
					arr[row][col+i] = "B ";
			}
		}
		
		// Submarine 
		
		// Loop to check if the ship doesn't hit anything
		do{
			
			// Generate a random direction
			direction = (int)(Math.random() * 2);
			
			// Generate the coordinate of the ship's head
			row = (int)(Math.random() * 7);
			col = (int)(Math.random() * 7);
			
		}while(!check(direction, arr, row, col, 3));
		
		// Create the symbol
		if(direction == 1)
			arr[row][col] = "1 ";
		else
			arr[row][col] = "0 ";
		
		// Fill the board with the ship
		for(int y = 1; y < 3; y++){
			if(direction == 1){
				if(y == 2)
					arr[row + y][col] = "2 ";
				else
					arr[row + y][col] = "S ";
			}
			else{
				if(y == 2)
					arr[row ][col+y] = "3 ";
				else
					arr[row][col+y] = "S ";
			}		
		}
		// Cruiser
		
		// Loop to check if the ship doesn't hit anything
		do{
			// Generate a random direction
			direction = (int)(Math.random() * 2);
			
			// Generate the coordinate of the ship's head
			row = (int)(Math.random() * 7);
			col = (int)(Math.random() * 7);
			
		}while(!check(direction, arr, row, col, 3));
	
		// Create the symbol
		if(direction == 1)
			arr[row][col] = "1 ";
		else
			arr[row][col] = "0 ";
		
		// Fill the board with the ship
		for(int y = 1; y < 3; y++){
			if(direction == 1){
				if(y == 2)
					arr[row + y][col] = "2 ";
				else
					arr[row + y][col] = "c ";
			}
			else{
				if(y == 2)
					arr[row][col+ y] = "3 ";
				else
					arr[row][col+ y] = "c ";
			}		
		}
		
		// Destroyer
		
		// Loop to check if the ship doesn't hit anything
		do{
			
			// Generate a random direction
			direction = (int)(Math.random() * 2);
			
			// Generate the coordinate of the ship's head
			row = (int)(Math.random() * 8);
			col = (int)(Math.random() * 8);
			
		}while(!check(direction, arr, row, col, 2));
		
		// Create the symbol
		if(direction == 1)
			arr[row][col] = "1 ";
		else
			arr[row][col] = "0 ";
		
		// Fill the board with the ship
		for(int y = 1; y < 2; y++){
			if(direction == 1){
				arr[row + y][col] = "2 ";
			}
			else{
				arr[row][col + y] = "3 ";
			}
		}
	}
	public static void ai(String arr[][]){
		
		// Check if you must use the AI
		if(!nextTurn.isEmpty()){
			
			// Checks if it is possible to guess a space to the right
			if(count == 0){
				
				// Checks if the space on the right is outside the gameboard
				if(nextTurn.get(count) >= 9){
					count = 1;
				}
				
				// Checks if the space on the right has already been guessed
				else{
					while(arr[nextX][nextTurn.get(count)].equals("X ") || arr[nextX][nextTurn.get(count)].equals("O ")){
						if(arr[nextX][nextTurn.get(count)].equals("O ")){
							count = 1;
							break;
						}
						nextTurn.add(count, nextTurn.get(count) + 1);
						nextTurn.remove(count + 1);
						if(nextTurn.get(count) >= 9){
							count = 1;
							break;
						}
						
					}
				}
			}
			
			// Checks if it is possible to guess a space to the left
		    if(count == 1){
				
				// Checks if the space on the left is outside the gameboard
				if(nextTurn.get(count) < 0){
					
					count = 2;
				}
				
				// Checks if the space on the left has already been guessed
				else{
					while(arr[nextX][nextTurn.get(count)].equals("X ") || arr[nextX][nextTurn.get(count)].equals("O ")){
						if(arr[nextX][nextTurn.get(count)].equals("O ")){
							count = 2;
							break;
						}
						nextTurn.add(count,nextTurn.get(count) - 1);
						nextTurn.remove(count + 1);
						if(nextTurn.get(count) < 0){
							count = 2;
							break;
						}
					}
				}
			}
			
			// Checks if it is possible to guess a space on the top
			if(count == 2){
				
				// Checks if the space on the top is outside the gameboard
				if(nextTurn.get(count) < 0){
					count = 3;
					
				}
				
				// Checks if the space on the top has already been guessed
				else{
					while(arr[nextTurn.get(count)][nextY].equals("X ") || arr[nextTurn.get(count)][nextY].equals("O ")){
						if(arr[nextTurn.get(count)][nextY].equals("O ")){
							count = 3;
							break;
						}
						nextTurn.add(count, nextTurn.get(count) - 1);
						nextTurn.remove(count + 1);
						if(nextTurn.get(count) < 0){
							count = 3;
							break;
						}
					}
				}
			}
			
			// Checks if it is possible to guess a space to the bottom
			if (count == 3){
				// Checks if the space on the bottom is outside the gameboard
				if(nextTurn.get(count) > 8){
					count = 4;
				}
				// Checks if the space on the bottom has already been guessed
				else{
					while(arr[nextTurn.get(count)][nextY].equals("X ") || arr[nextTurn.get(count)][nextY].equals("O ")){
						if(arr[nextTurn.get(count)][nextY].equals("O ")){
							count = 4;
							break;
						}
						nextTurn.add(count,nextTurn.get(count) + 1);
						nextTurn.remove(count + 1);
						if(nextTurn.get(count) > 8){
							count = 4;
							break;
						}
					}
				}
			}
			
			// Checks the space on either the right or left
			if(count < 2){
				if(!arr[nextX][nextTurn.get(count)].equals(". ")){
					attack++;
					hit = true;
					if(attack >= 2){
						horizontal = true;
					}
				}
				
				randX = nextX;
				randY = nextTurn.get(count);
				
			}
			
			
			// Checks the space on either the top or bottom
			if(1 < count && count < 4 ){
				if(!arr[nextTurn.get(count)][nextY].equals(". ")){
					hit = true;
				}
				randX = nextTurn.get(count);
				randY = nextY;
			}
			if(count == 4){
				nextTurn.clear();
			}
			if(!hit){
				count++;
			}
			
		}
	}
	public static void main(String[] args) {
		// Fill the player and enemy fields with ships and water
		fillBoards(playerField, enemyField );
		populateField(playerField);
		populateField(enemyField);
		
		// Output the enemy ships for cheats
		System.out.println("\n\n\n\n\n\n\n");
		System.out.printf("%30s", "");
		System.out.println("Enemy Ships");
		
		// Output coordinates
		System.out.printf("%30s", "");
		System.out.print("  ");
		for(int i = 1; i < enemyField.length + 1; i++){
			System.out.print(i + " ");
		}
		System.out.println();
		for(int i = 0; i < enemyField.length; i++){
			System.out.printf("%30s", "");
			System.out.print((char)(i + 65) + " ");
			
			// Output ships
			for(int j = 0; j < enemyField[i].length; j++){
				System.out.print(enemyField[i][j]);
			}
			System.out.println();
		}
		
		System.out.println("\n\n\n\n\n\n\n\n\n\n");
		System.out.printf("%30s", "");
		
		System.out.println("Player Ships");
		System.out.printf("%30s", "");
		System.out.print("  ");
								
		// Output coordinates and enemy ships for cheats
		for(int i = 1; i < playerField.length + 1; i++){
			System.out.print(i + " ");
		}
		System.out.println();
									
		for(int i = 0; i < playerField.length; i++){
			System.out.printf("%30s", "");
			System.out.print((char)(i + 65) + " ");
			for(int j = 0; j < playerField[i].length; j++){
				System.out.print(playerField[i][j]);
			}
			System.out.println();
		}
		System.out.println("\n\n\n\n");
		System.out.printf("%30s", "");
		
		// Create new JFrame
	    Game window = new Game("Battleship");
		window.setSize(450, 720);
		window.setVisible(true);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
	// Create a game
     public Game(String title){
    	 // Header
    	super(title);
    	
    	// Create the CardLayout for menu and game
	    c = getContentPane();
		cards=new CardLayout();
	    c.setLayout(cards);
	    
	    // Create the menu
		menu = new JPanel();
		menu.setLayout(null); 
		
		// Set background colour
		menu.setBackground(new Color(45, 160, 215));
		
		// Create the Title 
		name = new JLabel("Battleship");
		name.setBounds(100, 50, 350, 100);
		name.setFont(new Font("Serif", Font.BOLD, 60));
		menu.add(name);
		
		// Create the "Play" button
		play = new JButton("Play");
	    play.setBounds(80, 150, 300, 100);
	    menu.add(play);
		play.addActionListener(this);
	   
		// Create the "Tutorial" button
	    info = new JButton("Tutorial");
	    info.setBounds(80, 250, 300, 100);
	    menu.add(info);
	    info.addActionListener(this);
		
	    // Create the "Game Statistics" button
	    stats = new JButton("Game Statistics");
	    stats.setBounds(80, 350, 300, 100);
	    menu.add(stats);
	    stats.addActionListener(this);
		
	    // Create the game panel
		game = new JPanel();
	    game.setLayout(new GridLayout(9, 9));
	    			
		// Create a label to indicate the player and enemy ships
		a = new JLabel("Top: Enemy Ships              Bottom: Player Ships");
		
		// Create a panel to display information
		statPanel = new JPanel();
		statPanel.setLayout(new FlowLayout());
		statPanel.add(a);
		
		// Set colour of narration
		statPanel.setBackground(new Color(45, 160, 215));
		
		// Create a button "back" for returning to the menu
		back= new JButton("back");
		back.addActionListener(this);
		statPanel.add(back);
		
		// Create the player's field with ships and water
		plField = new JPanel();
		plField.setLayout(new GridLayout(9, 9));
		
		// Checks playerField for ships and water
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				// Checks if playerField[i][j] is the top part of the ship
				if(playerField[i][j].equals("1 ")){
					plLbls[i][j] = new JLabel(up);
					plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
				}
				// Checks if playerField[i][j] is the left part of the ship
				else if(playerField[i][j].equals("0 ")){
					plLbls[i][j] = new JLabel(left);
					plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
			
				}
				// Checks if playerField[i][j] is the bottom part of the ship
				else if(playerField[i][j].equals("2 ")){
					plLbls[i][j] = new JLabel(down);
					plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
			
				}
				// Checks if playerField[i][j] is the right part of the ship
				else if(playerField[i][j].equals("3 ")){
					plLbls[i][j] = new JLabel(right);
					plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
			
				}
				// Checks if playerField[i][j] is the middle part of the ship
				else if(!playerField[i][j].equals(". ")){
					plLbls[i][j] = new JLabel(ship);
					plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
				}
				// Checks if playerField[i][j] is just water
				else{
					plLbls[i][j] = new JLabel(water);
					plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
					
				}
				// Add the ImageIcon of ship or water onto the JPanel
				plField.add(plLbls[i][j]);
			}
		}
		
		// Create the enemy's field
		emenField = new JPanel();
		emenField.setLayout(new GridLayout(9, 9));
		
		// Fill the enemy's field with buttons for attack
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
			    String str = String.valueOf((char)(i + 65)) + Integer.toString(j + 1);
			    enemBtns[i][j] = new JButton(str);
			    enemBtns[i][j].addActionListener(this);
				emenField.add(enemBtns[i][j]);
			}
		}
		
		// Add the information and set it to the top of the screen
		game.setLayout(new BorderLayout());
		game.add(statPanel, BorderLayout.NORTH);
		
		
		// Create the playing fields for the enemy and player
		JPanel tmp= new JPanel(new GridLayout(2, 1));
		tmp.add(emenField);
		tmp.add(plField);
		game.add(tmp, BorderLayout.CENTER);
		
		// Add the menu and the playing field
		c.add(menu, "mmm");
  	    c.add(game, "ggg");
  	    
  	    // Set starting lives
  	    eLives = 17;
	    pLives = 17;
	}	
     
     	// Check for buttons clicked
    	public void actionPerformed(ActionEvent e) {
    		
    			// If the user clicks play
	    		if(e.getSource() == play){
	    			
	    			// Show the playing field
				    cards.show(c, "ggg");
				}
	    		
	    		// If no one won, keep playing
	    		if(eLives != 0 && pLives != 0){
	    			
	    			// Loop through enemy field to see if anything is clicked
		    		for(int i = 0; i < 9; i++){
		    			for(int j = 0; j < 9; j++){
		    				
		    				// If the button hasn't been clicked before and it isn't the enemy's turn
		    				if(e.getSource() == enemBtns[i][j] && !enemyField[i][j].equals("  ")&& !enemyTurn){
		    					
		    					// If the button is just water
		    					if(enemyField[i][j].equals(". ")){
		    						
		    						// Set the image on the screen to be a "miss"
		    						enemBtns[i][j].setIcon(miss);
		    						
		    						// Mark that this button has been clicked before
		    						enemyField[i][j] = "  ";
		    					}
		    					
		    					// If the button contains a ship
		    					else{
		    						
		    						// Set the image of the button to be a "explosion icon"
		    						enemBtns[i][j].setIcon(explosion);
		    						
		    						// Decrease lives
		    						eLives -= 1;
		    						
		    						// Mark that this button has been clicked before
		    						enemyField[i][j] = "  ";
		    						
		    						// If enemy lost
		    						if(eLives == 0){
		    							
		    							// User wins
		    							wins++;
		    							statPanel.removeAll();
		    							statPanel.add(new JLabel("You Win"));
		    							
		    							// Add "back" button
		    							back.addActionListener(this);
		    							statPanel.add(back);
		    						}
		    					}
		    				
		    					// While the enemy is still alive
		    					if(eLives != 0){
		    						
		    						// AI makes an attack
			    					ai(playerField);
			    					
			    					// If there are no leads to where a ship may be
			    	    			if(nextTurn.isEmpty()){
			    	    				
			    	    				// Attack a random square that hasn't been attacked before
			    		    			do{
			    							randX = (int)(Math.random() * 9);
			    							randY = (int)(Math.random() * 9);
			    						}while((playerField[randX][randY].equals("O ") || playerField[randX][randY].equals("X ")));
			    	    			}
			    	    			
			    	    			// If AI hits the player
			    	    			if(!playerField[randX][randY].equals(". " )&& nextTurn.isEmpty()){
			    						hit = true;
			    					}
			    	    			
			    	    			// Check if the AI hit the player
			    	    			if(hit){
			    	    				
			    						// Set a hit in the array
			    						playerField[randX][randY] = "X ";
			    						
			    						// Update the statPanel
			    						statPanel.removeAll();
			    						a = new JLabel("AI Attacks: " + (char)(randX + 65) + "" + (randY + 1) + ".   It Hit");			    		
			    						statPanel.add(a);
			    						
			    						// Add "back" button
			    						back.addActionListener(this);
		    							statPanel.add(back);
			    						
			    						// Subtract lives
			    						pLives--;
			    						
			    						// Set icon for an explosion
			    	    				plLbls[randX][randY].setIcon(explosion);
			    						hit = false;
			    						
			    						// If the player can have an educated guess
			    						if(!nextTurn.isEmpty()){
			    							
			    							// Update the planned attack
											if (count == 0 || count == 4){
												nextTurn.add(count, nextTurn.get(count) + 1);
												nextTurn.remove(count + 1);
											}
											else{
												nextTurn.add(count, nextTurn.get(count) - 1);
												nextTurn.remove(count + 1);
											}
			    						}
										else{
											// Creates next attacks for AI that are educated guesses
											nextX = randX;
											nextY = randY;
											nextTurn.add(nextY + 1);
											nextTurn.add(nextY - 1);
											nextTurn.add(nextX - 1);
											nextTurn.add(nextX + 1);
											count = 0;
											attack = 0;
										}
			    						
			    						// If player loses
			    						if(pLives == 0){
			    							
			    							// Player loss
			    							losses++;
			    							statPanel.removeAll();
			    							statPanel.add(new JLabel("You lose"));
			    							
			    							// Add "back" button
			    							back.addActionListener(this);
			    							statPanel.add(back);
			    						}
			    	    			}
			    	    			
			    	    			// If all four directions are attempted
			    	    			else{
			    	    				if(horizontal && count > 1 && !hit){
			    	    					count = 4;
			    	    					horizontal = false;
			    	    				}
			    						// Deactivate the AI
			    	    				if(count == 4){
			    	    					
			    	    					// Empty planned attacks
			    	    					nextTurn.clear();
			    	    				}
			    	    				
			    	    				// Set as a miss
			    	    				playerField[randX][randY] = "O ";
			    	    				
			    	    				// Update the screen for attacks
			    	    				a = new JLabel("AI Attacks: " + (char)(randX + 65) + "" + (randY + 1) +".    It Missed");
			    						statPanel.removeAll();
			    						statPanel.add(a);
			    						
			    						// Add "back" button
			    						back.addActionListener(this);
		    							statPanel.add(back);
		    							
		    							// Set icon as "miss"
			    	    				plLbls[randX][randY].setIcon(miss);	    				
			    	    			}
		    					}
		    	    		}
		    			}
		    		}		    		
	    		}
	    		
	    		// If user clicks for tutorial
			    if (e.getSource() == info){
			    	
			    	// Pop up for info
				     JOptionPane.showMessageDialog( null, "Click a coordinate to attack your opponent. \n"
				     		+ "First person to have 17 hits wins", "Information", JOptionPane.INFORMATION_MESSAGE );
				}
				else if ( e.getSource() == stats){
					
					// If user has finished a game
					if(wins + losses > 0){
						
						// Pop up for game stats
						 JOptionPane.showMessageDialog( null, "Wins: " + wins + "\nLosses: " + losses + 
								 "\nWinning Percentage: " + Math.round((double)wins / (wins + losses) * 10000) / 100.0 + "%", "Game Statistics", JOptionPane.INFORMATION_MESSAGE );
					}
					else{
						
						// Pop up for game stats
						JOptionPane.showMessageDialog( null, "Wins: 0 \n Losses: 0" + 
								 "\n Winning Percentage: 0.00%", "Game Statistics", JOptionPane.INFORMATION_MESSAGE );
						
					}
				}
			    
			    // If user clicks "back"
				else if (e.getSource() == back){
					
					// Show the main menu
				    cards.show(c, "mmm");
				    
				    // If someone wins
				    if(pLives == 0 || eLives == 0){
				    	nextTurn.clear();
				    	// Reset game boards
				    	game.removeAll();
						fillBoards(playerField, enemyField);
						populateField( playerField);
						populateField(enemyField);
						
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
						System.out.printf("%30s", "");
						System.out.println("Enemy Ships");
						System.out.printf("%30s", "");
						System.out.print("  ");
						
						// Output coordinates and enemy ships for cheats
						for(int i = 1; i < enemyField.length + 1; i++){
							System.out.print(i + " ");
						}
						System.out.println();
						
						for(int i = 0; i < enemyField.length; i++){
							System.out.printf("%30s", "");
							System.out.print((char)(i + 65) + " ");
							for(int j = 0; j < enemyField[i].length; j++){
								System.out.print(enemyField[i][j]);
							}
							System.out.println();
						}
						System.out.println("\n\n\n\n\n\n\n\n\n");
						System.out.printf("%30s", "");
						
						System.out.println("Player Ships");
						System.out.printf("%30s", "");
						System.out.print("  ");
						
						// Output coordinates and enemy ships for cheats
						for(int i = 1; i < playerField.length + 1; i++){
							System.out.print(i + " ");
						}
						System.out.println();
						
						for(int i = 0; i < playerField.length; i++){
							System.out.printf("%30s", "");
							System.out.print((char)(i + 65) + " ");
							for(int j = 0; j < playerField[i].length; j++){
								System.out.print(playerField[i][j]);
							}
							System.out.println();
						}
						System.out.println("\n\n\n\n");
						System.out.printf("%30s", "");
						
						// Create new game
						game = new JPanel();
						game.setLayout(new GridLayout(9, 9));
						
						// Reset statPanel
						a = new JLabel("Top: Enemy Ships              Bottom: Player Ships");
						statPanel = new JPanel();
						statPanel.setLayout(new FlowLayout());
						statPanel.setBackground(new Color(45, 160, 215));
						statPanel.add(a);
						
						// Add "back" button
						back= new JButton("back");
						back.addActionListener(this);
						statPanel.add(back);
						
						// New player field
						plField = new JPanel();
						plField.setLayout(new GridLayout(9, 9));
						
						// Checks playerField for ships and water
						for(int i = 0; i < 9; i++){
							for(int j = 0; j < 9; j++){
								// Checks if playerField[i][j] is the top part of the ship
								if(playerField[i][j].equals("1 ")){
									plLbls[i][j] = new JLabel(up);
									plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
								}
								// Checks if playerField[i][j] is the left part of the ship
								else if(playerField[i][j].equals("0 ")){
									plLbls[i][j] = new JLabel(left);
									plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
							
								}
								// Checks if playerField[i][j] is the bottom part of the ship
								else if(playerField[i][j].equals("2 ")){
									plLbls[i][j] = new JLabel(down);
									plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
							
								}
								// Checks if playerField[i][j] is the right part of the ship
								else if(playerField[i][j].equals("3 ")){
									plLbls[i][j] = new JLabel(right);
									plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
							
								}
								// Checks if playerField[i][j] is the middle part of the ship
								else if(!playerField[i][j].equals(". ")){
									plLbls[i][j] = new JLabel(ship);
									plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
								}
								// Checks if playerField[i][j] is just water
								else{
									plLbls[i][j] = new JLabel(water);
									plLbls[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
									
								}
								// Add the ImageIcon of ship or water onto the JPanel
								plField.add(plLbls[i][j]);
							}
						}
						
						// Create the enemy's field
						emenField = new JPanel();
						emenField.setLayout(new GridLayout(9, 9));
						
						// Create buttons for enemy field for user to attack
						for(int i = 0; i < 9; i++){
							for(int j = 0; j < 9; j++){
							    String str = String.valueOf((char)(i + 65)) + Integer.toString(j + 1);
							    enemBtns[i][j] = new JButton(str);
							    enemBtns[i][j].addActionListener(this);
								emenField.add(enemBtns[i][j]);
								
							}
						}
						
						// Add enemy and player field to game
						game.setLayout(new BorderLayout());
						game.add(statPanel, BorderLayout.NORTH);
						JPanel tmp= new JPanel(new GridLayout(2, 1));
						tmp.add(emenField);
						tmp.add(plField);
						game.add(tmp, BorderLayout.CENTER);
						c.add(game, "ggg");
						
						// Reset Lives
						eLives = 17;
					    pLives = 17;
				   }
				}
	    	}
}


