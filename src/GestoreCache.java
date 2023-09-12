import java.io.*;

public class GestoreCache { //00
    public final static void salva(InterfacciaTrafficoFerroviario itf, String file) //01
    {
        try(
        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file)); //02
        ) { oout.writeObject(new Cache(itf));} //03
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        catch(NumberFormatException nbe)
        {
            System.err.println("IMPOSSIBILE SALVARE");
        }
    }
    
    public final static Cache carica(String file){ //04
        try(
        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file)); //02
        ){ return (Cache) oin.readObject();} //05
        catch(IOException | ClassNotFoundException e){
            System.err.println(e.getMessage() + "\nNon c'è alcun dato da caricare.");
        }
        return null; //06
    }
}
/* Note:

00) Classe che salva e importa la cache locale degli input su file binario
01) Salva la cache locale degli input sull'interfaccia itf sul file indicato dalla
    stringa file
02) Si aggancia il flusso oggetto al flusso file
03) Scrittura dell'oggetto Cache sul file binario.
    L'oggetto Cache viene creato sul momento, prelevando gli input presenti
    nell'Interfaccia Grafica.
04) Carica dal file binario la cache degli input relativa all'ultima esecuzione
05) Il metodo restituisce l'oggetto Cache letto dal file binario, che verrà utilizzato
    per ripristinare lo stato precedente dell'Interfaccia Grafica
06) In caso di errore (file binario non trovato o file Cache.class non trovato),
    il metodo restituisce null. In tal caso, l'interfaccia non ripristinerà
    lo stato della precedente esecuzione.
*/
