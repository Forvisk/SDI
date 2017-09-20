package chatAZJ;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.*;

public class ChatClient {
	//private int lstMsg;
	private static String username;
	private static ChatServer server;

	public static void main(String[] args) {

		try {

			URL url = new URL("http://127.0.0.1:9876/chatAZJ?wsdl");
			QName qname = new QName("http://chatAZJ/",
				"ChatServerImplService");

			Service service = Service.create(url, qname);

			//ChatServer server = service.getPort(ChatServer.class);
			server = service.getPort(ChatServer.class);
			//String name = "prasad";
			//System.out.println(server.sayHello(name));

			while(!conectar());
			
			//Thread de receber mensagens
			new Thread(){
				@Override
				public void run(){
					recebe();
				}
			}.start();

			//Thread para enviar mensagens
			new Thread(){
				@Override
				public void run(){
					enviar();
				}
			}.start();


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static boolean conectar(){
		username = ler();
		return server.loginServer(username);
	}

	private static String ler(){
		String ret = "";
		BufferedReader inText = new BufferedReader( new InputStreamReader(System.in));
		try{
			ret = inText.readLine();
		}catch (IOException ioe){
			System.out.println(ioe);
		}
		return ret;
	}

	private static void enviar(){
		String msg = "";
		try{
			while(true){
				msg = ler();
				server.sendMesage(">>"+username+": "+msg);
				Thread.sleep(1000);
			}
		}catch(InterruptedException e){
			System.out.println(e);
		}

	}

	private static void recebe(){
		int lstMsg = server.getNumMsg();
		String msg = "-";
		try{
			while(true){
				msg = server.getMesage(lstMsg);
				if( msg != "-"){
					System.out.println(msg);
					lstMsg++;
				}else{
					Thread.sleep(2000);
				}
			}
		}catch(InterruptedException e){
			System.out.println(e);
		}
	}


}
