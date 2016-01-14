import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/* 
 * PlayerClient.java
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
 * Player Client is for client who needs to connect to server
 */
public class PlayerClient
{
	//Server port no.
	int port, serverPort;
	private InetAddress address;
	DatagramSocket client;
	String data;
	Scanner input;
	
	/**
	 * Parameterized Constructor
	 * 
	 * @param ip					: IP address of server
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public PlayerClient() throws UnknownHostException, IOException
	{
		this.client = new DatagramSocket();
	}
	
	/**
	 * Main Method
	 */
	public static void main(String args[]) throws UnknownHostException, IOException
	{
		//Scanner for taking ip address of server
		Scanner input = new Scanner(System.in);
		System.out.println("Enter IP address of server");
		String ip = input.next();
		System.out.println("Enter port number");
		String p = input.next();
		//A new client is made
		PlayerClient player = new PlayerClient();
		player.address = InetAddress.getByName(ip);
		player.port = Integer.parseInt(p);
		player.client.connect(player.address, player.port);
		//Two threads are started for listening to and writing to
		//servers
		new ClientThread(player.client,player.address, player.port).start();
		new ServerThread(player.client).start();
	}
	
	
}
