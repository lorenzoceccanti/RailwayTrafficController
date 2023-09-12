import java.io.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ValidatoreXML {
    private final static String PERCORSO_RELATIVO = "..\\";
    
    public static boolean valida(String xml, String fileSchema)
    {
        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); // 01
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); //02
            Document d; //03
            d = db.parse(new InputSource(new StringReader(xml))); //04
            Schema s = sf.newSchema(new StreamSource(new File (((fileSchema.compareTo("log.xsd") == 0) ? PERCORSO_RELATIVO : "") + fileSchema ))); //05
            s.newValidator().validate(new DOMSource(d)); //06
        } catch(ParserConfigurationException | SAXException | IOException e)
        {
            if(e instanceof SAXException) //07
                System.err.println("Errore di validazione: " + e.getMessage());
            else
                System.err.println(e.getMessage());
            return false; //08
        }
       return true; //09
    }           
}

/* Note:
   01)  Dato che bisogna fare validazione di documenti XML modificabili da agenti esterni
        (e.g.: il file XML è un file di configurazione, è un file proveniente da socket, ..)
        la validazione è dinamica utilizzando le API Java.xml.validation
        https://docs.oracle.com/javase/8/docs/api/javax/xml/validation/package-summary.html
        DocumentBuilder istanzia dei parser che producono oggetti DOM da documenti XML
   02)  SchemaFactory legge rappresentazioni esterne di schemi per la validazione
        Funge da compilatore di schema XML e necessita di un XML schema namespace per definire
        un linguaggio di schema
        https://docs.oracle.com/javase/8/docs/api/javax/xml/validation/SchemaFactory.html
   03)  Oggetto in grado di rappresentare un documento DOM, che può essere un documento HTML, o
        come in questo caso, un documento XML.
        https://docs.oracle.com/javase/8/docs/api/org/w3c/dom/Document.html
   04)  Creazione di un oggetto XML da una stringa
        (fonte: https://stackoverflow.com/questions/7607327/how-to-create-a-xml-object-from-string-in-java)
        DocumentBuilder permette di fare il parsing non solo da file, ma anche da un InputSource:
        https://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilder.html#parse-org.xml.sax.InputSource-
        che rappresenta una sorgente ottenibile da un flusso di caratteri. Si associa un flusso di caratteri
        ad un InputSource con la versione di costruttore che ha come parametro un oggetto Reader
        https://docs.oracle.com/javase/8/docs/api/org/xml/sax/InputSource.html#InputSource-java.io.Reader-
        In realtà l'argomento formale è di tipo StringReader che è una sottoclasse di Reader.
        Il costruttore di tale sottoclasse ha come parametro un tipo String, quindi si passa la stringa con il contenuto
        del file XML.
   05)  Schema è l'oggetto schema vero e proprio creato dallo SchemaFactory a partire dal file XSD
        La compareTo serve per distinguere se il file schema è log.xsd oppure config.xsd, dato che questo metodo
        può essere chiamato anche per validare il file schema dei parametri di configurazione.
        La differenza tra i due casi è che la classe che invoca la validazione di log.xsd è ServerLogXML 
        che è l'unica classe che viene mandata in esecuzione dalla directory RailwayController\build\classes; pertanto
        in quel caso va anteposto il percorso relativo per saltare due volte di fila alla directory padre.
   06)  Viene creato un oggetto validatore che valida il documento DOM derivante
        dal documento XML, sullo Schema appena creato.
   07)  Si ha SAXException con eccezione dovuta ad errore di validazione.
   08)  La validazione non ha successo nemmeno se si verifica IOException o ParserConfigurationException
   09)  Se nessuna eccezione viene lanciata, la validazione e' avvenuta correttamente.
*/