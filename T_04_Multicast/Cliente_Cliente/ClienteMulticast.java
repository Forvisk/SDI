import java.io.*;
import java.net.*;
/**
*
* @author Adriano ZJ
*/

public class ClienteMulticast{
  private static String usuario;

  public static void main( String[] args){
    System.out.println("Nome:");
    usuario = lerMensagem();

    try {
      new Thread(){
        @Override
        public void run(){
          envia();
        }
      }.start();

      new Thread(){
        @Override
        public void run(){
          recebe();
        }
    }.start();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  private static void envia(){
    DatagramSocket socket = null;
    DatagramPacket outPacket = null;
    byte[] outBuf;
    final int PORT = 8888;

    try {
      socket = new DatagramSocket();
      String msgSend;

      while (true){
        msgSend = lerMensagem();
        outBuf = (usuario + " : " + msgSend).getBytes();

        //Send to multicast IP address and port
        InetAddress address = InetAddress.getByName("224.2.2.3");
        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);

        socket.send(outPacket);
        System.out.println("You: "+msgSend);
        try{
          Thread.sleep(500);
        }catch (InterruptedException ie) {
        }
      }
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
  }

  private static String lerMensagem(){
    String ret = "";
    BufferedReader inText = new BufferedReader( new InputStreamReader(System.in));
    try{
      ret = inText.readLine();
    }catch (IOException ioe){
      System.out.println(ioe);
    }
    return ret;
  }

  private static void recebe(){
    MulticastSocket socket = null;
    DatagramPacket inPacket = null;
    byte[] inBuf = new byte[256];

    try {
      //Prepare to join multicast group
      socket = new MulticastSocket(8888);
      InetAddress address = InetAddress.getByName("224.2.2.3");
      socket.joinGroup(address);
 
      while (true) {
        inPacket = new DatagramPacket(inBuf, inBuf.length);
        socket.receive(inPacket);
        String msg = new String(inBuf, 0, inPacket.getLength());
        //System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
        System.out.println( msg);
      }
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
  }

}