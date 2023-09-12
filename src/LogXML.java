import com.thoughtworks.xstream.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class LogXML implements Serializable{ //00
    private final String 
                        nomeApplicazione, 
                        indirizzoIpClient, 
                        dataOraCorrente,
                        nomeEvento; //01
   
    public LogXML(String nomeApplicazione, String indirizzoIpClient, 
    String nomeEvento) {
        this.nomeApplicazione = nomeApplicazione;
        this.indirizzoIpClient = indirizzoIpClient;
        dataOraCorrente = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date()); //02
        this.nomeEvento = nomeEvento;
    }
    
    public String toString(){ //03
        XStream xs = new XStream();
        xs.useAttributeFor(LogXML.class, "nomeApplicazione"); //04
        xs.useAttributeFor(LogXML.class, "indirizzoIpClient"); //04
        xs.useAttributeFor(LogXML.class, "dataOraCorrente"); //04
        xs.useAttributeFor(LogXML.class, "nomeEvento"); //04
        return xs.toXML(this) + "\n"; //05
    }
}
/* Note:

00) Classe che rappresenta una riga di log da inviare ad un server log,
    ogniqualvolta viene avviata/chiusa l'applicazione, premuto
    uno dei tasti o selezionata la data di riferimento
01) Un log contiene il nome dell'evento, l'indirizzo IP del client, la data
    e l'ora di creazione del log e il nome dell'Applicazione.
02) Per ottenere la current_date, basta creare un oggetto di tipo java.util.Date
    utilizzando la versione di costruttore priva di parametri
    Da specifica questo fornisce l'istante di creazione in millisecondi
    https://docs.oracle.com/javase/8/docs/api/java/util/Date.html#Date--
    Per ottenere la current_date mostrata nel formato italiano gg/mm/aaaa
    va utilizzato il metodo format() della classe SimpleDateFormat
    http://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html#format-java.util.Date-java.lang.StringBuffer-java.text.FieldPosition-
03) Metodo che converte un oggetto di tipo LogXML in una stringa XML
04) In base alle regole di buona progettazione XML, ogni membro della classe
    e' stato scelto come attributo in quanto si tratta di una stringa semplice
05) Si mette un newline in fondo per questioni di leggibilità nel file log.xml
*/
