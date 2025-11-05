import java.io.Serializable;
import java.util.UUID;

public class Mitglied implements Serializable {
    private final String id;
    private final String vorname;
    private final String nachname;
    private Abo abo;

    public Mitglied(String vorname, String nachname) {
        this.id = UUID.randomUUID().toString();
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public String getId() { return id; }
    public String getVorname() { return vorname; }
    public String getNachname() { return nachname; }
    public Abo getAbo() { return abo; }
    public void setAbo(Abo abo) { this.abo = abo; }

    @Override
    public String toString() {
        return id + " | " + vorname + " " + nachname + " - " + (abo==null? "kein Abo" : abo.getSummary());
    }
}
