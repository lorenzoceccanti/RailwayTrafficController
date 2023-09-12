import java.io.*;
import java.net.*;

public class ServerLogXML {
    private final static int PORTA = 6789;
    private final static String FILE_LOG = "log.xml";
    private final static String SCHEMA_LOG = "log.xsd";
    
    public static void main(String[] args){
        System.out.println("*************");
        System.out.println("Server Log avviato.\n");
        System.out.println("*************");
        
        try(
        ServerSocket svSock = new ServerSocket(PORTA);
        ){
            while(true)
            {
                try(
                    Socket sock = svSock.accept();
                    ObjectInputStream oin = new ObjectInputStream(sock.getInputStream());
                )
                {
                    String xml = (String) oin.readObject();
                    System.out.println(xml);
                    if(ValidatoreXML.valida(xml, SCHEMA_LOG))
                        GestoreFile.salva(xml, FILE_LOG);
                }
            }
        } catch(IOException | ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
    }
                
    
}
