import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
	//Server Socket
	private ServerSocket serverSocket;
	
	//Port to which connect to
	private int port = 2526;
	
	//For storing details of names of players
	private ArrayList<String> playerName2, playerName4;
	
	//For storing details of sockets of players
	private ArrayList<Socket> playerSocket2, playerSocket4;
	
	//Default Constructor
	public GameServer() throws IOException
	{
		this.serverSocket = new ServerSocket(port);	
		this.playerName2 = new ArrayList<String>();
		this.playerName4 = new ArrayList<String>();
		this.playerSocket2 = new ArrayList<Socket>();
		this.playerSocket4 = new ArrayList<Socket>();
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
			new Thread(new PlayerDetails(serverSocket.accept(), playerName2, 
					playerName4, playerSocket2, playerSocket4)).start();
			
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
		new Thread(new GameStarter2(playerName2, playerSocket2)).start();
		new Thread(new GameStarter4(playerName4, playerSocket4)).start();
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
		
		//methods are called one by one
		server.gameStarters();
		server.start();
	}
}

