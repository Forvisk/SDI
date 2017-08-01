import java.io.*;
import java.net.*;
import java.lang.*;

public class MulticastClient {

	public static void main(String[] args){
		String strUser;
		String strAvalServer, strSocket;
		int iSocketServ;
		Boolean lAvalServer = false;
		try{
			do{
				BufferedReader pedidoUsuario = new BufferedReader( new InputStreamReader(System.in));
				Socket pedidoSocket = new Socket("localhost", 6780);
				DataOutputStream pedidoServer = new DataOutputStream( pedidoSocket.getOutputStream());
				BufferedReader respostaServer = new BufferedReader( new InputStreamReader( pedidoSocket.getInputStream()));
				
				strUser = pedidoUsuario.readLine();
				pedidoServer.writeBytes( strUser + "\n");
				strAvalServer = respostaServer.readLine();
				System.out.println("Resposta: "+strAvalServer);
				if(strAvalServer.equals("ok")){
					strSocket = respostaServer.readLine();
					lAvalServer = true;
				}
				else{
					System.out.println("Conexão não estabelecida");
				}
				pedidoSocket.close();
			}while(!lAvalServer);
		}catch(IOException eped){
			System.out.println(ePed);
		}
		try{
		iSocketServ = parseInt( strSocket);
		}catch( NumberFormatException eInt){
			System.out.println(eInt);
		}
	}

	/*
	private static Runnable sender = new Runnable(){
	}
	private static Runnable receiver = new Runnable(){
	}
	*/
}