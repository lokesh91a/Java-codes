import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/* 
 * MainClass.java
 * 
 * Revisions: 
 *     $1$ 
 */


/**
 * This is the server of the game
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * MainClass is the server
 */
public class MainClass extends UnicastRemoteObject implements PlayGame
{
	private static final long serialVersionUID = 1L;
	private Connect4FieldModel connect4FieldObj;
	private View viewObj;

	/**
	 * Default Constructor
	 * @throws RemoteException
	 */
	public MainClass() throws RemoteException
	{
		super();		
		//Object of Connect4FieldModel is created
		connect4FieldObj = new Connect4FieldModel();	
		//Object of View is created
		viewObj = new View();
	}

	private static int numPlayers = 0;
	
	/**
	 * if 2 player arrives then this method starts the game
	 */
	public void playGame(String playerName)
	{																		
		//If no. of players is less than 4
		if(numPlayers < 4){		
			//Object of Controller is created and object of connect4FieldModel and
			//view is passes as an parameter
			Controller controllerObj = new Controller(connect4FieldObj, viewObj, numPlayers, playerName);
			numPlayers++;
			new Thread(controllerObj).start();
		}		
	}	
	
	/**
	 * Registers all the players who are going to play the game
	 * @param ClientPlayerInterface 
	 */
	public void registerPlayers(ClientPlayerInterface player) 
			throws RemoteException{
		if(numPlayers < 4)
		{		
			//Method in view class is called
			viewObj.registerClients(player);
		}
		else
		{
			//If already 2 players are playing the game then third player is not
			//entertained
			player.display("Four Players already Playing game. Try again later");
		}
	}
	
	/**
	 * This method unregisters all the players who were playing the game
	 */
	public void unRegisterPlayers(ClientPlayerInterface player)
	{
		//Method in view class is called 
		viewObj.unRegisterClients(player);
		
		//number of players is again initialized to 0
		numPlayers = 0;
	}
	
	/**
	 * Main method
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String args[]) throws RemoteException
	{		
		//Object of mainclass has been made
		MainClass mc = new MainClass();
		
		//New registry is created at port number 4444
		Registry registry = LocateRegistry.createRegistry(4444);
		
		//this method binds the name of the object of GameServer "mc" with name
				//"GameServer
		registry.rebind("GameServer", mc);
		
		System.out.println("Server is up and ready for connection");
		
	}		
}
