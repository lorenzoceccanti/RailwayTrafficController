import java.sql.*;
import java.time.LocalDate;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.stage.*;

public class RailwayController extends Application {
    private final static String FILE_CACHE = "cache.bin",
                                FILE_CONFIGURAZIONE = "config.xml",
                                SCHEMA_CONFIGURAZIONE = "config.xsd";
    private InterfacciaTrafficoFerroviario interfaccia;
    
   public void start(Stage stage){
       ParametriConfigurazioneXML config =  new ParametriConfigurazioneXML(
                ValidatoreXML.valida(GestoreFile.carica(FILE_CONFIGURAZIONE), SCHEMA_CONFIGURAZIONE) ? // 06)
                    GestoreFile.carica(FILE_CONFIGURAZIONE) : null // 07)
            );
       
       DBTreni.impostaValoriDB(config);
       GestoreLogXML.impostaValoriLog(config);
       
       GestoreLogXML.creaLog("RailwayController", "Avvio");
       
       interfaccia = new InterfacciaTrafficoFerroviario(FILE_CACHE, config);
       // interfaccia.impostaStile
       interfaccia.impostaStile(config);
       
       // gestore eventi per la chiusura dello stage
        stage.setOnCloseRequest((WindowEvent we) -> { 
            GestoreCache.salva(interfaccia, FILE_CACHE); 
            GestoreLogXML.creaLog("RailwayController","Termine"); 
        });
       
       Group posizionamentoUltimaRiga = interfaccia.getContenitoreUltimaRiga();
       posizionamentoUltimaRiga.setLayoutY(176.5);
       
       stage.setTitle("RailwayController");
       Scene scene = new Scene(new Group(interfaccia.getContenitore(), posizionamentoUltimaRiga), 850, 750);
       stage.setScene(scene);
       stage.show();
       
   }
   
   public static void inviaLog(String nomeEvento){
       GestoreLogXML.creaLog("RailwayController", nomeEvento);
   }
   
   public static void schedulaTreno(Treno t, InterfacciaTrafficoFerroviario itf)
   {
       GestoreLogXML.creaLog("RailwayController", "Aggiungi");
       DBTreni.schedulaTreno(t, itf);
   }
   
   public static void transitoTreno(Treno t, InterfacciaTrafficoFerroviario itf,
           String oraTransito)
   {
       GestoreLogXML.creaLog("RailwayController", "Transito");
       DBTreni.transitoTreno(t, itf, oraTransito);
   }
   
   public static void sospendiTreno(Treno t, InterfacciaTrafficoFerroviario itf, String note)
   {
       GestoreLogXML.creaLog("RailwayController", "Sospendi");
       DBTreni.sospendiTreno(t, itf, note);
   }
   
   public static void annullaSchedulazioneTreno(Treno t, InterfacciaTrafficoFerroviario itf)
   {
       GestoreLogXML.creaLog("RailwayController", "Annulla");
       DBTreni.annullaSchedulazioneTreno(t, itf);
   }
   
   public static void visualizzaSchedule(TabellaTreni tt, Date dataRif)
   {
       GestoreLogXML.creaLog("RailwayController", "Mostra");
       DBTreni.visualizzaSchedule(tt, dataRif);
   }
   
   public static void ottieniTendenza(ObservableList<XYChart.Data<String,Number>> obReg,
    ObservableList<XYChart.Data<String,Number>> obRV, ObservableList<XYChart.Data<String,Number>> obIC,
    ObservableList<XYChart.Data<String,Number>> obAV, LocalDate d)
   {
       DBTreni.ottieniTendenzaPerCat(obReg, obRV, obIC, obAV, Date.valueOf(d));
       
   }
       
}
