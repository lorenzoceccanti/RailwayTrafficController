import java.io.*;
import java.nio.file.*;

public class GestoreFile { // 00
    private final static String PERCORSO_RELATIVO = "..\\"; //01
    
    public static void salva(String s, String file)  //02
    {
        try{
            Files.write(Paths.get(PERCORSO_RELATIVO + file), s.getBytes(), StandardOpenOption.APPEND); //03
        } catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
   
    public static String carica(String file) { // 08)
        try {
            return new String(Files.readAllBytes(Paths.get(file))); // 09)
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null; // 10)
    }
}

/* Note
    
00) Classe che si occupa di scrivere e leggere dati da file
01) GestoreFile.class si trova in RailwayController\build\classes
    (\RailwayController\ è la directory root dell'applicazione)
    I file binari e XML su cui scrivere si trovano nella root
    pertanto sono due salti indietro.
02) Il metodo permette di salvare una stringa (insieme di caratteri di un
    file XML) nel file indicato dal parametro String file.
03) Viene eseguita  la scrittura
    L'ultimo parametro specifica la modalità di scrittura.
    https://docs.oracle.com/javase/7/docs/api/java/nio/file/StandardOpenOption.html
04) Carica il contenuto di un file e lo restituisce come String
05) Viene eseguita la lettura
06) Il metodo ritorna null nel caso la lettura non sia andata a buon fine

*/