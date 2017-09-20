package chatAZJ;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.ArrayList;
 
@WebService
@SOAPBinding(style = Style.RPC)
public interface ChatServer {
 
	@WebMethod
	String sayHello(String name);

	@WebMethod
	String getMesage(int posMsg);

	@WebMethod
	boolean loginServer(String username);

	@WebMethod
	boolean sendMesage(String msg);

	@WebMethod
	int getNumMsg();
}