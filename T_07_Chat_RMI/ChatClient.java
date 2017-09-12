/** ChatClient.java **/
import java.rmi.registry.*;
import java.io.*;

public class ChatClient {
	public static void main( String[] args){
		String host = (args.length < 1) ? null : args[0];
		
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			
			//Chat stubC_S = (Chat) registry.lookup("clientToServer");
			//Chat stubS_C = (Chat) registry.lookup("serverToClient");
			Chat stub = (Chat) registry.lookup("serverAZJ");
			//teste envio de mensagem
			if( stub.clientToServer("Mensagem do Cliente!")){
				System.out.println("Mensagem enviada!");
			}else{
				System.out.println("Erro no envio!");
			}

			//teste recebimento de mensagem
			String msg = stub.serverToClient();
			System.out.println("Mensagem do servidor: " + msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
