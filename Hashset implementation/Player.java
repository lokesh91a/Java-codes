/* 
 * PlayerInterface.java
 * 
 * Revisions: 
 *     $1$ 
 */

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
	 * Parameterized constructor
	 * 
	 * @param theField
	 * @param name
	 * @param gamePiece
	 */
	public Player(Connect4FieldInterface theField, String name, char gamePiece)
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
	 * Implements method nextMove
	 * It does not do anything
	 * 
	 * @param	None
	 * 
	 * @return	None
	 * 
	 */
	public int nextMove() {
		
		return 0;
	}	
}