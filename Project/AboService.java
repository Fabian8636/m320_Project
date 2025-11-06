import java.util.HashMap;
import java.util.Map;

public class AboService {
    private final Map<String, Abo> aboMap = new HashMap<>();

    public void assignAbo(Mitglied m, Abo a) {
        aboMap.put(m.getId(), a);
    }

    public Abo getAbo(Mitglied m) {
        return aboMap.get(m.getId());
    }
}
