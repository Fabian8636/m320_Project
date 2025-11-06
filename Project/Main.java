public class Main {
    public static void main(String[] args) {
        // load data
        MitgliedRepository.getInstance().load();
        KursRepository.getInstance().load();

        new ConsoleUI().run();

        // save on exit
        MitgliedRepository.getInstance().save();
        KursRepository.getInstance().save();
        System.out.println("Programm beendet. Daten gespeichert.");
    }
}
