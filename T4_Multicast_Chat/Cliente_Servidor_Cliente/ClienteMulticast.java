	import java.io.*;
	import java.net.*;
	/**
	*
	* @author Adriano ZJ
	*/

	public class ClienteMulticast{
		private static String usuario;
		private static Socket clientSocket = null;

		private static BufferedReader inText = null;
		private PrintStream outText = null;

		private static int portNumber = 2222;
		private static String host = "localhost";

		private boolean closed = false;

		public void main( String[] args){
			//System.out.println("Nome:");
			//usuario = lerMensagem();
			System.out.println("Iniciando Cliente...");

			try {
				clientSocket = new Socket( host, portNumber);
				inText = new BufferedReader( new InputStreamReader(System.in));
				outText = new PrintStream( clientSocket.getOutputStream());

			} catch (UnknownHostException e) {
				System.err.println("Host deesconhecido " + host);
			} catch (IOException e) {
				System.err.println("Algum erro " + host);
			}
			if ( clientSocket != null){
				try {
					/*
					new Thread(){
						@Override
						public void run(){
							envia();
						}
					}.start();
					*/
					new Thread(){
						@Override
						public void run(){
							recebe();
						}
					}.start();
					System.out.println("Digite seu usuario: ");
					while(!closed){
						envia();
					}
					if ( closed)
						inText.close();
						clientSocket.close();
				} catch (Exception ex) {
                	System.err.println("IOException:  " + ex);
				}
			}
		}


		private void envia(){
			try{
				outText.println(inText.readLine().trim());
			} catch (IOException ioe){
				System.out.println(ioe);
			}
		}

		private String lerMensagem(){
			String ret = "";
			//BufferedReader inText = new BufferedReader( new InputStreamReader(System.in));
			try{
				ret = inText.readLine();
			}catch (IOException ioe){
				System.out.println(ioe);
			}
			return ret;
		}

		private void recebe(){
			MulticastSocket socket = null;
			DatagramPacket inPacket = null;
			byte[] inBuf = new byte[256];
			String msg;

			try {
	//Prepare to join multicast group
				socket = new MulticastSocket(8888);
				InetAddress address = InetAddress.getByName("224.2.2.3");
				socket.joinGroup(address);

				do{
					inPacket = new DatagramPacket(inBuf, inBuf.length);
					socket.receive(inPacket);
					msg = new String(inBuf, 0, inPacket.getLength());
	//System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
					System.out.println( msg);
					if( msg.indexOf("*** Bye") != -1){
						break;
					}
				}while( msg != null);
				closed = true;
			} catch (IOException ioe) {
				System.out.println("IOException " + ioe);
			}
		}

	}