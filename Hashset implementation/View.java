import java.rmi.RemoteException;
import java.util.HashMap;

/* 
 * View.java
 * 
 * Revisions: 
 *     $1$ 
 */


/**
 * This is a class for displaying anything to the user
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */


/**
 * View class displays everything and anything to the user
 */
public class View
{	
	//Used for storing playerid vs playername
	static HashMap<Integer,ClientPlayerInterface> playerlist = new HashMap<Integer,ClientPlayerInterface>();
	
	//Initializes the no. of players as 0
	static int numberPlayers = 0;
	
	/**
	 * This method takes a string as a input and displays it
	 * 
	 * @param	String It displays this string(Which was passed to it)
	 * 
	 * @return	None
	 * @throws RemoteException 
	 */
	public void display(String content,int playerId) throws RemoteException
	{		
		//method of respective client is called to display the content by 
				//matching player id
		((ClientPlayerInterface)playerlist.get(playerId)).display(content);
	}
	
	/**
	 * Respective method of the particular player has been called to get the
	 * input from the player 
	 * 
	 * @param content Board is passed as string
	 * @param playerId player id passed
	 * @return column number which player has entered
	 * @throws RemoteException
	 */
	public int getInput(String content,int playerId) throws RemoteException{
		return ((ClientPlayerInterface)playerlist.get(playerId)).getInput();
	}
		
	/**
	 * This method has been called to add the player details in the hashmap
	 * @param player
	 */
	public void registerClients(ClientPlayerInterface player)
	{
		playerlist.put(numberPlayers, player);
		numberPlayers++;
	}
	
	/**
	 * This method removes the player from the hashmap and reduces the no of
	 * players by 1
	 * 
	 * @param player
	 */
	public void unRegisterClients(ClientPlayerInterface player)
	{
		playerlist.remove(player);
		numberPlayers--;
	}
}
