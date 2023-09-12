import java.io.*;
import java.net.*;

public class GestoreLogXML {
    private static String IP_SERVER_LOG;
    private static int PORTA_SERVER_LOG;
    private static String IP_CLIENT;
    
    private static void invia(LogXML log, String ipServerLog, int portaServerLog)
    {
        try(
        Socket sock = new Socket(ipServerLog, portaServerLog);
        ObjectOutputStream oout = new ObjectOutputStream(sock.getOutputStream());
      ){
            oout.writeObject(log.toString());
      } catch(IOException e)
      {
          System.err.println(e.getMessage());
      }
    }
           
    public static void impostaValoriLog(ParametriConfigurazioneXML config)
    {
        IP_SERVER_LOG = config.getIpServerLog();
        PORTA_SERVER_LOG = config.getPortaServerLog();
        IP_CLIENT = config.getIpClient();
    }
    
    public static void creaLog(String nomeApp, String etichetta)
    {
        GestoreLogXML.invia(new LogXML(nomeApp, IP_CLIENT, etichetta), IP_SERVER_LOG,
        PORTA_SERVER_LOG);
    }
    
    public static int getPortaServerLog()
    {
        return PORTA_SERVER_LOG;
    }
}
