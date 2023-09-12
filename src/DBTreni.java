import java.sql.*;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class DBTreni {
    private static String IP;
    private static String USERNAME;
    private static String PASSWORD;
    private static int PORTA;
    
    public static void impostaValoriDB(ParametriConfigurazioneXML config)
    {
        IP = config.getIpDB();
        USERNAME = config.getUsernameDB();
        PASSWORD = config.getPasswordDB();
        PORTA = config.getPortaDB();
    }
    
    private static void calcolaRitardo(String NumTreno)
    {
        try(
        Connection co = DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORTA+"/archiviotreni",USERNAME, PASSWORD);
        PreparedStatement ps = co.prepareStatement("UPDATE transiti SET Note = (SELECT D.* FROM (SELECT IF(SUBTIME(OraTransito, OraPrevista) < 0, CONCAT('ANT ', HOUR(SUBTIME(OraTransito, OraPrevista))*60 + MINUTE(SUBTIME(OraTransito, OraPrevista))), CONCAT('RIT ', HOUR(SUBTIME(OraTransito, OraPrevista))*60 + MINUTE(SUBTIME(OraTransito, OraPrevista))) )  AS Result FROM transiti WHERE NumTreno = ?) AS D) WHERE NumTreno = ?;");
        ){ 
            ps.setString(1, NumTreno);
            ps.setString(2, NumTreno);
            System.out.println("rows affected: " + ps.executeUpdate());
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    
    public static void schedulaTreno(Treno t, InterfacciaTrafficoFerroviario itf)
    {
        try(
        Connection co = DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORTA+"/archiviotreni",USERNAME, PASSWORD);
        PreparedStatement ps = co.prepareStatement("INSERT INTO transiti(Categoria, NumTreno, Origine, Destinazione, OraPrevista, DataRif) VALUES (?,?,?,?,?, current_date())");
        ){
            ps.setString(1, t.getCategoria());
            ps.setString(2, t.getNumTreno());
            ps.setString(3, t.getOrigine());
            ps.setString(4, t.getDestinazione());
            ps.setTime(5, t.getOraPrevista());
            System.out.println("rows affected: " + ps.executeUpdate());
            DBTreni.visualizzaSchedule(itf.getTabellaTreni(), new Date(System.currentTimeMillis()));
            
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public static void transitoTreno(Treno t, InterfacciaTrafficoFerroviario itf, String oraTransito)
    {
        try(
        Connection co = DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORTA+"/archiviotreni",USERNAME, PASSWORD);
        PreparedStatement ps = co.prepareStatement("UPDATE transiti SET OraTransito = ? WHERE NumTreno = ? AND OraTransito IS NULL;");
        )
        {
            ps.setString(2, t.getNumTreno());
            ps.setString(1, (oraTransito + ":00"));
            System.out.println("rows affected: " + ps.executeUpdate());
            DBTreni.calcolaRitardo(t.getNumTreno());
            DBTreni.visualizzaSchedule(itf.getTabellaTreni(), new Date(System.currentTimeMillis()));
       
            LocalDate ld = new java.sql.Date(t.getDataRif().getTime()).toLocalDate();
            itf.getTendenzaRitardi().aggiorna(ld);
            
        }
         catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public static void sospendiTreno(Treno t, InterfacciaTrafficoFerroviario itf, String note)
    {
        try(
        Connection co = DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORTA+"/archiviotreni",USERNAME, PASSWORD);
        PreparedStatement ps = co.prepareStatement("UPDATE transiti SET OraTransito = 'CAN', Note = ? WHERE NumTreno = ? AND OraTransito IS NULL;");
        )
        {
            ps.setString(1, note);
            ps.setString(2, t.getNumTreno());
            System.out.println("rows affected: " + ps.executeUpdate());
            DBTreni.visualizzaSchedule(itf.getTabellaTreni(), new Date(System.currentTimeMillis()));
        }
         catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public static void annullaSchedulazioneTreno(Treno t, InterfacciaTrafficoFerroviario itf)
    {
        try(
        Connection co = DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORTA+"/archiviotreni",USERNAME, PASSWORD);
        PreparedStatement ps = co.prepareStatement("DELETE FROM transiti WHERE NumTreno = ?");
        )
        {
            ps.setString(1, t.getNumTreno());
            System.out.println("rows affected: " + ps.executeUpdate());
            itf.getTabellaTreni().getTreni().remove(t);
        }
         catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public static void visualizzaSchedule(TabellaTreni tt, Date dataRif)
    {
        try(
        Connection co = DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORTA+"/archiviotreni?user="+USERNAME+"&password="+PASSWORD);
        PreparedStatement ps = co.prepareStatement("SELECT Categoria, NumTreno, Origine, Destinazione, OraPrevista, OraTransito, Note FROM transiti WHERE DataRif = ? ORDER BY OraPrevista;");
        )
        {
            ps.setDate(1, dataRif);
            ResultSet rs = ps.executeQuery();
            tt.getTreni().clear();
            while(rs.next())
                tt.getTreni().add(new Treno(rs.getString("Categoria"), rs.getString("NumTreno"), rs.getString("Origine"),
                        rs.getString("Destinazione"), rs.getTime("OraPrevista"), rs.getString("OraTransito"),
                        rs.getString("Note"), dataRif));
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public static void ottieniTendenzaPerCat(ObservableList<XYChart.Data<String,Number>> obReg,
    ObservableList<XYChart.Data<String,Number>> obRV, ObservableList<XYChart.Data<String,Number>> obIC,
    ObservableList<XYChart.Data<String,Number>> obAV, Date d)
    {
        try(
        Connection co = DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORTA+"/archiviotreni",USERNAME, PASSWORD);
        PreparedStatement ps = co.prepareStatement("SELECT F.Categoria, F.FasciaOraria, F.MinutiMediRitardoCategoria FROM( SELECT D3.Categoria, D3.FasciaOraria, AVG(D3.MinutiRitardo) AS MinutiMediRitardoCategoria FROM ( SELECT D2.*, IF(TIMESTAMPDIFF(MINUTE, D2.OraPrevista, D2.OraTransitoConv)>0, TIMESTAMPDIFF(MINUTE, D2.OraPrevista, D2.OraTransitoConv), 0)  AS MinutiRitardo FROM ( select  T.*, D.FasciaOraria, IF(OraTransito IS NOT NULL, IF(OraTransito <> 'CAN', CONVERT(T.OraTransito, TIME), NULL), NULL) AS OraTransitoConv FROM transiti T LEFT OUTER JOIN ( SELECT '07:00:00' AS InizioFascia, '09:00:00' AS FineFascia, '07-09' AS FasciaOraria UNION SELECT '09:00:00' AS InizioFascia, '12:00:00' AS FineFascia,'09-12'AS FasciaOraria UNION SELECT '12:00:00' AS InizioFascia, '14:00:00' AS FineFascia,'12-14' AS FasciaOraria UNION SELECT '14:00:00' AS InizioFascia, '18:00:00' AS FineFascia,'14-18' AS FasciaOraria UNION SELECT '18:00:00' AS InizioFascia, '21:00:00' AS FineFascia,'18-21' AS FasciaOraria UNION SELECT '21:00:00' AS InizioFascia, '23:59:59' AS FineFascia,'21-7' AS FasciaOraria UNION SELECT '00:00:00' AS InizioFascia, '06:59:59' AS FineFascia,'21-7' AS FasciaOraria ) AS D ON 1 WHERE OraPrevista BETWEEN D.InizioFascia AND D.FineFascia AND DataRif = ?) AS D2 WHERE D2.OraTransitoConv IS NOT NULL ) AS D3 GROUP BY D3.Categoria, D3.FasciaOraria ) AS F ORDER BY F.FasciaOraria ASC, F.Categoria ASC;");
        )
        {
            ps.setDate(1, d);
            ResultSet rs = ps.executeQuery();
            obReg.clear();
            obRV.clear();
            obIC.clear();
            obAV.clear();
            while(rs.next())
            {
                if(rs.getString("Categoria").equals("AV"))
                    obAV.add(new XYChart.Data(rs.getString("FasciaOraria"), rs.getDouble("MinutiMediRitardoCategoria")));
                if(rs.getString("Categoria").equals("IC"))
                    obIC.add(new XYChart.Data(rs.getString("FasciaOraria"), rs.getDouble("MinutiMediRitardoCategoria")));
                if(rs.getString("Categoria").equals("REG"))
                    obReg.add(new XYChart.Data(rs.getString("FasciaOraria"), rs.getDouble("MinutiMediRitardoCategoria")));
                if(rs.getString("Categoria").equals("RV"))
                    obRV.add(new XYChart.Data(rs.getString("FasciaOraria"), rs.getDouble("MinutiMediRitardoCategoria")));
            }
            
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        
    }
}
