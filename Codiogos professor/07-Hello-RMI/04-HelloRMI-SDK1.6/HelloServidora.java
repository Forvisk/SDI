import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.swing.*;

public class HelloServidora extends PortableRemoteObject implements Hello{
    public HelloServidora() throws RemoteException{}

    public void imprimirOla(String oqImprimir) throws RemoteException{
        System.out.println("Mensagem enviada pelo objeto Cliente"+oqImprimir);
        JOptionPane.showMessageDialog(null,oqImprimir,"Mensagem do Objeto Cliente",JOptionPane.INFORMATION_MESSAGE);
    }
}
