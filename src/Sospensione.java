import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class Sospensione {
    private final HBox boxSospensione;
    private final Button tastoSospendi;
    private final TextField note;
    
    public Sospensione(InterfacciaTrafficoFerroviario itf)
    {
        boxSospensione = new HBox();
        tastoSospendi = new Button("SOSPENDI");
        note = new TextField("Note");
        
        tastoSospendi.setOnAction(
                (ActionEvent ev) -> {
            RailwayController.sospendiTreno(itf.getTabellaTreni().trenoSelezionato, itf, note.getText());
        }
        );
        
        boxSospensione.getChildren().addAll(note);
        
    }
    public void impostaStile(ParametriConfigurazioneXML config){
        tastoSospendi.setFont(Font.font(config.getFontTabella(), config.getDimensioneFontTabella()));
        tastoSospendi.setMinSize(85, 30);
        note.setMinSize(40, 32);
        tastoSospendi.setStyle("-fx-background-color: rgb(248,201,200); -fx-border-color: rgb(248,201,200); -fx-border-radius: 12px;");
    }
    public HBox getBoxSospensione(){return boxSospensione;}
    public Button getTastoSospendi() {return tastoSospendi;}
    public String getNoteScritte() {return note.getText();}
    public TextField getCampoTesto(){return note;}
}
