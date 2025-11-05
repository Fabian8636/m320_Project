public class AboFactory {
    public static Abo createAbo(String typ) {
        if (typ == null) throw new IllegalArgumentException("Typ null");
        switch (typ.toUpperCase()) {
            case "MONAT": return new Abo.MonatsAbo(1, 39.99);
            case "JAHRES": return new Abo.JahresAbo(12, 399.0);
            case "TAG": return new Abo.Tagespass();
            default: throw new IllegalArgumentException("Unbekannter Abo-Typ: " + typ);
        }
    }
}
