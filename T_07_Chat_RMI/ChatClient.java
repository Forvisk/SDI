/** ChatClient.java **/
import java.rmi.*;

public class ChatClient {
	public static void main( String[] args){
		String host = (args.length < 1) ? null : args[0];
		
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			
			Chat stub = (Chat) registry.lookup("clientToServer");
			Chat stub = (Chat) registry.lookup("serverToClient");
			
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
