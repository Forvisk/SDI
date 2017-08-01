 
/** HelloServer.java **/
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
 
public class HelloServer implements HelloWorld {
   public HelloServer() {}
   // main()
   // hello()

   static ArrayList<String> msg_list; 

   public static void main(String[] args) {
      try {
         msg_list = new ArrayList<String>();
         // Instancia o objeto servidor e a sua stub  
         HelloServer server = new HelloServer();
         HelloWorld stub = (HelloWorld) UnicastRemoteObject.exportObject(server, 0);
         // Registra a stub no RMI Registry para que ela seja obtida pelos clientes
         Registry registry = LocateRegistry.getRegistry();
         registry.bind("Hello", stub);
         System.out.println("Servidor pronto");
      } catch (Exception ex) {
         ex.printStackTrace();
      } 
   }

   public boolean sendToServer(String msg) throws RemoteException {
      System.out.println("Mensagem recebida: "+msg);
      msg_list.add(msg);
      return true;
   }

   public ArrayList<String> receiveFromServer(){
      return msg_list;
   }
   public int countMessages(){
      return msg_list.size();
   }
}

