package controllers;

import models.users.User;
import org.hibernate.SessionFactory;
import services.ClerkService;
import services.ProfessorService;
import services.StudentService;

import java.util.Scanner;

public class Utilities {
    static Scanner sc = new Scanner(System.in);
    private static final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    private static final ClerkService clerkService = new ClerkService(sessionFactory);
    private static final ProfessorService professorService = new ProfessorService(sessionFactory);
    private static final StudentService studentService = new StudentService(sessionFactory);

    public static String usernameReceiver() {
        while (true) {
            printGreen("Username: ");
            String username = sc.nextLine();
            if (usernameExists(username)) printGreen("This username Already Exists! Try another one: ");
            else return username;
        }
    }

    public static <T extends User>  String usernameEditor(T t) {
        while (true) {
            printGreen("Username: ");
            String username = sc.nextLine();
            if (t.getUsername().equals(username)) return username;
            else if (usernameExists(username)) printGreen("This username Already Exists! Try another one: ");
            else return username;
        }
    }

    private static Boolean usernameExists(String username) {
        return clerkService.find(username) != null
                || professorService.find(username) != null
                || studentService.find(username) != null;
    }

    public static Integer intReceiver() {
        while (true) {
            try {
                int output = Integer.parseInt(sc.nextLine());
                if (output > 0) return output;
                else printGreen("Enter a number bigger than 0");
            } catch (NumberFormatException e) {
                printGreen("Only numbers are allowed here.");
            }
        }
    }

    public static Double doubleReceiver() {
        while (true) {
            try {
                double output = Double.parseDouble(sc.nextLine());
                if (output > 0) return output;
                else printGreen("Enter a number bigger than 0");
            } catch (NumberFormatException e) {
                printGreen("Only numbers are allowed here.");
            }
        }
    }

    public static void printGreen(String input) {
        System.out.println("         " + "\u001b[32m |" + input + "\u001b[0m");
    }
}
