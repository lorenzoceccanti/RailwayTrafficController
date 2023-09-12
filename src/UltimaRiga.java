import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class UltimaRiga { //00
    private final HBox boxUltimaRiga;
    private final ComboBox categoriaIns; // 01
    private final TextField
            numTrenoIns, origineIns, destinazioneIns; // 02
    private final ComboBox
            oraPrevistaIns, minutiPrevistiIns; //03
    
    public UltimaRiga(InterfacciaTrafficoFerroviario itf, ParametriConfigurazioneXML config)
    {
        boxUltimaRiga = new HBox();
        HBox boxAusiliario = new HBox();
        categoriaIns = new ComboBox();
        
        for(int i=0; i< config.getNumCategorieTreno(); i++)
            categoriaIns.getItems().add(config.getCategorieTreno()[i]);
        
        boolean boxCategoriaInsVuoto = categoriaIns.getSelectionModel().isEmpty(); //05
        if(boxCategoriaInsVuoto == true) // 05
            categoriaIns.setValue("Cat."); // 05
        
        numTrenoIns = new TextField("#");
        origineIns = new TextField("Origine");
        destinazioneIns = new TextField("Destinazione");
        
        oraPrevistaIns = new ComboBox();
        oraPrevistaIns.getItems().setAll(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList())); // 06
        boxCategoriaInsVuoto = oraPrevistaIns.getSelectionModel().isEmpty(); //05
        if(boxCategoriaInsVuoto == true) // 05
            oraPrevistaIns.setValue("Ore"); // 05
        
        minutiPrevistiIns = new ComboBox();
        minutiPrevistiIns.getItems().setAll(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList())); // 06
        boxCategoriaInsVuoto = minutiPrevistiIns.getSelectionModel().isEmpty(); //05
        if(boxCategoriaInsVuoto == true) // 05
            minutiPrevistiIns.setValue("Minuti"); // 05
        
        boxAusiliario.getChildren().addAll(oraPrevistaIns, minutiPrevistiIns);
        boxUltimaRiga.getChildren().addAll(categoriaIns, numTrenoIns, origineIns, destinazioneIns, boxAusiliario);
    }
    
    public void impostaStile(ParametriConfigurazioneXML config) // 07
    {
        numTrenoIns.setFont(Font.font(config.getFontTabella(), config.getDimensioneFontTabella()));
        origineIns.setFont(Font.font(config.getFontTabella(), config.getDimensioneFontTabella()));
        destinazioneIns.setFont(Font.font(config.getFontTabella(), config.getDimensioneFontTabella()));
        
        
        boxUltimaRiga.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
        categoriaIns.setMinWidth(120);
        numTrenoIns.setMaxWidth(120);
        origineIns.setMaxWidth(120);
        destinazioneIns.setMaxWidth(120);
        oraPrevistaIns.setMaxWidth(65);
        minutiPrevistiIns.setMaxWidth(77);
    }
    
    public HBox getContenitore(){return boxUltimaRiga;}
    public String getCategoriaInserita(){return categoriaIns.getValue().toString();}
    public String getNumTrenoInserito() {return numTrenoIns.getText();}
    public String getOrigineInserita() {return origineIns.getText();}
    public String getDestinazioneInserita() {return destinazioneIns.getText();}
    public int getOraPrevistaInserita(){return Integer.parseInt(oraPrevistaIns.getValue().toString());}
    public int getMinutiPrevistiInseriti(){return Integer.parseInt(minutiPrevistiIns.getValue().toString());}
    public ComboBox getCampoCategoria() {return categoriaIns;}
    public TextField getCampoNumTreno() {return numTrenoIns;}
    public TextField getCampoOrigine() {return origineIns;}
    public TextField getCampoDestinazione() {return destinazioneIns;}
    public ComboBox getCampoOra() {return oraPrevistaIns;}
    public ComboBox getCampoMinuti() {return minutiPrevistiIns;}
    
    
}
    
/*  Note:
00) Porzione di interfaccia grafica che realizza l'ultima riga della tabella editabile
01) Menù a tendina da cui scegliere la categoria di treno
02) Campi di testo in cui si inserisce numero, origine, destinazione del prossimo
    transito da schedulare
03) Selezione dell'orario previsto di transito tramite
    due menù a tendina, uno per l'ora e l'altro per i minuti
    (un'equivalente di DatePicker per l'ora e minuti è assente in javafx2)
04) Possibili categorie di treno selezionabili dal menù a tendina
05) Questa porzione di codice serve per mostrare la scritta "Cat." come placeholder
    quando nessuna categoria è stata ancora scelta dal menù a tendina
06) Con IntStream.rangeClosed(IntegerA,IntegerB).boxed().collect(Collectors.toList()) genero
    degli Integer da IntegerA a IntegerB estremi inclusi che verranno confezionati in un unico
    Stream di tipo IntStream a sua volta poi confezionati in un'unica lista con l'uso
    del metodo toList() della classe Collectors
    Con getItems().setAll() in un colpo solo aggiungo tutti gli elementi della lista alla comboBox
07) Imposta lo stile dell'ultima riga ricevendo il font-family e dimensione del font dai parametri
    di configurazione
    
*/
