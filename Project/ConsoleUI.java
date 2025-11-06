import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final MitgliedRepository mitRepo = MitgliedRepository.getInstance();
    private final KursRepository kursRepo = KursRepository.getInstance();
    private final AboService aboService = new AboService();
    private final KursService kursService = new KursService();

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("=================================================================================");
            System.out.println("                   WELCOME TO BLEICHEFIT FITNESS STUDIO");
            System.out.println("=================================================================================");
            System.out.println();
            System.out.println("  ####### H A U P T M E N Ü #######");
            System.out.println();
            System.out.println(" 1) Mitglied anlegen");
            System.out.println(" 2) Abo abschließen");
            System.out.println(" 3) Kurs erstellen");
            System.out.println(" 4) Kurs buchen");
            System.out.println(" 5) Mitglieder anzeigen");
            System.out.println(" 6) Kurse anzeigen");
            System.out.println(" 0) Beenden");
            System.out.println();
            System.out.print("> ");

            String c = scanner.nextLine().trim();
            System.out.println("---------------------------------------------------------------------------------");
            try {
                switch (c) {
                    case "1": createMember(); break;
                    case "2": createAbo(); break;
                    case "3": createKurs(); break;
                    case "4": bookKurs(); break;
                    case "5": listMembers(); break;
                    case "6": listKurse(); break;
                    case "0": running = false; System.out.println("Danke fürs Benutzen von BleicheFit!"); break;
                    default: System.out.println("Ungültige Eingabe, bitte erneut versuchen."); break;
                }
            } catch (Exception e) {
                System.out.println("Fehler aufgetreten: " + e.getMessage());
            }
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println();
        }
    }

    private void createMember() {
        System.out.println("=================================================================================");
        System.out.println("                          N E U E S   M I T G L I E D   A N L E G E N");
        System.out.println("=================================================================================");
        System.out.print("Vorname: ");
        String v = scanner.nextLine().trim();
        System.out.print("Nachname: ");
        String n = scanner.nextLine().trim();
        Mitglied m = new Mitglied(v, n);
        mitRepo.save(m);
        System.out.println();
        System.out.println(">>> Mitglied erfolgreich erstellt! <<<");
        System.out.println("ID: " + m.getId());
        System.out.println("Vorname: " + v);
        System.out.println("Nachname: " + n);
        System.out.println("=================================================================================");
        System.out.println("                      Aktuelle Mitgliederübersicht");
        System.out.println("=================================================================================");
        for (Mitglied mm : mitRepo.findAll()) {
            System.out.println("| ID: " + mm.getId() + " | Name: " + mm.getVorname() + " " + mm.getNachname() + " |");
            System.out.println("---------------------------------------------------------------------------------");
        }
        System.out.println("=================================================================================");
        System.out.println();
    }

    private void createAbo() {
        System.out.println("=================================================================================");
        System.out.println("                          A B O   Z U W E I S E N");
        System.out.println("=================================================================================");
        System.out.print("Mitglied-ID: ");
        String id = scanner.nextLine().trim();
        Mitglied m = mitRepo.findById(id);
        if (m == null) {
            System.out.println("Mitglied nicht gefunden!");
            return;
        }
        System.out.print("Abo-Typ (MONAT/JAHRES/TAG): ");
        String t = scanner.nextLine().trim();
        Abo a = AboFactory.createAbo(t);
        aboService.assignAbo(m, a);
        System.out.println("=================================================================================");
        System.out.println(">>> Abo erfolgreich gesetzt! <<<");
        System.out.println("Mitglied: " + m.getVorname() + " " + m.getNachname());
        System.out.println("Abo-Typ: " + a.getSummary());
        System.out.println("=================================================================================");
        System.out.println("                      Alle Mitglieder mit Abos");
        System.out.println("=================================================================================");
        for (Mitglied mm : mitRepo.findAll()) {
            Abo abo = aboService.getAbo(mm);
            String aboInfo = (abo != null) ? abo.getSummary() : "Kein Abo";
            System.out.println("| ID: " + mm.getId() + " | Name: " + mm.getVorname() + " " + mm.getNachname() + " | Abo: " + aboInfo + " |");
            System.out.println("---------------------------------------------------------------------------------");
        }
        System.out.println();
    }

    private void createKurs() {
        System.out.println("=================================================================================");
        System.out.println("                          N E U E R   K U R S");
        System.out.println("=================================================================================");
        System.out.print("Kursname: ");
        String name = scanner.nextLine().trim();
        System.out.print("Kapazität: ");
        int k = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Trainername: ");
        String trainer = scanner.nextLine().trim();
        Kurs kurs = new Kurs.FitnessKurs(name, k, trainer);
        kursRepo.save(kurs);
        System.out.println("=================================================================================");
        System.out.println(">>> Kurs erfolgreich erstellt! <<<");
        System.out.println("ID: " + kurs.getId());
        System.out.println("Name: " + name);
        System.out.println("Trainer: " + trainer);
        System.out.println("Max. Teilnehmer: " + k);
        System.out.println("=================================================================================");
        System.out.println("                          Alle Kurse");
        System.out.println("=================================================================================");
        for (Kurs kk : kursRepo.findAll()) {
            System.out.println("| ID: " + kk.getId() + " | Name: " + kk.getName() + " |");
            System.out.println("---------------------------------------------------------------------------------");
        }
        System.out.println();
    }

    private void bookKurs() {
        System.out.println("=================================================================================");
        System.out.println("                          K U R S   B U C H E N");
        System.out.println("=================================================================================");
        System.out.print("Mitglied-ID: ");
        String mid = scanner.nextLine().trim();
        Mitglied m = mitRepo.findById(mid);
        if (m == null) {
            System.out.println("Mitglied nicht gefunden!");
            return;
        }
        System.out.print("Kurs-ID: ");
        String kid = scanner.nextLine().trim();
        Kurs kurs = kursRepo.findById(kid);
        if (kurs == null) {
            System.out.println("Kurs nicht gefunden!");
            return;
        }
        try {
            kursService.bucheKurs(kid, mid);
            System.out.println("=================================================================================");
            System.out.println(">>> Buchung erfolgreich! <<<");
            System.out.println("Mitglied: " + m.getVorname() + " " + m.getNachname());
            System.out.println("Kurs: " + kurs.getName());
            System.out.println("=================================================================================");
            System.out.println("                          Alle Buchungen (Dummy)");
            System.out.println("=================================================================================");
            System.out.println("Mitglieder, die in diesem Kurs angemeldet sind:");
            System.out.println("| " + m.getVorname() + " " + m.getNachname() + " | Kurs: " + kurs.getName() + " |");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println();
        } catch (KursVollException e) {
            System.out.println("Kurs voll: " + e.getMessage());
        }
    }

    private void listMembers() {
        System.out.println("=================================================================================");
        System.out.println("                          A L L E   M I T G L I E D E R");
        System.out.println("=================================================================================");
        for (Mitglied m : mitRepo.findAll()) {
            Abo a = aboService.getAbo(m);
            String aboInfo = (a != null) ? a.getSummary() : "Kein Abo";
            System.out.println("| ID: " + m.getId() + " | Name: " + m.getVorname() + " " + m.getNachname() + " | Abo: " + aboInfo + " |");
            System.out.println("---------------------------------------------------------------------------------");
        }
        System.out.println();
    }

    private void listKurse() {
        System.out.println("=================================================================================");
        System.out.println("                          A L L E   K U R S E");
        System.out.println("=================================================================================");
        for (Kurs k : kursRepo.findAll()) {
            System.out.println("| ID: " + k.getId() + " | Name: " + k.getName() + " |");
            System.out.println("---------------------------------------------------------------------------------");
        }
        System.out.println();
    }
}
