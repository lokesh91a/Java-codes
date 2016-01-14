/* 
 * Hangman.java 
 * 
 * Version: 
 *     $2$ 
 * 
 * Revisions: 
 *     $1$ 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This is a hangman game
 *
 * @author      Sahil Jasrotia
 * @author      Lokesh Agrawal
 */

public class HangMan 
{	
	//ArrayList for storing names of Players
	static ArrayList<String> playerList = new ArrayList<String>();
	
	//ArrayList for storing the random words alloted to the players
	static ArrayList<String> playerWord = new ArrayList<String>();
	
	//ArrayList for storing the scores of the players
	static ArrayList<Integer> playerScore = new ArrayList<Integer>();
	
	//ArrayList for storing the wrong guesses of the players
	static ArrayList<String> wrongGuesses = new ArrayList<String>();
	
	//ArrayList for displaying the part of the word that the player has already 
	//guessed
	static ArrayList<String> guessedWord = new ArrayList<String>();
	
	//ArrayList for storing the winning status of the player
	static ArrayList<Integer> winCheck = new ArrayList<Integer>();
	
	//2D array for drawing hangman
	static String hangManArray[][] = new String[10][10];
	
    /**
     * The main program.
     *
     * @param    args    command line arguments are used as file location
     */		
	public static void main(String args[])
	{		
		Scanner input = new Scanner(System.in);
		System.out.println("How many players are going to play? ");
		int noOfPlayers = input.nextInt();
	
		// Taking player names and allocates a randomly generated word to each player 
		for(int playerName = 0; playerName<noOfPlayers; playerName++)
		{
			System.out.println("Enter the name of Player" +(playerName+1));
			playerList.add(input.next());
			playerWord.add(generateWord(args[0]));
		}
		
		//loop to initialize player score and initialize guessesWord
		intializeScrGwd(noOfPlayers);
		
		//loop to initialize the 2d array which is used for drawing hangman
		initialize2dArray();
		
		/*Outer for loop for 8 correct chances to each player and inner for  
		 *loop chance of each player one by one(alternatively)
		 */
		for(int totalChances = 0; totalChances<8; totalChances++)
		{
	skipForWin: for(int currPlayer = 0; currPlayer<noOfPlayers; currPlayer++)
			{
				// Skip the chance of current player if he had already guessed  
				// the whole word
				if(winCheck.get(currPlayer)==0)
				continue skipForWin;
				System.out.println();
				
				//To notify the player of his chance
				System.out.println("Chance"+(totalChances+1) + " of " +
												playerList.get(currPlayer));
				
				//To print the part of the word which player has already guess
				System.out.println("Word: " +guessedWord.get(currPlayer));
				
				//To print the alphabets which the player guessed wrong
				System.out.println("Misses: " +wrongGuesses.get(currPlayer));						
				
				System.out.print("Guess: " );
				String guess = input.next();
				
				/* checkGuess is a function which returns -1 if player has  
				 * entered correct alphabet and the current player gets 1  
				 * more chance and the function returns 0 if the current  
				 * player has entered wrong alphabet and now next player 
				 * gets the chance to guess  
				 */
				currPlayer = currPlayer + checkGuess(currPlayer,guess);
				
			}
		}
		
		/* When the game is finished: scorePlayerSort function sorts the score 
		 * and player list in order of highest to lowest score
		 */
		scorePlayerSort(noOfPlayers);
		
		//Below loop prints the player name and their scores
		for(int currentPlayer = 0; currentPlayer<noOfPlayers;currentPlayer++)
		{
			System.out.println(playerList.get(currentPlayer) +": " + 
											playerScore.get(currentPlayer) );
		}
		
	}      
	
	/**
   	* This method generates random word from a given text file   
   	*
   	* @param    args	 location of input text file
   	* 
   	* @return   		  Returns the randomly generated word
   	*/		
	public static String generateWord(String args) 
	{
        String randomWordRead = ""; 	// Stores random word read from file
        int readWordCounter = 0;		// Counter to read words from file
        int numberOfWords=0;            // Stores total no. of words from file    
        
        String readLine = "";
        try {
        	// Read file
        	Scanner sc = new Scanner(new File(args));
        	
        	// Calculates total number of words from a file.
            while(sc.hasNext())
            {
            	numberOfWords++;
            	sc.nextLine();
            }
            
            // Reading file
            sc = new Scanner(new File(args));
            
            // Generating random number to read a random word from file
            int randomWordNumber = randomNo(numberOfWords);
            while (sc.hasNext() && readWordCounter < randomWordNumber) {
            	readWordCounter = readWordCounter + 1;
                readLine = sc.nextLine();
            }
            
            randomWordRead = readLine;
            sc.close();
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        return randomWordRead;
    }

	/**
   	* This method prints the generated hangman   
   	*
   	* @param    None
   	* 
   	* @return   None		  
   	*/		
	public static void printHangMan()
	{		
		// Loop through the entire 2D array to display Hangman image
		for (int row = 0; row < 10; row++)
		{
			for (int column = 0; column < 10; column++)
			{
				System.out.print(hangManArray[row][column]);
			}
			System.out.println();
		}
	}
	
	/**
   	* This method draws the hangman in 2d array   
   	*
   	* @param    chance	  The numbers of misses guesses
   	* 
   	* @return   None		  
   	*/		
	public static void drawHangMan(int chance)
	{		
		// The hangman will be erected from bottom to top, that is first gallos
		// base will be made and then step by step hangman will be generated.		
		switch(chance)
		{			
			// we do not need add break in the cases because when players misses
			// word after 8 chances then complete image of hangman is created.
			case 8:
				// Create legs of man
				hangManArray[6][6] = "/";
				hangManArray[7][5] = "/";
				hangManArray[6][8] = "\\";
				hangManArray[7][9] = "\\";
				
			case 7:
				// Create hands of man
				hangManArray[4][6] = "/";
				hangManArray[5][5] = "/";
				hangManArray[4][8] = "\\";
				hangManArray[5][9] = "\\";
				
			case 6:
				// Create tummy of man
				for(int i = 3; i <= 5; i++)				
					hangManArray[i][7] = "|";
				
			case 5:
				// Create eyes and nose of man
				hangManArray[2][6] = "*";
				hangManArray[2][7] = "!";
				hangManArray[2][8] = "*";
				
			case 4:				
				// Create face of man
				hangManArray[2][5] = "(";
				hangManArray[2][9] = ")";				
				
			case 3:				
				// Create gallos
				for(int i = 3; i <= 7; i++)				
					hangManArray[0][i] = "#";
				hangManArray[1][7] = "#";
				
			case 2:
				// Create gallos 
				for(int i = 0; i <= 8; i++)				
					hangManArray[i][2] = "#";			
								
			case 1:
				// Create base of gallos
				for(int i = 0; i <= 4; i++)				
					hangManArray[9][i] = "#";			
			break;
			
			default:
				break;
				
		}
	}
	
	/**
   	* This method initializes the 2d array with blank spaces before printing
   	* hangman image   
   	*
   	* @param    None
   	* 
   	* @return   None
   	*/		
	public static void initialize2dArray()
	{
		// Initialize the 2d array with blank spaces before printing the 
		// actual image of hangman.
		for (int row = 0; row < 10; row++)
		{
			for (int column = 0; column < 10; column++)
			{
				hangManArray[row][column] = " ";
			}			
		}
	}
		
    /**
     * When the game is finished: scorePlayerSort function sorts the score
	 * & player list in order of highest to lowest score. Bubble sort has  
	 * been used
	 * @param    noOfPlayers	Total number of players playing
	 * 
	 * @return   None		 * 
	 */ 
	public static void scorePlayerSort(int noOfPlayers)
	{
		 /* Outer and inner loop will get executed (noOfPlayers-1) times.
		  * Inner loop compares the score of current player with next
		  * player and swaps if the score of next player is higher
		  */
		 for(int loopCount = 0; loopCount<noOfPlayers-1; loopCount++)
		 {
			for(int currPlayer = 0; currPlayer<noOfPlayers-1; currPlayer++)
			{
				if(playerScore.get(currPlayer+1)>playerScore.get(currPlayer))
				{
					//score swaps
					int temp = playerScore.get(currPlayer);
					playerScore.set(currPlayer, playerScore.get(currPlayer+1));
					playerScore.set(currPlayer+1, temp);
					
					//Name also swaps if score swaps
					String tempName = playerList.get(currPlayer);
					playerList.set(currPlayer, playerList.get(currPlayer+1));
					playerList.set(currPlayer+1, tempName);						
				}
			}	
		 }			
	}
		
	/**
   	* This method is for generating random integers   
   	*
   	* @param    wordCount	Total number of words in a file
   	* 
   	* @return   Returns the random number to choose word from the file
   	*/					
	public static int randomNo(int wordCount)
	{
		Random rand = new Random();
		int randomNumber = rand.nextInt(wordCount);
		return randomNumber;
		
	}
	
	/**
   	* function for initializing array lists of playerScore, guessedWord,   
   	* wrongGuesses and setting winCheck length to the length of the word
   	* alloted to the current player
   	* 
   	* @param    wordCount	Total number of words in a file
   	* 
   	* @return   Returns the random number to choose word from the file
   	*/							
	public static void intializeScrGwd(int noOfPlayers)
	{		
		for(int currentPlayer=0;currentPlayer<noOfPlayers;currentPlayer++)
		{
			playerScore.add(0);
			guessedWord.add("");
			wrongGuesses.add("");
			winCheck.add(playerWord.get(currentPlayer).length());
		}
		
		/*loop for printing the intial word to the user in the format of:
		 * "_ _ _ _ _ _"(number of underscores depend on the size of word)
		 */
		
		for(int currentPlayer=0;currentPlayer<noOfPlayers; currentPlayer++)
		{
		  for(int j = 0;j<playerWord.get(currentPlayer).length();j++)
		  {
		    guessedWord.set(currentPlayer,guessedWord.get(currentPlayer)
				  													+"_ ");
		  }
		}
	}
	
	
	/** This function is comparing the guess of the player with his word
	 *  and validating the following things:
	 * 1.If the guess is correct then increments scores by 10
	 * 2.If the guess is not correct then decrements the score by 5
	 * 3.Updates the guessedWord by replacing the underscore with the 
	 *   correct alphabet guessed
	 * 4.Updates wrong guesses of the player by adding the incorrect guess
	 *   to the list
	 *     
	 * @param    currentPlayer	Current playing player
	 * 			 guess			The guess that current player has guessed
   	 * 
   	 * @return   -1 if guess is correct and returns 0 if guess is not 
	 *   		  correct   
	 * 
	 */
	public static int checkGuess(int currentPlayer, String guess)
	{
		String word = playerWord.get(currentPlayer);
		int flag = 0, score = 0;
			
		/* loop to compare the guess with the word and if matches then  
		 * updates the score and give one more chance to the player and  
		 * if not matches then also updates the score and give the chance
		 *  to the next player
		 */
		for(int i = 0; i<word.length(); i++)
		{
			String already = Character.toString
								(guessedWord.get(currentPlayer).charAt(2*i));
			if(guess.equalsIgnoreCase(Character.toString(word.charAt(i)))
										&&!guess.equalsIgnoreCase(already))
			{
				flag = flag + 1;
				StringBuilder replace = 
						new StringBuilder(guessedWord.get(currentPlayer));
				
				replace.setCharAt(2*i, word.charAt(i));
				String p = replace.toString();
				guessedWord.set(currentPlayer, p);
											
			}
		}
	
		if(flag>0)
		{
			playerScore.set(currentPlayer, playerScore.get(currentPlayer)+
																(flag*10));
			winCheck.set(currentPlayer, winCheck.get(currentPlayer)-flag);
			return -1;
		}
		else
		{
		   playerScore.set(currentPlayer,playerScore.get(currentPlayer)-5);
		   wrongGuesses.set(currentPlayer,wrongGuesses.get(currentPlayer)+ 
				   													guess);
			
			// Create the hangman with respect to number of wrong guesses
			// print it.
			System.out.println("");
			drawHangMan(wrongGuesses.get(currentPlayer).length());
			printHangMan();
			return 0;
		}

	}		
}