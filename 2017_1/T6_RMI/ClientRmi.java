/** HelloClient.java **/
import java.rmi.registry.*;
import java.io.*;

/*
public class HelloClient {
   public static void main(String[] args) {
      String host = (args.length < 1) ? null : args[0];
      try {
         // Obtém uma referência para o registro do RMI
         Registry registry = LocateRegistry.getRegistry(host);
 
         // Obtém a stub do servidor
         HelloWorld stub= (HelloWorld) registry.lookup("Hello");
 
         // Chama o método do servidor e imprime a mensagem
         String msg = stub.hello();
         System.out.println("Mensagem do Servidor: " + msg); 
      } catch (Exception ex) {
         ex.printStackTrace();
      } 
   }
}
*/

public class ClientRmi {
   static int pos = -1;
   public static void main( String[] args){
      String host = (args.length < 1) ? null : args[0];
      try {
         // Obtém uma referência para o registro do RMI
         Registry registry = LocateRegistry.getRegistry(host);
 
         // Obtém a stub do servidor
         ChatRmi stub = (ChatRmi) registry.lookup("server");
 
         // Chama o método do servidor e imprime a mensagem
         new Thread(){
            @Override
            public void run(){
               try{

                  String msg;
                  boolean sOk = true;
                  while(sOk){
                     msg = enviar();
                     sOk = stub.send(msg);
                     pos++;
                  }

               }catch(Exception se){
                  se.printStackTrace();
               }
               System.out.println("Finalizando serviço");
            }
         }.start();

         new Thread(){
            @Override
            public void run(){
               try{
                  boolean rOk = true;
                  long timeIni;
                  String msg = null;
                  while(rOk){
                     timeIni = System.currentTimeMillis();
                     while( System.currentTimeMillis() - timeIni < 2000){
                        msg = stub.receive( pos);
                     }
                     if( msg != null){
                        pos++;
                        System.out.println(msg);
                     }
                  }
               }catch(Exception re){
                  re.printStackTrace();
               }
               System.out.println("Finalizando serviço");
            }
         }.start();
      } catch (Exception ex) {
         ex.printStackTrace();
      } 
   }
   /*
   public static boolean receber(){
      String msg;
      long tempoIni = System.currentTimeMillis();
      while( System.currentTimeMillis() - tempoIni < 2000);
      msg = stub.receive();
      if( msg == null)
         return false;
      else         
         System.out.println(msg);
      return true;
   }
   */
   
   public static String enviar(){
      String envio;
      try{
         BufferedReader entrada = new BufferedReader( new InputStreamReader(  System.in));

         envio = entrada.readLine();
      }catch( IOException e){
         System.out.println(e);
         return null;
      }
      //boolean bResp = stub.send(envio);
      return envio;
   }
   
}

/*
new Thread () {
    @Override
    public void run () {
        while (true) {
            receber ();
        }
    }
}.start ();
*/