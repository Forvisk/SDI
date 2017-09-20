package chatAZJ;
 
import javax.jws.WebService;
import java.util.ArrayList;
 
@WebService(endpointInterface = "chatAZJ.ChatServer")
public class ChatServerImpl implements ChatServer {
 	private int numMsg;
 	private ArrayList<String> mensagens;

	public String sayHello(String name) {
		return "Hello " + name + " !, Hope you are doing well !!";
	}

	public String getMesage( int lstMsg){
		if(( lstMsg < 0) || ( lstMsg > mensagens.size()))
			return "-";
		return mensagens.get(lstMsg);
	}
 
 	public boolean loginServer(String username){
 		mensagens.add(">>Usuario "+username+" se conectou!");
 		return true;
 	}

 	public int getNumMsg(){
 		return numMsg;
 	}


	public boolean sendMesage(String msg){
		System.out.println(msg);
		if (mensagens == null) {
			mensagens = new ArrayList<String>();
			numMsg = 0;
		}
		mensagens.add(msg);
		numMsg++;
		return true;
	}
}
