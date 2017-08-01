import java.io.*;
import java.net.*;
import java.util.*;

class TCPClient_mod {

    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser
                = new BufferedReader(
                        new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer
                = new DataOutputStream(
                        clientSocket.getOutputStream());
        BufferedReader inFromServer
                = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //sentence = inFromUser.readLine();

        sentence = leituraArquivo();
        outToServer.writeBytes(sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: "
                + modifiedSentence);
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
                    linha = linha + newLinha;
                }
                newLinha = leituraArq.readLine();
            }while( newLinha != null);
        } catch (IOException e){
            System.err.printf("Erro na abertura do arquivo: %s !\n", e.getMessage());
        }

        System.out.println("Fim da leitura!");
        System.out.println(linha);
        return linha;
    }
}
