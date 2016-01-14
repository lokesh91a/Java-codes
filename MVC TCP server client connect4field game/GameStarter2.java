import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/* 
 * GameStarter2.java
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
 * starts a 2 player game
 */
public class GameStarter2 implements Runnable
{
	private ArrayList<String> playerName2;
	private ArrayList<Socket> playerSocket2;
	private Player[] playerObj;
	private int noOfPlayers;
	ArrayList<String> playerNames = new ArrayList<String>();
	ArrayList<Socket> playerSockets = new ArrayList<Socket>();
	
	/**
	 * Default Constructor
	 * 
	 * 
	 * @param playerName2  : List of names
	 * @param playerSocket2: List of sockets
	 */
	public GameStarter2(ArrayList<String> playerName2, 
			ArrayList<Socket> playerSocket2)
	{
		this.playerName2 = playerName2;
		this.playerSocket2 = playerSocket2;
		
		//No of players is initialized as 2
		this.noOfPlayers = 2;
		
		//Array of player objects
		this.playerObj = new Player[2];
	}
	
	//Default run method
	public void run()
	{	
		
		//This loop runs forever
		while(true)
		{
			//Synchronized on list playername2
			synchronized (playerName2)
			{
				//If playerName2 has less than 2 players then, it goes to wait
				while(playerName2.size()<2)
				{
					try {
						playerName2.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//Copies details of all players in a local list
				//and leaves the lock
				playerNames.add(playerName2.get(0));
				playerNames.add(playerName2.get(1));
				playerSockets.add(playerSocket2.get(0));
				playerSockets.add(playerSocket2.get(1));
				
				//Clears original lists
				playerName2.clear();
				playerSocket2.clear();
				
			}
			
			//Object of model, view, players are created and is given to the
			//Controller
			Connect4FieldModel connect4FieldObj = new Connect4FieldModel();
			View viewObj = new View();
			playerObj[0] = new Player(playerNames.get(0),
					'*', playerSockets.get(0));
			playerObj[1] = new Player(playerNames.get(1),
					'+', playerSockets.get(1));
			
			//Local list of player details is also now cleared
			playerNames.clear();
			playerSockets.clear();
			
			//Object of controller is created
			Controller controllerObj = new Controller(connect4FieldObj, viewObj,
					noOfPlayers, playerObj);
			
			//Thread of controller started and this again goes to waiting
			//for next 2 players
			new Thread(controllerObj).start();
			
		}
	}

	
}
