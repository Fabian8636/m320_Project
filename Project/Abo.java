import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Abo implements Serializable {
    protected final String id = UUID.randomUUID().toString();
    protected final LocalDate start = LocalDate.now();
    protected LocalDate ende;
    protected double preis;

    public String getId() { return id; }
    public LocalDate getStart() { return start; }
    public LocalDate getEnde() { return ende; }
    public double getPreis() { return preis; }
    public boolean isActive() { return ende == null || ende.isAfter(LocalDate.now()); }
    public void kuendigen() { this.ende = LocalDate.now(); }
    public abstract String getSummary();

    // Subclasses as static nested classes to keep file count low
    public static class MonatsAbo extends Abo {
        private final int monate;
        public MonatsAbo(int monate, double preis) {
            this.monate = monate;
            this.preis = preis;
            this.ende = start.plusMonths(monate);
        }
        @Override public String getSummary() {
            return "MonatsAbo(" + monate + "M) bis " + ende + " CHF " + preis;
        }
    }

    public static class JahresAbo extends Abo {
        private final int monate;
        public JahresAbo(int monate, double preis) {
            this.monate = monate;
            this.preis = preis;
            this.ende = start.plusMonths(monate);
        }
        @Override public String getSummary() {
            return "JahresAbo(" + monate + "M) bis " + ende + " CHF " + preis;
        }
    }

    public static class Tagespass extends Abo {
        public Tagespass() {
            this.preis = 9.99;
            this.ende = start;
        }
        @Override public String getSummary() {
            return "Tagespass für " + start + " CHF " + preis;
        }
    }
}
