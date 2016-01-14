import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/* 
 * GameStarter4.java
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
 * starts a 4 player game
 */
public class GameStarter4 implements Runnable
{
	private ArrayList<String> playerName4;
	private ArrayList<Socket> playerSocket4;
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
	public GameStarter4(ArrayList<String> playerName4, ArrayList<Socket> playerSocket4)
	{
		
		this.playerName4 = playerName4;
		this.playerSocket4 = playerSocket4;
		
		//No of players is initialized as 2
		this.noOfPlayers = 4;
		
		//Array of player objects
		this.playerObj = new Player[4];
	}
	
	//Default run method
	public void run()
	{	
		//Forever loop
		while(true)
		{
			//Synchronized on list playername4
			synchronized (playerName4)
			{
				//If playerName4 has less than 4 players then, it goes to wait
				while(playerName4.size()<4)
				{
					try {
						playerName4.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//Copies details of all players in a local list
				//and leaves the lock
				playerNames.add(playerName4.get(0));
				playerNames.add(playerName4.get(1));
				playerNames.add(playerName4.get(2));
				playerNames.add(playerName4.get(3));
				playerSockets.add(playerSocket4.get(0));
				playerSockets.add(playerSocket4.get(1));
				playerSockets.add(playerSocket4.get(2));
				playerSockets.add(playerSocket4.get(3));
				playerName4.clear();
				
				//Clears original lists
				playerSocket4.clear();
				playerName4.notifyAll();
			}
			
			//Object of model, view, players are created and is given to the
			//Controller
			Connect4FieldModel connect4FieldObj = new Connect4FieldModel();
			View viewObj = new View();
			playerObj[0] = new Player(playerNames.get(0),
					'*', playerSockets.get(0));
			playerObj[1] = new Player(playerNames.get(1),
					'+', playerSockets.get(1));
			playerObj[2] = new Player(playerNames.get(2),
					'&', playerSockets.get(2));
			playerObj[3] = new Player(playerNames.get(3),
					'$', playerSockets.get(3));
			
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
