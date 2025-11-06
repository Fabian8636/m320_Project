
import java.io.*;
import java.util.*;

public class KursRepository {
    private static KursRepository instance;
    private final Map<String, Kurs> store = new LinkedHashMap<>();
    private final String path = "data/kurse.dat";
    private KursRepository() {}
    public static synchronized KursRepository getInstance() {
        if (instance == null) instance = new KursRepository();
        return instance;
    }
    public synchronized void save(Kurs k) { store.put(k.getId(), k); }
    public synchronized Kurs findById(String id) { return store.get(id); }
    public synchronized Collection<Kurs> findAll() { return store.values(); }

    @SuppressWarnings("unchecked")
    public synchronized void load() {
        try {
            File f = new File(path);
            if (!f.exists()) return;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Object o = ois.readObject();
                if (o instanceof Map) {
                    store.putAll((Map<String,Kurs>) o);
                }
            }
        } catch (Exception e) {
            System.out.println("Warnung: Kurse konnten nicht geladen werden: " + e.getMessage());
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
            System.out.println("Fehler beim Speichern der Kurse: " + e.getMessage());
        }
    }
}
