
import java.io.*;
import java.net.*;
import java.util.*;

class UDPClient_mod {

    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser
                = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress
                = InetAddress.getByName("localhost");
        //byte[] sendData = new byte[1024];
        //byte[] receiveData = new byte[1024];
        byte[] receiveData = new byte[1024*1024];
        byte[] sendData = new byte[1024*1024];
        //String sentence = inFromUser.readLine();
        int n = 0;
        while(n++ < 3){
            String sentence = leituraArquivo();

            //System.out.println(sentence);

            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket
                    = new DatagramPacket(receiveData,
                            receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + modifiedSentence);
        }
        clientSocket.close();
    }


    public static String leituraArquivo(){
        String nomeArquivo;
        String linha = "", newLinha = "";
        BufferedReader inText = new BufferedReader( new InputStreamReader(System.in));
        Scanner leitor = new Scanner(System.in);

        try{
            System.out.println("Nome do arquivo:");
            nomeArquivo = inText.readLine();

            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader leituraArq = new BufferedReader(arq);
            newLinha = leituraArq.readLine();
            do{
                
                if( newLinha != null){
                    //linha.concat(newLinha);
                    linha = linha + "\n" + newLinha;
                    //linha = linha + newLinha;
                }
                newLinha = leituraArq.readLine();
            }while( newLinha != null);
        } catch (IOException e){
            System.err.printf("Erro na abertura do arquivo: %s !\n", e.getMessage());
        }

        System.out.println("Fim da leitura!");
        //System.out.println(linha);

        return linha;
    }
}


