import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

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
	private DatagramSocket socket;
	private String name;
	private int port;
	private InetAddress address;
	
	/**
	 * Parameterized Constructor
	 * 
	 * @param socket	: Socket of player(Client) is passed
	 */
	public ClientThread(DatagramSocket socket, InetAddress address, int port)
	{
		this.socket = socket;
		this.address = address;
		this.port = port;
	}

	public void run() {
		try {
			
			//New writer and reader for client is created for a particular 
			//socket
			String message = null;
			Scanner input = new Scanner(System.in);
			
			System.out.println("Enter column number when board will be " +
					"displayed");
			
			//It runs and reads from server and writes to player 
			while(true)
			{
				byte[] data = new byte[1024];
				message = input.next();	
				data = message.getBytes();	
				DatagramPacket packet = new DatagramPacket(data, data.length, 
						address, port);
				socket.send(packet);
			}
			
		} catch (Exception e) {
			socket.close();
			System.out.println(e);
		}
	}
}
