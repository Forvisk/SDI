/** Chat_azj.java **/
import java.rmi.*;

public interface Chat_azj extends Remote{
	public String serverToClient(int lstMsg) throws RemoteException;
	public boolean clientToServer( String mensage) throws RemoteException;
}
