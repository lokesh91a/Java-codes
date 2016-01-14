package connect4FieldMVC;

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

/**
 * Player class implements PlayerInterface and hence implements all the methods
 * in this interface.
 * Indirectly each player will have its own gamePiece, name and every player
 * will get a move
 */
public interface PlayerInterface 
{
	  // this is how your constructor has to be
	  //Player(Connect4FieldInterface theField, String name, char gamePiece);
      public char getGamePiece();
      public String getName();
      public int  nextMove();	
}