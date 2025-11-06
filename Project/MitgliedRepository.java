import java.io.*;
import java.util.*;

public class MitgliedRepository {
    private static MitgliedRepository instance;
    private final Map<String, Mitglied> store = new LinkedHashMap<>();
    private final String path = "data/mitglieder.dat";
    private MitgliedRepository() {}
    public static synchronized MitgliedRepository getInstance() {
        if (instance == null) instance = new MitgliedRepository();
        return instance;
    }
    public synchronized void save(Mitglied m) { store.put(m.getId(), m); }
    public synchronized Mitglied findById(String id) { return store.get(id); }
    public synchronized Collection<Mitglied> findAll() { return store.values(); }

    @SuppressWarnings("unchecked")
    public synchronized void load() {
        try {
            File f = new File(path);
            if (!f.exists()) return;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Object o = ois.readObject();
                if (o instanceof Map) {
                    store.putAll((Map<String,Mitglied>) o);
                }
            }
        } catch (Exception e) {
            System.out.println("Warnung: Mitglieder konnten nicht geladen werden: " + e.getMessage());
        }
    }

    public synchronized void save() {
        try {
            File d = new File("data");
            if (!d.exists()) d.mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
                oos.writeObject(store);
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Speichern der Mitglieder: " + e.getMessage());
        }
    }
}
