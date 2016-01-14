


/* 
 * Connect4FieldInterface.java
 * 
 * Revisions: 
 *     $1$ 
 */


/**
 * This is a interface of the game
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */


/**
 * Connect4Field class implements this interface and hence implements all its 
 * methods
 */
public interface Connect4FieldInterface {
	
    public boolean checkIfPiecedCanBeDroppedIn(int column);
    public void dropPieces(int column, char gamePiece);
    boolean didLastMoveWin();
    public boolean isItaDraw();
    public void init( PlayerInterface playerA, PlayerInterface playerB );
    public String toString();
    public void playTheGame();
    
}