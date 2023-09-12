import java.sql.*;
import java.time.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InterfacciaTrafficoFerroviario {
    private final VBox contenitore;
    private final Group contenitoreUltimaRiga;
    private final GridPane gpTasti; // per allineare i tasti
    private final Label titolo;
    private final TabellaTreni tabellaTreni;
    private final UltimaRiga ultimaRiga;
    private final Button tastoAggiungi;
    private final Button tastoAnnulla;
    private final Transito transito;
    private final Sospensione sospensione;
    private final Storico storico;
    private final TendenzaRitardi tendenzaRitardi;
    private final EventHandler<ActionEvent> annullaTrenoHandler;
    public InterfacciaTrafficoFerroviario(String fileCache, ParametriConfigurazioneXML config)
    {
        contenitore = new VBox();
        contenitoreUltimaRiga = new Group();
        gpTasti = new GridPane();
        titolo = new Label("Railway Traffic Controller");
        
        ultimaRiga = new UltimaRiga(this, config);
        Cache cache = GestoreCache.carica(fileCache);
        if(cache != null){
            ultimaRiga.getCampoCategoria().setValue(cache.getCategoria());
            ultimaRiga.getCampoNumTreno().setText(cache.getNumTreno());
            ultimaRiga.getCampoOrigine().setText(cache.getOrigine());
            ultimaRiga.getCampoDestinazione().setText(cache.getDestinazione());
            ultimaRiga.getCampoOra().setValue(cache.getOraPrevista().getHour());
            ultimaRiga.getCampoMinuti().setValue(cache.getOraPrevista().getMinute());
        }
           
        tabellaTreni = new TabellaTreni(this);
        tastoAggiungi = new Button("AGGIUNGI");
        tastoAnnulla = new Button("ANNULLA");
        transito = new Transito(tabellaTreni.trenoSelezionato, this);
        storico = new Storico(this);
        sospensione = new Sospensione(this);
        tendenzaRitardi = new TendenzaRitardi();
        
        
        if(cache != null)
        {
            tabellaTreni.getTabella().getSelectionModel().select(cache.getTrenoSelezionato());
            transito.getCampoOra().setValue(cache.getOraTransito().getHour());
            transito.getCampoMinuti().setValue(cache.getOraTransito().getMinute());
            storico.getPickerData().setValue(cache.getDataRif());
            sospensione.getCampoTesto().setText(cache.getNote());
        }
        
            
        
        contenitoreUltimaRiga.getChildren().addAll(ultimaRiga.getContenitore());
        
        HBox boxAggiungiAnnulla = new HBox();
        boxAggiungiAnnulla.getChildren().addAll(tastoAggiungi, tastoAnnulla);
        boxAggiungiAnnulla.setSpacing(149);
        
        gpTasti.setPadding(new Insets(10,10,10,10));
        gpTasti.setVgap(40);
        gpTasti.setHgap(30);
        gpTasti.add(boxAggiungiAnnulla, 0, 1);
        gpTasti.add(transito.getGruppoCombo(), 1, 1);
        gpTasti.add(transito.getTastoTransito(), 2, 1);
        gpTasti.add(storico.getBoxSceltaData(), 0, 2);
        gpTasti.add(sospensione.getBoxSospensione(), 1, 2);
        gpTasti.add(sospensione.getTastoSospendi(), 2, 2);
        
        tastoAggiungi.setOnAction(
                (ActionEvent ev) -> {
        String categoria = ultimaRiga.getCategoriaInserita();
        String numTreno = ultimaRiga.getNumTrenoInserito();
        String origine = ultimaRiga.getOrigineInserita();
        String destinazione = ultimaRiga.getDestinazioneInserita();
        LocalTime oraPrevista = LocalTime.of(ultimaRiga.getOraPrevistaInserita(), ultimaRiga.getMinutiPrevistiInseriti());
        Treno t = new Treno(categoria, numTreno, origine, destinazione, Time.valueOf(oraPrevista));
        RailwayController.schedulaTreno(t, this);
        }
        );
        
        annullaTrenoHandler = (ActionEvent ae) -> {
            if(tabellaTreni.trenoSelezionato != null)
                    RailwayController.annullaSchedulazioneTreno(tabellaTreni.trenoSelezionato, this);
        };
        
        tabellaTreni.getTabella().getSelectionModel().selectedItemProperty().addListener(
                (property, vecchioTreno, nuovoTreno) -> {
                    tabellaTreni.trenoSelezionato = nuovoTreno;
                    tastoAnnulla.setOnAction(annullaTrenoHandler);
                }
        );
        
        contenitore.getChildren().addAll(titolo, tabellaTreni.getTabella(), gpTasti, tendenzaRitardi.getContenitore());        
    }
    public void impostaStile(ParametriConfigurazioneXML config)
    {
        titolo.setFont(Font.font(config.getFontTitolo(), FontWeight.BOLD, config.getDimensioneFontTitolo()));
        contenitore.setAlignment(Pos.CENTER);
        tabellaTreni.impostaStile(config);
        ultimaRiga.impostaStile(config);
        transito.impostaStile(config);
        storico.impostaStile(config);
        sospensione.impostaStile(config);
        
        tastoAggiungi.setMinSize(85, 30);
        tastoAggiungi.setFont(Font.font(config.getFontTabella(), config.getDimensioneFontTabella()));
        tastoAggiungi.setStyle("-fx-background-color: rgb(4,84,220); -fx-border-color: rgb(4,84,220); -fx-border-radius: 12px; -fx-text-fill: white");
        tastoAnnulla.setMinSize(85, 30);
        tastoAnnulla.setFont(Font.font(config.getFontTabella(), config.getDimensioneFontTabella()));
        tastoAnnulla.setStyle("-fx-background-color: rgb(224,209,226); -fx-border-color: rgb(224,209,226); -fx-border-radius: 12px;");
        
    }
    public VBox getContenitore() {return contenitore;}
    public Group getContenitoreUltimaRiga() {return contenitoreUltimaRiga;}
    public TabellaTreni getTabellaTreni() {return tabellaTreni;}
    public Transito getTransito() {return transito;}
    public Storico getStorico(){return storico;}
    public Sospensione getSospensione() {return sospensione;}
    public TendenzaRitardi getTendenzaRitardi() {return tendenzaRitardi;}
    public UltimaRiga getUltimaRiga() {return ultimaRiga;}
}


        
        
