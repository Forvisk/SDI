/** HelloWorld.java **/
import java.rmi.*;
import java.util.*;
 
public interface HelloWorld extends Remote {
   public boolean sendToServer(String msg) throws RemoteException;
   public ArrayList<String> receiveFromServer() throws RemoteException;
   public int countMessages() throws RemoteException;
}
