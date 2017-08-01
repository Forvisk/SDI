/** HelloWorld.java **/
import java.rmi.*;
 /*
public interface HelloWorld extends Remote {
   public String hello() throws RemoteException;
}
*/
public interface ChatRmi extends Remote {
	public boolean send( String message) throws RemoteException;
	public String receive( int pos) throws RemoteException;
}