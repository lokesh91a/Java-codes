import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/* 
 * ClientThread.java
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
 * Listens from the server for a particular player
 */
public class ClientThread extends Thread {
	
	//Socket of player
	private Socket socket;
	private String name;
	
	/**
	 * Parameterized Constructor
	 * 
	 * @param socket	: Socket of player(Client) is passed
	 */
	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			
			//New writer and reader for client is created for a particular 
			//socket
			String message = null;
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),
					true);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(System.in));
			
			//It runs and reads from server and writes to player 
			while ((message = bufferedReader.readLine()) != null) {
				printWriter.println(message);
			}
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
