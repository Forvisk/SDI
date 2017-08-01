/** HelloClient.java **/
import java.rmi.registry.*;
import java.util.*;
import java.io.*;

public class HelloClientSender extends Thread {
   public void run() { 
      try {
         String msg;
         String host = "localhost";
         Registry registry = LocateRegistry.getRegistry(host);
         HelloWorld stub= (HelloWorld) registry.lookup("Hello");
       
         while (true) {
            Scanner s = new Scanner(System.in);
            msg = s.next();
            stub.sendToServer(msg);
            try {Thread.sleep(500);} catch (InterruptedException ie) {}
           }
      } catch (Exception ex) {
         ex.printStackTrace();
      } 

      //List<String> msg = stub.receiveFromServer();
   }
}
