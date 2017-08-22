import java.io.*;
import java.net.*;
/**
*
* @author Adriano ZJ
*/


public class ServerMulticast{

	// The server socket.
	private static ServerSocket serverSocket = null;
	// The client socket.
	private static Socket clientSocket = null;

  // This chat server can accept up to maxClientsCount clients' connections.
	private static final int maxClientsCount = 10;
	private static final clientThread[] threads = new clientThread[maxClientsCount];

	public void main( String[] args){
		System.out.println("Server iniciado!");
	}

	private static void envia( String msgUsu){
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
				System.out.println(msgSend);
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
