import com.sun.prism.paint.Color;
import java.sql.Date;
import java.time.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class Storico {
    private final HBox boxSceltaData;
    private final DatePicker dataRiferimento;
    private final Button tastoMostra;
    
    public Storico(InterfacciaTrafficoFerroviario itf)
    {
        boxSceltaData = new HBox();
        dataRiferimento = new DatePicker();
        dataRiferimento.setValue(LocalDate.now());
        tastoMostra = new Button("MOSTRA");
        
        
        
        // Evento riga di log per scelta data
        dataRiferimento.setOnAction((ActionEvent ev)->{ RailwayController.inviaLog("Scelta DataRif");});
        
        tastoMostra.setOnAction(
                (ActionEvent ev) -> {
            RailwayController.visualizzaSchedule(itf.getTabellaTreni(), Date.valueOf(this.getDataRiferimento()));
            itf.getTendenzaRitardi().aggiorna(this.getDataRiferimento());
        }
        );
        
        boxSceltaData.getChildren().addAll(dataRiferimento, tastoMostra);
    }
    
    public void impostaStile(ParametriConfigurazioneXML config)
    {
        // Mettere in sezione apposita per stile
         boxSceltaData.setSpacing(57);
         dataRiferimento.setMinHeight(30);
         dataRiferimento.setStyle("-fx-border-color: blue;");
         
        
        tastoMostra.setMinSize(80, 30);
        tastoMostra.setFont(Font.font(config.getFontTabella(), config.getDimensioneFontTabella()));
        tastoMostra.setStyle("-fx-background-color: rgb(249,211,171); -fx-border-color: rgb(249,211,171); -fx-border-radius: 12px;");
    }
    public LocalDate getDataRiferimento(){return dataRiferimento.getValue();}
    public HBox getBoxSceltaData(){return boxSceltaData;}
    public DatePicker getPickerData(){return dataRiferimento;}
    
}
