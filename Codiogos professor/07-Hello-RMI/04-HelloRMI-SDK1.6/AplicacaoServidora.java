import java.rmi.*;

public class AplicacaoServidora{
    public static void main(String argv[]){
        HelloServidora objetoServidor;

        try{
            objetoServidor = new HelloServidora();
            Naming.rebind("//localhost/ObjetoServidor",objetoServidor);
            //Naming.rebind("rmi://asterix.galia/ObjetoServidor1",objetoServidor);
            System.out.println("ObjetoServidor esta ativo!");
        }
        catch(RemoteException re){
            System.out.println("Erro Remoto: "+re.toString());
        }
        catch(Exception e){
            System.out.println("Erro Local: "+e.toString());
        }
    }
}
