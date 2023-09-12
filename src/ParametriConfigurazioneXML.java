import com.thoughtworks.xstream.*;

public class ParametriConfigurazioneXML { //00
    private final static String FONT_TITOLO_DEFAULT = "Arial"; // 01
    private final static String FONT_TABELLA_DEFAULT = "Courier New"; // 01
    private final static int DIMENSIONE_FONT_TITOLO_DEFAULT = 40; // 01
    private final static int DIMENSIONE_FONT_TABELLA_DEFAULT = 11; // 01
    private final static String CATEGORIA_TRENO_DEFAULT = "REG"; // 01
    private final String fontTitolo; // 02
    private final String fontTabella; // 03
    private final int dimensioneFontTitolo; //04
    private final int dimensioneFontTabella; //05
    private final int numCategorieTreno; //06
    private final String[] categorieTreno; //07
    private final String ipClient; //08
    private final String ipServerLog; //08
    private final int portaServerLog; // 08
    private final String ipDB; // 09
    private final String usernameDB; // 09
    private final String passwordDB; // 09
    private final int portaDB; // 09
    
    public ParametriConfigurazioneXML(String xml){
        categorieTreno = new String[4];
        if(xml == null || xml.compareTo("") == 0) { //10
            fontTitolo = FONT_TITOLO_DEFAULT;
            fontTabella = FONT_TABELLA_DEFAULT;
            dimensioneFontTitolo = DIMENSIONE_FONT_TITOLO_DEFAULT;
            dimensioneFontTabella = DIMENSIONE_FONT_TABELLA_DEFAULT;
            numCategorieTreno = 1;
            categorieTreno[0] = CATEGORIA_TRENO_DEFAULT;
            ipClient = "127.0.0.1"; // 01
            ipServerLog = "127.0.0.1"; // 01
            portaServerLog = 6789; // 01
            ipDB = "127.0.0.1"; // 01
            usernameDB = "root"; // 01
            passwordDB = ""; // 01
            portaDB = 3306; // 01
        } else {
            ParametriConfigurazioneXML p = (ParametriConfigurazioneXML)creaXStream().fromXML(xml);
            fontTitolo = p.fontTitolo;
            fontTabella = p.fontTabella;
            dimensioneFontTitolo = p.dimensioneFontTitolo;
            dimensioneFontTabella = p.dimensioneFontTabella;
            numCategorieTreno = p.numCategorieTreno;
            System.arraycopy(p.categorieTreno, 0, categorieTreno, 0, numCategorieTreno);
            ipClient = p.ipClient;
            ipServerLog = p.ipServerLog;
            portaServerLog = p.portaServerLog;
            ipDB = p.ipDB;
            usernameDB = p.usernameDB;
            passwordDB = p.passwordDB;
            portaDB = p.portaDB;
            
        }
    }
        
    public final XStream creaXStream(){
            XStream xs = new XStream();
            xs.useAttributeFor(ParametriConfigurazioneXML.class, "ipClient"); //11
            xs.useAttributeFor(ParametriConfigurazioneXML.class, "ipServerLog"); //11
            xs.useAttributeFor(ParametriConfigurazioneXML.class, "portaServerLog"); //12
            xs.useAttributeFor(ParametriConfigurazioneXML.class, "ipDB"); //11
            xs.useAttributeFor(ParametriConfigurazioneXML.class, "usernameDB"); //11
            xs.useAttributeFor(ParametriConfigurazioneXML.class, "passwordDB"); //11
            xs.useAttributeFor(ParametriConfigurazioneXML.class, "portaDB"); //12
            return xs;
    }
    
    public String toString(){ //13
        return creaXStream().toXML(this);
    }
    public String getFontTitolo(){return fontTitolo;}
    public String getFontTabella(){return fontTabella;}
    public int getDimensioneFontTitolo(){return dimensioneFontTitolo;}
    public int getDimensioneFontTabella(){return dimensioneFontTabella;}
    
    public int getNumCategorieTreno() {return numCategorieTreno;}
    public String[] getCategorieTreno(){return categorieTreno;}
    
    public String getIpClient(){return ipClient;}
    public String getIpServerLog(){return ipServerLog;}
    public int getPortaServerLog(){return portaServerLog;}
    
    public String getIpDB(){return ipDB;}
    public String getUsernameDB(){return usernameDB;}
    public String getPasswordDB(){return passwordDB;}
    public int getPortaDB() {return portaDB;}
}
    
/*  Note:
 
00) Classe contenente i Parametri di Configurazione, prelevati da un file XML
    Esso contiene:
    - Il numero delle possibili categorie di treno e le possibili categorie
    di treno che l'utente può scegliere nell'inserimento con menù a tendina
    - Il font da usare per il titolo e per la tabella e la dimensione del font
    per titolo e per la tabella
    - L'indirizzo IP del client, l'indirizzo IP e la porta del server di log
    - L'username, la password, l'indirizzo IP e la porta del DBMS.
01) Valori di default nel caso in cui il file XML di configurazione dovesse mancare
    o che non venga validato
02) Font in uso per il titolo dell'interfaccia grafica
    In base alle regole di progettazione XML viene modellato come elemento 
    perché può assumere una moltitudine di valori
03) Font in uso per la tabella e per tutti testi dell'interfaccia grafica
    In base alle regole di progettazione XML viene modellato come elemento 
    perché può assumere una moltitudine di valori
04) Dimensione del font in uso per il titolo dell'interfaccia grafica
    In base alle regole di buona progettazione XML, viene modellato come
    elemento in quanto occorre specificare la sua presenza in un certo ordine,
    ovvero dopo il campo fontTitolo, in quanto le due informazioni sono strettamente 
    correlate
05) Dimensione del font in uso per la tabella e per tutti i testi dell'interfaccia grafica
    In base alle regole di buona progettazione XML, viene modellato come
    elemento in quanto occorre specificare la sua presenza in un certo ordine,
    ovvero dopo il campo fontTabella, in quanto le due informazioni sono strettamente 
    correlate
06) Numero delle possibili categorie di treno
    In base alle regole di buona progettazione XML, viene modellato come
    elemento in quanto occorre specificare la sua presenza in un certo ordine,
    ovvero prima della lista delle categorie di treno, in quanto le due informazioni sono
    strettamente correlate.
07) Possibili categorie di treno
    In base alle regole di buona progettazione XML, viene modellato come
    elemento in quanto puo' assumere una moltitudine di valori
08) Informazioni per raggiungere il Server Log.
    In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto cambia poco frequentemente.
09) Informazioni per raggiungere ed accedere al Database.
    In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto cambia poco frequentemente.
10) Nel caso in cui il file XML di configurazione dovesse mancare o non venga validato,
    il costruttore usa dei valori di default.
11) In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di un numero semplice
12) In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di una stringa semplice
13) Serializza l'oggetto in XML.
*/
