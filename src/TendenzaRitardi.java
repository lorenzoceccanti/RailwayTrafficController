import java.time.LocalDate;
import javafx.scene.chart.*;
import javafx.scene.layout.*;

public class TendenzaRitardi {
    private final VBox contenitore;
    private final BarChart<String, Number> grafico;
    private final XYChart.Series serieRegionali;
    private final XYChart.Series serieRV;
    private final XYChart.Series serieIntercity;
    private final XYChart.Series serieAltaVelocita;
    
    public TendenzaRitardi()
    {
        contenitore = new VBox();
        serieRegionali = new XYChart.Series();
        serieRV = new XYChart.Series();
        serieIntercity = new XYChart.Series();
        serieAltaVelocita = new XYChart.Series();
        grafico = new BarChart<>(new CategoryAxis(), new NumberAxis());
        grafico.setTitle("Minuti medi di ritardo per fascia oraria");
        
        serieRegionali.setName("REG");
        serieRV.setName("RV");
        serieIntercity.setName("IC");
        serieAltaVelocita.setName("AV");
        
        grafico.getData().addAll(serieAltaVelocita, serieIntercity, serieRegionali, serieRV);
        grafico.setAnimated(false);
        contenitore.getChildren().addAll(grafico);
        
    }
    
    public void aggiorna(LocalDate d){
        
        RailwayController.ottieniTendenza(serieRegionali.getData(), serieRV.getData(), serieIntercity.getData(), serieAltaVelocita.getData(), d);
    }
    
    
    
    public VBox getContenitore() {return contenitore;}
}
