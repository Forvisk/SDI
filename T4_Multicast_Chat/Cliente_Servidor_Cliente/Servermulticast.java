import java.io.*;
import java.net.*;
/**
*
* @author Adriano ZJ
*/


public class ServerMulticast{

	public void main(){
		System.out.println("Server iniciado!");
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
}
