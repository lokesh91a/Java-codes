import java.rmi.Remote;
import java.rmi.RemoteException;

/* 
 * PlayGame.java
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This is interface for playing the game
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * Interface which defines methods through which player will play the game
 */
public interface PlayGame extends Remote{

	public void playGame(String playerName) throws RemoteException;
	public void registerPlayers(ClientPlayerInterface player) throws RemoteException;
	public void unRegisterPlayers(ClientPlayerInterface player) throws RemoteException;
}
