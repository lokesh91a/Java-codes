import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/* 
 * ClientPlayer.java
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This is the client side code
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * Client Code which implements client player interface
 */
public class ClientPlayer extends UnicastRemoteObject implements 
ClientPlayerInterface
{

	private static final long serialVersionUID = 1L;
	
	//To store IP address
	public static String ipAddress;
	
	//Scanner for taking input from user
	public Scanner sc = new Scanner(System.in); 
	
	//Constructor to handle any exception thrown from its super class
	public ClientPlayer() throws RemoteException 
	{
		super();		
	}	
	
	/**
	 * getScanner method returns object of scanner
	 * 
	 * @return Scanner Object of Scanner
	 */
	public Scanner getScanner()
	{
		return sc;
	}
	
	/**
	 * getInput returns input from user
	 * 
	 * @return int integer type inout from user
	 */
	public int getInput()
	{		
		return sc.nextInt();
	}
	
	/**
	 * Displays the board which has been passed to it
	 * 
	 * @return None
	 */
	public void display(String board) throws RemoteException
	{
		//If Board receives "Won the Game" then it unregisters all the players
				//and stops the service
		if (board.contains("Won the Game"))
		{
			PlayGame service;
			try {
				//Looks for the service of typr PlayGame
				service = (PlayGame)Naming.lookup("rmi://" + ipAddress + 
						":4444/GameServer");
				
				//Unregisters the player
				service.unRegisterPlayers(this);
				
				//Service gets closed
				sc.close();
			} catch (MalformedURLException e) {				
				e.printStackTrace();
			} catch (NotBoundException e) {				
				e.printStackTrace();
			}							
		}
		System.out.println(board);
	}
	
	/**
	 * Main function
	 * 
	 * @param args
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void main(String args[]) throws MalformedURLException, 
	RemoteException, NotBoundException 
	{
		
		//New object of ClientPlayer is made
		ClientPlayer player = new ClientPlayer();
		
		System.out.println("Enter IP Address of Server: ");
		
		//User enters the ip address of the server
		ipAddress = player.getScanner().next();
		
		//Looks for the service in rmi registry
		PlayGame service = (PlayGame)Naming.lookup("rmi://" + ipAddress + 
				":4444/GameServer");
		System.out.println("Enter Player Name: ");
		
		//User enters the name
		String playerName = player.getScanner().next();
		
		//This player is registered
		service.registerPlayers(player);		
		service.playGame(playerName);			
	}
}
