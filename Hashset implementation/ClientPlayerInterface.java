import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientPlayerInterface extends Remote{
	
	public int getInput() throws RemoteException;
	
	public void display(String board) throws RemoteException;
}
