import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

/* 
 * PlayerDetails.java
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
 * This class takes details of each player independently 
 */
public class PlayerDetails implements Runnable
{
	public static Hashtable<Integer, String> addresses;
	DatagramPacket packet;
	static int key = 0;
	DatagramSocket socket;
	byte[] sendData;
	ArrayList<Player> player;
	static int flag;
	static char[] gamepiece = new char[2];
 	
	public PlayerDetails(DatagramPacket packet, Hashtable<Integer, String> 
			addresses, DatagramSocket socket, ArrayList<Player> player)
	{
		this.packet = packet;
		this.addresses = addresses;
		this.socket = socket;
		this.sendData = new byte[1024];
		this.player = player;
		this.gamepiece[0] = '*';
		this.gamepiece[1] = '&';
	}

	@Override
	public void run()
	{
		InetAddress ipAddress = packet.getAddress();
		if(addresses.size()>=2 && !addresses.contains(packet.getAddress().
				toString()))
		{
			String reply = "Game already running. Please wait.";
			sendData = reply.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.
					length,packet.getAddress(), packet.getPort()); 
			try {
			socket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else
		{
			synchronized (addresses)
			{
				if(!addresses.contains(ipAddress.toString()) && 
						addresses.size()<2)
				{
					addresses.put(key, ipAddress.toString());
					player.add(new Player(new String(packet.getData()), 
					gamepiece[key], packet.getAddress(), packet.getPort()));
					key = key + 1;
				}
			}
		
			if(addresses.containsValue(ipAddress.toString()) && addresses.
					size()==2 && flag==1)
				{
					int data = Integer.parseInt(new String(packet.getData()).
							trim());
					if(player.get(0).getIp().equals(packet.getAddress()))
					{	
						synchronized (player.get(0)) 
						{
							player.get(0).setData(data);
							player.get(0).notify();
						}
				
					}
			
					else
					{
						synchronized (player.get(1)) 
						{
							player.get(1).setData(data);
							player.get(1).notify();
						}
					}
				}
			
			synchronized (addresses)
			{
				if(addresses.size()==2 && flag==0)
				{
					flag = 1;
					addresses.notify();
				}	
			}
		
		}
	}		
}	
	

