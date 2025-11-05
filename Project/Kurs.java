import java.io.Serializable;
import java.util.*;

public interface Kurs extends Serializable {
    String getId();
    String getName();
    int getKapazitaet();
    int getTeilnehmerAnzahl();
    boolean istVoll();
    void buche(Mitglied m) throws KursVollException;
    List<String> getTeilnehmerIds();
    String toString();

    // Minimal implementations as static nested classes are placed here for file count
    class FitnessKurs implements Kurs {
        private final String id = UUID.randomUUID().toString();
        private final String name;
        private final int kap;
        private final String trainer;
        private final List<String> teilnehmer = new ArrayList<>();

        public FitnessKurs(String name, int kapazitaet, String trainer) {
            this.name = name;
            this.kap = kapazitaet;
            this.trainer = trainer;
        }
        public String getId() { return id; }
        public String getName() { return name; }
        public int getKapazitaet() { return kap; }
        public int getTeilnehmerAnzahl() { return teilnehmer.size(); }
        public List<String> getTeilnehmerIds() { return teilnehmer; }
        public boolean istVoll() { return teilnehmer.size() >= kap; }
        public synchronized void buche(Mitglied m) throws KursVollException {
            if (istVoll()) throw new KursVollException("Kurs " + name + " voll");
            if (!teilnehmer.contains(m.getId())) teilnehmer.add(m.getId());
        }
        public String toString() {
            return id + " | " + name + " (" + getTeilnehmerAnzahl() + "/" + kap + ") Trainer: " + trainer;
        }
    }

    class YogaKurs extends FitnessKurs {
        public YogaKurs(String name, int kapazitaet, String trainer) {
            super(name, kapazitaet, trainer);
        }
        @Override
        public String toString() {
            return super.toString() + " [Yoga]";
        }
    }
}
