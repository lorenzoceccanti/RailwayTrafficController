import java.sql.*;
import java.time.LocalDate;

public class Treno {
    private final String categoria, numTreno, origine, destinazione;
    private final Time oraPrevista;
    private final String oraTransito, note;
    private final Date dataRif;
    
    public Treno(String categoria, String numTreno, String origine,
    String destinazione, Time oraPrevista, String oraTransito, String note,
    Date dataRif)
    {
        this.categoria = categoria;
        this.numTreno = numTreno;
        this.origine = origine;
        this.destinazione = destinazione;
        this.oraPrevista = oraPrevista;
        this.oraTransito = oraTransito;
        this.note = note;
        this.dataRif = dataRif;
    }
    public Treno(String categoria, String numTreno, String origine,
    String destinazione, Time oraPrevista)
    {
        this(categoria, numTreno, origine, destinazione, oraPrevista,
        null, null, Date.valueOf(LocalDate.now()));
    }
    public String getCategoria(){return categoria;}
    public String getNumTreno() {return numTreno;}
    public String getOrigine() {return origine;}
    public String getDestinazione() {return destinazione;}
    public Time getOraPrevista() {return oraPrevista;}
    public String getOraTransito () {return oraTransito;}
    public String getNote() {return note;}
    public Date getDataRif() {return dataRif;}
}
