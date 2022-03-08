package controllers.professorControllers;

import controllers.Utilities;
import models.users.Professor;
import org.hibernate.SessionFactory;
import services.ProfessorService;

import java.util.Scanner;

public class ChangePasswordController {
    private final Scanner sc = new Scanner(System.in);
    private final Professor professor;
    private final ProfessorService professorService;

    public ChangePasswordController(SessionFactory sessionFactory,Integer professorId) {
        professorService = new ProfessorService(sessionFactory);
        professor = professorService.find(professorId);
    }

    public void changePassword() {
        Utilities.printGreen("Old Password: ");
        String oldPass = sc.nextLine();
        Utilities.printGreen("New Password: ");
        String newPass = sc.nextLine();
        if (professor.getPassword().equals(oldPass)) {
            professor.setPassword(newPass);
            professorService.editProfile(professor);
        } else Utilities.printGreen("Old Password was Wrong.");
    }
}
