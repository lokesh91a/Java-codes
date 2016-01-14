import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Hashtable;
import java.util.Scanner;

/* 
 * Controller.java
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
 * Class Controller controls the whole game. It contacts to both model and view
 */
public class Controller implements Runnable {
	// Object of model of Connect4Field(Back end Logic of game)
	private Connect4FieldModel connect4FieldObj;

	// Object of view of Connect4Field(to display)
	private View viewObj;
	public Hashtable<Integer, String> addresses;
	private Player[] playerObj;
	private int noOfPlayers;
	byte[] sendbyte = new byte[1024];
	DatagramSocket sendSocket;
	
	// Parameterized Constructor
	public Controller(Connect4FieldModel objConnect4Field, View objView,
		int noOfPlayers, Player[] playerObj, Hashtable<Integer, String> 
			addresses, DatagramSocket socket) 
	{
		this.connect4FieldObj = objConnect4Field;
		this.viewObj = objView;
		this.playerObj = playerObj;
		this.noOfPlayers = noOfPlayers;
		this.addresses = addresses;
		this.sendSocket = socket;
	}

	public void run() 
	{
		try {
			play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Game Over");
		}
	}

	/**
	 * This method contains the logic of how the game is played
	 * 
	 * @param None
	 * 
	 * @return None
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public void play() throws IOException, InterruptedException
	{
 		for (int index = 0; index < noOfPlayers; index++)
 		{
			// New players are created here
			connect4FieldObj.playerArrayInterface2[index] = playerObj[index];
		}
		
 		while (!(connect4FieldObj.isItaDraw()))
		{
			for (int index = 0; index < noOfPlayers; index++)
			{
				byte[] sendbyte = new byte[1024];
				sendbyte = (connect4FieldObj.printConnect4FieldBoard() + "\n"
						+ connect4FieldObj.playerArrayInterface2[index]
							.getName() + " Enter Column number:").getBytes();
				DatagramPacket packet = new DatagramPacket(sendbyte,sendbyte.
						length,connect4FieldObj.playerArrayInterface2[index]
						.getIp(),connect4FieldObj.playerArrayInterface2[index]
								.getPort());
				sendSocket.send(packet);
				
				synchronized (connect4FieldObj.playerArrayInterface2[index])
				{
					connect4FieldObj.playerArrayInterface2[index].wait();		
				}
				
				if (connect4FieldObj.checkIfPiecedCanBeDroppedIn
						(connect4FieldObj.playerArrayInterface2[index].
								getData())) 
				{
					connect4FieldObj.dropPieces(connect4FieldObj.
							playerArrayInterface2[index].getData(),
							connect4FieldObj.playerArrayInterface2[index]
									.getGamePiece());
				}
				
				if(connect4FieldObj.didLastMoveWin())
				{
					for(int all = 0; all < noOfPlayers; all++)
					{
						byte[] sendbyte1 = new byte[1024];
						sendbyte1 = (connect4FieldObj.printConnect4FieldBoard()
								+ "\n" + connect4FieldObj.playerArrayInterface2
								[index].getName() + " won the game").getBytes();
						DatagramPacket packet1 = new DatagramPacket(sendbyte1,
								sendbyte1.length,connect4FieldObj.
								playerArrayInterface2[all].getIp(),
								connect4FieldObj.playerArrayInterface2[all]
											.getPort());
						sendSocket.send(packet1);
					}
					break;
				}	
			}
		}
	}	
}
