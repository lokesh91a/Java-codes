import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/* 
 * ServerThread.java
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
 * Sends to server for a particular player
 */
public class ServerThread extends Thread{
	
	//Socket of player
	private Socket socket;
	
	/**
	 * Parameterized Constructor
	 * 
	 * @param socket	: Socket of player(Client) is passed
	 */
	public ServerThread(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		String message = null;
		try {
			
			//New reader for client is created for a particular 
			BufferedReader BR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//It runs and reads from server and writes to player 
			while((message = BR.readLine()) != null){
				System.out.println(message);
			}
			socket.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
