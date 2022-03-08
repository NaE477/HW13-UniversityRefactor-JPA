package controllers.clerkControllers;

import controllers.Utilities;
import org.hibernate.SessionFactory;
import services.TermService;

import javax.persistence.EntityManagerFactory;
import java.util.Locale;
import java.util.Scanner;

public class EndTermController {
    private final Scanner sc = new Scanner(System.in);
    private final TermService termService;

    public EndTermController(EntityManagerFactory entityManagerFactory) {
        termService = new TermService(entityManagerFactory);
    }

    public void endTerm() {
        Utilities.printGreen("Sure?(Y/N)");
        String yOrN = sc.nextLine().toUpperCase(Locale.ROOT);
        if (yOrN.equals("Y")) {
            termService.endTerm();
            Utilities.printGreen("Term Ended successfully.");
        } else {
            Utilities.printGreen("Cancelled.");
        }
    }
}
