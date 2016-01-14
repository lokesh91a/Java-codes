import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

/* 
 * GameServer.java
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This is a controller of Connect4Field  game
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * Main Server which contacts new players.
 */

public class GameServer 
{	
	byte[] receiveData;
	public static Hashtable<Integer, String> addresses;
	DatagramSocket serverSocket;
	DatagramPacket packet;
	private ArrayList<Player> playerObj;
	
	
	//Default Constructor
	public GameServer() throws IOException
	{
		this.serverSocket = new DatagramSocket(2555);
		this.playerObj = new ArrayList<Player>();
		addresses = new Hashtable<Integer, String>();
		this.receiveData = new byte[1024];	
	}
	
	/**
	 * This method starts a thread of player details
	 * 
	 * @param None
	 * 
	 * @return None
	 * @throws IOException
	 */
	public void start() throws IOException
	{
		//Whenever a new player contacts server, a new thread is made for
		//it
		while(true)
		{
			this.receiveData = new byte[1024];
			this.packet = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(packet);			 
			new Thread(new PlayerDetails(packet, addresses, serverSocket, playerObj)).start();
			
		}
	}
	
	/**
	 * This method creates threads gamestarter2 and gamestarter4 and starts
	 * them
	 * 
	 * @param None
	 * 
	 * @return None
	 */
	public void gameStarters()
	{
		new Thread(new GameStarter2(playerObj, addresses, serverSocket)).start();
	}
	
	/**
	 * Main method
	 * 
	 * @param None
	 * 
	 * @return None
	 */
	public static void main(String args[]) throws IOException
	{
		
		//new object of server is created
		GameServer server = new GameServer();
		server.gameStarters();
		server.start();
	}
}

