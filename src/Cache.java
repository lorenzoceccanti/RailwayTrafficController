import java.io.*;
import java.time.*;

public class Cache implements Serializable{
    private final String categoria, numTreno, origine, destinazione;
    private final LocalTime oraPrevista, oraTransito;
    private final LocalDate dataRif;
    private final String note;
    private final int trenoSelezionato;
    
    public Cache(InterfacciaTrafficoFerroviario itf)
    {
        categoria = itf.getUltimaRiga().getCategoriaInserita();
        numTreno = itf.getUltimaRiga().getNumTrenoInserito();
        origine = itf.getUltimaRiga().getOrigineInserita();
        destinazione = itf.getUltimaRiga().getDestinazioneInserita();
        oraPrevista = LocalTime.of(itf.getUltimaRiga().getOraPrevistaInserita(), itf.getUltimaRiga().getMinutiPrevistiInseriti());
        oraTransito = LocalTime.of(itf.getTransito().getOraTransito(), itf.getTransito().getMinutiTransito());
        dataRif = itf.getStorico().getDataRiferimento();
        note = itf.getSospensione().getNoteScritte();
        trenoSelezionato = itf.getTabellaTreni().getTabella().getSelectionModel().getSelectedIndex();
    }
    public String getCategoria(){return categoria;}
    public String getNumTreno(){return numTreno;}
    public String getOrigine(){return origine;}
    public String getDestinazione(){return destinazione;}
    public LocalTime getOraPrevista() {return oraPrevista;}
    public LocalTime getOraTransito() {return oraTransito;}
    public LocalDate getDataRif(){return dataRif;}
    public String getNote(){return note;}
    public int getTrenoSelezionato(){return trenoSelezionato;}
}
