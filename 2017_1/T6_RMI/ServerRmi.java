 
/** HelloServer.java **/
 
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

/*
public class HelloServer implements HelloWorld {
   public HelloServer() {}
   // main()
   // hello()

   public static void main(String[] args) {
      try {
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

public String hello() throws RemoteException {
   System.out.println("Executando hello()");
   return "Hello!!!";
}
}
*/

public class ServerRmi implements ChatRmi {
   String log[];
   int posA = -1;
   public ServerRmi() {}

   public static void main(String[] args) {
      try {
         // Instancia o objeto servidor e a sua stub  
         ServerRmi server = new ServerRmi();
         ChatRmi stub = (ChatRmi) UnicastRemoteObject.exportObject( server, 0);

         // Registra a stub no RMI Registry para que ela seja obtida pelos clientes
         Registry registry = LocateRegistry.getRegistry();
         registry.bind("server", stub);
         System.out.println("Servidor pronto");
      } catch (Exception ex) {
         ex.printStackTrace();
         System.exit(0);
      } 
   }


   public boolean send( String message) throws RemoteException{
      posA++;
      if( posA > 9)
         posA = 0;
      log[posA] = message;

      return true;
   }

   public String receive( int pos) throws RemoteException{
      if( pos != posA){
         pos++;
         if(pos > 9)
            pos = 0;
         System.out.println("Enviando mensagem");
         return log[pos];
      } else
         return null;
   }
}
