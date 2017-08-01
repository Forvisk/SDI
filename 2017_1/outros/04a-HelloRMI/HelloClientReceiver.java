/** HelloClient.java **/
import java.rmi.registry.*;
import java.util.*;
import java.io.*;

public class HelloClientReceiver extends Thread {
   static ArrayList<String> msg_list;
   public void run() { 
      try {
         String host = "localhost";
         Registry registry = LocateRegistry.getRegistry(host);
         HelloWorld stub= (HelloWorld) registry.lookup("Hello");
         int msgcount;
         msg_list = new ArrayList<String>();
         int msg_list_size = 0;

         while(true){
            msgcount = stub.countMessages();
            msg_list_size = msg_list.size();
            msg_list = stub.receiveFromServer();
            if(msg_list.size() > 0){
               for(int i=0; i < msg_list.size(); i++){
                  System.out.println("Message: "+msg_list.get(i));
               }
            }

            try {Thread.sleep(500);} catch (InterruptedException ie) {}
         }
            
      } catch (Exception ex) {
         ex.printStackTrace();
      } 
   }
}
