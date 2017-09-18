/** ChatServer.java **/
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.ArrayList;

public class ChatServer implements Chat_azj{
	private ArrayList<String> mensagens;
	private int numMsg;

	public ChatServer(){}
	//main()
	//clientToServer()
	//serverToClient()

	
	public static void main(String[] args){

		try{
			ChatServer server = new ChatServer();
			Chat_azj stub = (Chat_azj) UnicastRemoteObject.exportObject(server, 0);
			
			Registry registry = LocateRegistry.getRegistry();
			//registry.bind("serverToClient", stub);
			//registry.bind("clientToServer", stub);
			registry.rebind("serverAZJ", stub);
			System.out.println("Servidor pronto");
		} catch ( Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public String serverToClient( int lstMsg) throws RemoteException{
		if(( lstMsg < 0) || ( lstMsg > mensagens.size()))
			return "-";
		return mensagens.get(lstMsg);
	}
	
	public boolean clientToServer(  String mensage) throws RemoteException{
		System.out.println(mensage);
		if (mensagens == null) {
			mensagens = new ArrayList<String>();
			numMsg = 0;
		}
		mensagens.add(mensage);
		numMsg++;
		return true;
	}
	
}
