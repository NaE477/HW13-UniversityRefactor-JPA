package controllers.professorControllers;

import controllers.Utilities;
import models.users.Professor;
import org.hibernate.SessionFactory;
import services.ProfessorService;

import javax.persistence.EntityManagerFactory;
import java.util.Scanner;

public class ChangePasswordController {
    private final Scanner sc = new Scanner(System.in);
    private final Professor professor;
    private final ProfessorService professorService;

    public ChangePasswordController(EntityManagerFactory entityManagerFactory, Integer professorId) {
        professorService = new ProfessorService(entityManagerFactory);
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
