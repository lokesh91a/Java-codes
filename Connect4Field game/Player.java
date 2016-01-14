/* 
 * PlayerInterface.java
 * 
 * Revisions: 
 *     $1$ 
 */

import java.util.Scanner;

/**
 * This is a Interface for a player
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

//Player class implements PlayerInterface
public class Player implements PlayerInterface 
{	
	private String name;
	private char gamePiece;
	private Connect4FieldInterface theField;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
	

	}
	
	/**
	 * Parameterized constructor
	 * 
	 * @param theField
	 * @param name
	 * @param gamePiece
	 */
	Player(Connect4FieldInterface theField, String name, char gamePiece)
	{
		this.theField = theField;
		this.name = name;
		this.gamePiece = gamePiece;
	}
	
	/**
	 * Implements method getGamePiece
	 * 
	 * @param	None
	 * 
	 * @return	None
	 * 
	 */
	public char getGamePiece() {
		// TODO Auto-generated method stub
		return gamePiece;
	}

	/**
	 * Implements method getName
	 * 
	 * @param	None
	 * 
	 * @return	name
	 * 
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * Implements method nectMove
	 * 
	 * @param	None
	 * 
	 * @return	1: Game piece can be dropped
	 * 			2: Game piece cannot be dropped
	 * 
	 */
	public int nextMove() {
		
		Scanner input = new Scanner(System.in);
		System.out.println(getName() +" Enter Column number:");
		
		//Takes column number from player to drop game piece
		int column = Integer.parseInt(input.next().toString());
		
		/*
		 * If game piece can be dropped then drop the game piece at that column
		 * and return 1
		 */
    	if(theField.checkIfPiecedCanBeDroppedIn(column))
    	{
    		theField.dropPieces(column, gamePiece);
    		return 1;
    	}
    	else
    		return 0;
	}	
}