/** ChatServer.java **/
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class ChatServer implements Chat{
	public ChatServer(){}
	
	public static void main(String[] args){
		try{
			ChatServer server = new ChatServer();
			Chat stub = (Chat) UnicastRemoteObject.exportObject(server, 0);
			
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("serverToClient", stub);
			registry.bind("clientToServer", stub);
         	System.out.println("Servidor pronto");
		} catch ( Exception ex){
         ex.printStackTrace();
		
	}
	
	public String serverToClient() throws RemoteException{
		System.out.println("Executando serverToClient()");
		return "Hello!!!";
	}
	
	public boolean clientToServer(  String mensage) throws RemoteException{
		System.out.println("Mensagem recebifa : " + mensage);
		return true;
	}
	
}
