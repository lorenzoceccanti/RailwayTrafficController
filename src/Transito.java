import java.time.*;
import java.util.stream.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class Transito {
    private final HBox gruppoCombo;
    private final Button tastoTransito;
    private final ComboBox oraTransito;
    private final ComboBox minutiTransito;
    private final EventHandler<ActionEvent> transitoTrenoHandler;
    
    public Transito(Treno t, InterfacciaTrafficoFerroviario itf)
    {
            gruppoCombo = new HBox();
            tastoTransito = new Button("TRANSITO");
            
            oraTransito = new ComboBox(); 
            oraTransito.getItems().setAll(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList()));
            boolean boxCategoriaInsVuoto = oraTransito.getSelectionModel().isEmpty();
            if(boxCategoriaInsVuoto == true)
                oraTransito.setValue("OraTransito");
            
            minutiTransito = new ComboBox(); 
            minutiTransito.getItems().setAll(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList()));
            boxCategoriaInsVuoto = minutiTransito.getSelectionModel().isEmpty();
            if(boxCategoriaInsVuoto == true)
                minutiTransito.setValue("MinutiTransito");
            
            gruppoCombo.getChildren().addAll(oraTransito, minutiTransito, tastoTransito);
            
            
            transitoTrenoHandler = (ActionEvent ae) -> {
            LocalTime oraTransito = LocalTime.of(this.getOraTransito(), this.getMinutiTransito());
            String time = oraTransito.toString();
            System.out.println(time);
            System.out.println(itf.getTabellaTreni().trenoSelezionato.getNumTreno());
            if(itf.getTabellaTreni().trenoSelezionato != null)
                RailwayController.transitoTreno(itf.getTabellaTreni().trenoSelezionato, itf, time);
        };
        
        
        itf.getTabellaTreni().getTabella().getSelectionModel().selectedItemProperty().addListener(
                (property, vecchioTreno, nuovoTreno) -> {
                    itf.getTabellaTreni().trenoSelezionato = nuovoTreno;
                    this.getTastoTransito().setOnAction(transitoTrenoHandler);
                }
        );
            

    }
    public void impostaStile(ParametriConfigurazioneXML config)
    {
        tastoTransito.setFont(Font.font(config.getFontTabella(), config.getDimensioneFontTabella()));
        tastoTransito.setMinSize(85, 30);
        tastoTransito.setStyle("-fx-background-color: rgb(209,232,208); -fx-border-color: rgb(209,232,208); -fx-border-radius: 12px;");
       
    }
    public HBox getGruppoCombo(){return gruppoCombo;}
    public ComboBox getCampoOra(){return oraTransito;}
    public ComboBox getCampoMinuti(){return minutiTransito;}
    public Button getTastoTransito() {return tastoTransito;}
    public int getOraTransito() {return Integer.parseInt(oraTransito.getValue().toString());}
    public int getMinutiTransito() {return Integer.parseInt(minutiTransito.getValue().toString());}
    
}
