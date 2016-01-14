import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
	private DatagramSocket socket;
	
	
	/**
	 * Parameterized Constructor
	 * 
	 * @param socket	: Socket of player(Client) is passed
	 */
	public ServerThread(DatagramSocket socket)
	{
		this.socket = socket;
	}
	
	public void run(){
		String message = null;
		try {
			
			//It runs and reads from server and writes to player
			while(true)
			{
				byte[] data = new byte[1024];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				socket.receive(packet);
				System.out.println(new String(packet.getData()));
			}
			
		} catch (IOException e) {
			socket.close();
			e.printStackTrace();
		}
	}
}
