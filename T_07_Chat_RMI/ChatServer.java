/** ChatServer.java **/
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class ChatServer implements Chat{
	//String[30] buffer;
	//int numCient; 

	public ChatServer(){}
	//main()
	//clientToServer()
	//serverToClient()

	
	public static void main(String[] args){

		try{
			ChatServer server = new ChatServer();
			Chat stub = (Chat) UnicastRemoteObject.exportObject(server, 0);
			
			Registry registry = LocateRegistry.getRegistry();
			//registry.bind("serverToClient", stub);
			//registry.bind("clientToServer", stub);
			registry.rebind("serverAZJ", stub);
         	System.out.println("Servidor pronto");
		} catch ( Exception ex){
         ex.printStackTrace();
		}
	}
	
	public String serverToClient() throws RemoteException{
		System.out.println("Executando serverToClient()");
		return "Vai dormir, cliete!";
	}
	
	public boolean clientToServer(  String mensage) throws RemoteException{
		System.out.println("Mensagem recebifa : " + mensage);
		return true;
	}
	
}
