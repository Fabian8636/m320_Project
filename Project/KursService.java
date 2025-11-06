public class KursService {
    private final KursRepository kursRepo = KursRepository.getInstance();
    private final MitgliedRepository mitRepo = MitgliedRepository.getInstance();

    public void bucheKurs(String kursId, String mitgliedId) throws KursVollException {
        Kurs k = kursRepo.findById(kursId);
        if (k == null) throw new IllegalArgumentException("Kurs nicht gefunden");
        Mitglied m = mitRepo.findById(mitgliedId);
        if (m == null) throw new IllegalArgumentException("Mitglied nicht gefunden");
        k.buche(m);
        kursRepo.save(k);
    }
}
