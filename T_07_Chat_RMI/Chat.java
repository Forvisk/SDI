/** Chat.java **/
import java.rmi.*;

public interface Chat extends Remote{
	public String serverToClient() throws RemoteException;
	public boolean clientToServer( String mensage) throws RemoteException;
}
