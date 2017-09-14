//Cliente UDP
//ADriano Zanella Junior

import java.io.*;
import java.net.*;

class ClienteUDP_AZJ {

    public static void main(String args[]) throws Exception {
        String arquivo = "";
        BufferedReader inText = new BufferedReader( new InputStreamReader(System.in));

        BufferedReader inFromUser
                = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress
                = InetAddress.getByName("localhost");

        //v1
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        //v2
        //byte[] sendData = new byte[1024*1024];
        //byte[] receiveData = new byte[1024*1024];

        System.out.println( "Arquivo: ");
        arquivo = inText.readLine();

        String sentence = leituraArq( arquivo);

        sendData = sentence.getBytes();
        DatagramPacket sendPacket
                = new DatagramPacket(sendData, sendData.length,
                        IPAddress, 9876);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket
                = new DatagramPacket(receiveData,
                        receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence
                = new String(receivePacket.getData());
        System.out.println("FROM SERVER:"
                + modifiedSentence);
        clientSocket.close();

        //gravaArq( arquivo, modifiedSentence);
    }

    private static String leituraArq( String arquivo){
        //String arquivo;
        String text = "", line = "";
        //BufferedReader inText = new BufferedReader( new InputStreamReader(System.in));

        try{
            //System.out.println( "Arquivo: ");
            //arquivo = inText.readLine();

            FileReader arq = new FileReader( arquivo);
            BufferedReader leitura = new BufferedReader(arq);
            line = leitura.readLine();
            do{
                if( line != null){
                    text = text + "\n" + line;
                    //System.out.println(text);
                }
                line = leitura.readLine();
            }while (line != null);

        }catch( IOException e){
            System.err.printf("\nErro na abertura do arquivo: %s !\n", e.getMessage());
        }

        System.out.println("Fim da leitura!");

        return text;
    }
    /*
    private static void gravaArq( String arquivo, String text){
        FileWriter arq = new FileWriter("/home/udesc/Área de Trabalho/T3_Soquete UDP/new"+arquivo);
        BufferedWriter grava = new BufferedWriter(arq);
        grava.write(text);
        grava.close();
        arq.close();
    }
    */
}
