import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

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
	private ArrayList<Player> player;
	private int noOfPlayers;
	private Hashtable<Integer, String> addresses;
	DatagramSocket socket;
	
	/**
	 * Default Constructor
	 * 
	 * 
	 * @param playerName2  : List of names
	 * @param playerSocket2: List of sockets
	 */
	public GameStarter2(ArrayList<Player> player, Hashtable<Integer, String> addresses, DatagramSocket socket)
	{
		this.addresses = addresses;
		this.socket = socket;
		//No of players is initialized as 2
		this.noOfPlayers = 2;
		
		//Array of player objects
		this.player = player;
	}
	
	//Default run method
	public void run()
	{	
		
		//This loop runs forever
		while(true)
		{
			//Synchronized on list playername2
			synchronized (addresses)
			{
				//If playerName2 has less than 2 players then, it goes to wait
				while(addresses.size()<2)
				{
					try {
						addresses.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			//Object of model, view, players are created and is given to the
			//Controller
			Connect4FieldModel connect4FieldObj = new Connect4FieldModel();
			View viewObj = new View();
			Player[] player1 = new Player[2];
			player1[0] = player.get(0);
			player1[1] = player.get(1);
			
			
			
			//Object of controller is created
			Controller controllerObj = new Controller(connect4FieldObj, viewObj,
					noOfPlayers, player1, addresses, socket);
			
			
			//Thread of controller started and this again goes to waiting
			//for next 2 players
			Thread gameStart = new Thread(controllerObj);
			gameStart.start();
			try {
				gameStart.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addresses.clear();

		}
	}

	
}
