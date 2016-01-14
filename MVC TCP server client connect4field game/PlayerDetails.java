import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/* 
 * PlayerDetails.java
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
 * This class takes details of each player independently 
 */
public class PlayerDetails implements Runnable
{
	
	//All lists fro details of all players
	private static ArrayList<String> playerName2, playerName4;
	private static ArrayList<Socket> playerSocket2, playerSocket4;
	
	//Player Socket for taking its details
	private Socket playerSocket;
	
	/**
	 * Parameterized Constructor
	 * 
	 * 
	 * @param socket		: Socket of player is passed
	 * @param playerName2	: References of list for 2 player games is passed
	 * @param playerName4	: References of list for 4 player games is passed
	 * @param playerSocket2 : References of list for 2 player games is passed
	 * @param playerSocket4 : References of list for 4 player games is passed
	 */
	public PlayerDetails(Socket socket, ArrayList<String> playerName2,
			ArrayList<String> playerName4, ArrayList<Socket> playerSocket2,
			ArrayList<Socket> playerSocket4)
	{
		this.playerSocket = socket;
		this.playerName2 = playerName2;
		this.playerName4 = playerName4;
		this.playerSocket2 = playerSocket2;
		this.playerSocket4 = playerSocket4;
		
	}
	
	//Default run method
	public void run()
	{
		try
		{
			//Writer is made for sending data on client socket
			PrintWriter	out = new PrintWriter
					(playerSocket.getOutputStream (), true);
			
			//Reader is made for reading data from client
			BufferedReader in = new BufferedReader (
						new InputStreamReader (playerSocket.getInputStream()));
			out.println("Would you like to play a 2 player game or 4 player " +
					"game. Enter 2 or 4");
			
			//data is read
			String data = in.readLine();
			
			//If player wants to play a 2 player game
			if(Integer.parseInt(data)==2)
			{
				out.println("Enter your name");
				
				//Name of player is read
				data = in.readLine();
				synchronized (playerName2)
				{
					//Name of player is added in list
					playerName2.add(data);
					
					//Socket of player is added in a list
					playerSocket2.add(playerSocket);
					
					//If 2 size of list becomes 2 then gamestarter2 is notified
					//to start the game
					if(playerName2.size()==2)
					{
						playerName2.notifyAll();
					}
				}
			}
			
			//If player wants to play a 4 player game
			else
			{
				out.println("Enter your name");
				data = in.readLine();
				synchronized (playerName4)
				{
					playerName4.add(data);
					playerSocket4.add(playerSocket);
					System.out.println(playerSocket4.size());
					if(playerName4.size()==4)
					{
						playerName4.notifyAll();
					}
						
				}
			}
		}
        
		catch (Exception e)
		{
			System.out.println("oops!!This program made an exception." +
					"\n" + "I dont know what is it");
			e.printStackTrace();
		}
	}
	
	
}
