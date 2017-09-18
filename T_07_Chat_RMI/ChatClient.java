/** ChatClient.java **/
import java.rmi.registry.*;
import java.rmi.*;
import java.io.*;

public class ChatClient {
	private String username;
	private int numMsg;


	public void main( String[] args){
		String host = (args.length < 1) ? null : args[0];
		numMsg = 0;
		
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			
			//Chat_azj stubC_S = (Chat_azj) registry.lookup("clientToServer");
			//Chat_azj stubS_C = (Chat_azj) registry.lookup("serverToClient");
			Chat_azj stub = (Chat_azj) registry.lookup("serverAZJ");
			/*
			//teste envio de mensagem
			if( stub.clientToServer("Mensagem do Cliente!")){
				System.out.println("Mensagem enviada!");
			}else{
				System.out.println("Erro no envio!");
			}
			*/
			/*
			new Thread(){
				@Override
				public void run(){
					envia();
				}
			}.start();
			*/
			System.out.println("Insira seu usuario: ");
			username = lerMensagem();
			new Thread(){
				@Override
				public void run(){
					while(true){
						try{
							if( !stub.clientToServer(username + ": " + lerMensagem())){
								System.out.println("Erro no envio!");
							}
						}catch( RemoteException rmex){
							System.out.println(rmex);
						}
					} catch (IOException ioe){
						System.out.println(ioe);
					}
				}
			}.start();
			/*
			//teste recebimento de mensagem
			String msg = stub.serverToClient();
			System.out.println("Mensagem do servidor: " + msg);
			*/
			new Thread(){
				@Override
				public void run(){
					while(true){
						try{
							String income = stub.serverToClient( numMsg);

							if( income != "-"){
								System.out.println(income);
								numMsg++;
							}
						}catch( RemoteException re){
							re.printStackTrace();
						}
					}
				}
			}.start();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String lerMensagem(){
		String ret = "";
		BufferedReader inText = new BufferedReader( new InputStreamReader(System.in));
		try{
			ret = inText.readLine();
		}catch (IOException ioe){
			System.out.println(ioe);
		}
		return ret;
	}
}
