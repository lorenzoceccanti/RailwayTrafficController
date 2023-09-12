import java.sql.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;

public class TabellaTreni {
    private final VBox contenitore;
    private final TableView <Treno> tabella;
    private final TableColumn
            categoriaCol, numTrenoCol, origineCol,
            destinazioneCol, oraPrevistaCol, oraTransitoCol,
            noteCol;
    private ObservableList <Treno> treni;
    public Treno trenoSelezionato;
    
    public TabellaTreni(InterfacciaTrafficoFerroviario itf){
        contenitore = new VBox();
        
        categoriaCol = new TableColumn("Cat.");
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        numTrenoCol = new TableColumn("#");
        numTrenoCol.setCellValueFactory(new PropertyValueFactory<>("numTreno"));
        origineCol = new TableColumn("Origine");
        origineCol.setCellValueFactory(new PropertyValueFactory<>("origine"));
        destinazioneCol = new TableColumn("Destinazione");
        destinazioneCol.setCellValueFactory(new PropertyValueFactory<>("destinazione"));
        oraPrevistaCol = new TableColumn("OraPrevista");
        oraPrevistaCol.setCellValueFactory(new PropertyValueFactory<>("oraPrevista"));
        oraTransitoCol = new TableColumn("OraTransito");
        oraTransitoCol.setCellValueFactory(new PropertyValueFactory<>("oraTransito"));
        noteCol = new TableColumn("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        
        treni = FXCollections.observableArrayList();
        java.sql.Date d = new Date(System.currentTimeMillis());
        RailwayController.visualizzaSchedule(this, d);
        
        
        tabella = new TableView<>(treni);
        
        
        tabella.getSelectionModel().selectedItemProperty().addListener(
            (property, vecchioTreno, nuovoTreno) -> {
                trenoSelezionato = nuovoTreno;
            }
        );
                
                
        
        tabella.getColumns().addAll(categoriaCol, numTrenoCol, origineCol, destinazioneCol,
        oraPrevistaCol, oraTransitoCol, noteCol);
        contenitore.getChildren().addAll(tabella);
        
    }
    
    public void impostaStile(ParametriConfigurazioneXML config)
    {
        tabella.getColumns().forEach(column -> column.setMinWidth(120));
        tabella.setFixedCellSize(25);
        tabella.prefHeightProperty().set((5.5 * 25));
        tabella.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-font-family: '" + config.getFontTabella() + "'; -fx-font-size: " + config.getDimensioneFontTabella() + "px;");     
    }
    public TableView<Treno> getTabella(){ return tabella;}
    public ObservableList<Treno> getTreni() {return treni;}
        
};
