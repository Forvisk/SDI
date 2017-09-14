package chatAZJ;
 
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
 
public class ChatClient {
 
	public static void main(String[] args) {
 
		try {
 
			URL url = new URL("http://localhost:9876/chatAZJ?wsdl");
			QName qname = new QName("http://chat/",
					"ChatServerImplService");
 
			Service service = Service.create(url, qname);
 
			ChatServer server = service.getPort(ChatServer.class);
			String name = "prasad";
			System.out.println(server.sayHello(name));
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
	}
 
}
