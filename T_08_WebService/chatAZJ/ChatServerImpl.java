package chatAZJ;
 
import javax.jws.WebService;
 
@WebService(endpointInterface = "chat.ChatServer")
public class ChatServerImpl implements ChatServer {
 
	public String sayHello(String name) {
		return "Hello " + name + " !, Hope you are doing well !!";
	}
 
}
