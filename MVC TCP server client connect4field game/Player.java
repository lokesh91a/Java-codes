/* 
 * PlayerInterface.java
 * 
 * Revisions: 
 *     $1$ 
 */

import java.net.Socket;
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
	private Socket socket;
	
	/**
	 * Parameterized constructor
	 * 
	 * @param theField
	 * @param name
	 * @param gamePiece
	 */
	public Player(String name, char gamePiece, Socket socket)
	{
		this.name = name;
		this.gamePiece = gamePiece;
		this.socket = socket;
	}
	
	public Socket getSocket()
	{
		return socket;
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
	public String getName()
	{
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