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

	private static DatagramSocket socketMult = null;
	private static InetAddress address = null;
	//private DatagramPacket outPacket = null;
	private static int PORT = 8888;

	public static void main( String[] args){
		int portNumber = 2222;

		try{
			serverSocket = new ServerSocket( portNumber);
			socketMult = new DatagramSocket();
			//Send to multicast IP address and port
			address = InetAddress.getByName("224.2.2.3");
		}catch( IOException ex){
			System.out.println("IOException" + ex);
		}

		System.out.println("Server iniciado!");
		while( true){
			try{
				clientSocket = serverSocket.accept();
				int i = 0;
				for (i = 0; i < maxClientsCount; i++){
					if (threads[i] == null){
						(threads[i] = new clientThread( clientSocket, threads, this)).start();
						break;
					}
				}
				if( i == maxClientsCount) {
					clientSocket.close();
				}
			} catch(IOException ex2){
				System.out.println("IOException" + ex2);
			}
		}
	}

	public static void envia( String msgUsu){
		//DatagramSocket socket = null;
		DatagramPacket outPacket = null;
		byte[] outBuf;

		try {
			outBuf = ( msgUsu).getBytes();
			outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
			socketMult.send(outPacket);
			System.out.println(msgUsu);
			try{
				Thread.sleep(500);
			}catch (InterruptedException ie) {
				System.out.println(ie);
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
}
