/*
* The chat client thread. This client thread opens the input and the output
* streams for a particular client, ask the client's name, informs all the
* clients connected to the server about the fact that a new client has joined
* the chat room, and as long as it receive data, echos that data back to all
* other clients. When a client leaves the chat room this thread informs also
* all the clients about that and terminates.
*/
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

class clientThread extends Thread {

	private DataInputStream is = null;
	//private PrintStream os = null;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount;

	//Rotina envia
	private DatagramSocket socketMult = null;
	private InetAddress address = null;
	//private DatagramPacket outPacket = null;
	private final int PORT = 8888;

	public clientThread(Socket clientSocket, clientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	public void envia( String msgUsu){
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

	public void run() {
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;

		try {
		/*
		* Create input and output streams for this client.
		*/
		is = new DataInputStream(clientSocket.getInputStream());
		//os = new PrintStream(clientSocket.getOutputStream());
		//os.println("Enter your name.");
		String name = is.readLine().trim();
		//os.println("Hello " + name
		//	+ " to our chat room.\nTo leave enter /quit in a new line");
		/*for (int i = 0; i < maxClientsCount; i++) {
			if (threads[i] != null && threads[i] != this) {
				threads[i].os.println("*** A new user " + name
					+ " entered the chat room !!! ***");
			}
		}*/
		envia("Usuario " + name + "entrou no chat!");
		while (true) {
			String line = is.readLine();
			if (line.startsWith("/quit")) {
				break;
			}
			/*for (int i = 0; i < maxClientsCount; i++) {
				if (threads[i] != null) {
					threads[i].os.println("<" + name + "&gr; " + line);
				}
			}*/
			envia(">" + name + "&gr; " + line);
		}
		/*for (int i = 0; i < maxClientsCount; i++) {
			if (threads[i] != null && threads[i] != this) {
				threads[i].os.println("*** The user " + name
					+ " is leaving the chat room !!! ***");
			}
		}*/
		envia("O usuario " + name + "Saiu do chat!");
		//os.println("*** Bye " + name + " ***");

		/*
		* Clean up. Set the current thread variable to null so that a new client
		* could be accepted by the server.
		*/
		for (int i = 0; i < maxClientsCount; i++) {
			if (threads[i] == this) {
				threads[i] = null;
			}
		}

		/*
		* Close the output stream, close the input stream, close the socket.
		*/
		is.close();
		//os.close();
		clientSocket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}