/** HelloClient.java **/
import java.rmi.registry.*;
import java.util.*;
public class HelloClient {

   public static void main(String[] args){
      
      System.out.println("Welcome to my chat :)");
      
      Thread receiver = new HelloClientReceiver();
      receiver.start();
      
      Thread sender = new HelloClientSender();
      sender.start();
      
   }
}
