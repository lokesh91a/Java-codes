/* 
 * PlayerInterface.java
 * 
 * Revisions: 
 *     $1$ 
 */

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.sound.sampled.Port;


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
	private InetAddress Ip;
	private int port, move;
	
	/**
	 * Parameterized constructor
	 * 
	 * @param theField
	 * @param name
	 * @param gamePiece
	 */
	public Player(String name, char gamePiece, InetAddress Ip, int port)
	{
		this.name = name;
		this.gamePiece = gamePiece;
		this.Ip = Ip;
		this.port = port;
	}
	
	public void setData(int move)
	{
		this.move = move;
	}
	
	public int getData()
	{
		return this.move;
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
	
	public InetAddress getIp()
	{
		return Ip;
	}
	
	public int getPort()
	{
		return port;
	}
}