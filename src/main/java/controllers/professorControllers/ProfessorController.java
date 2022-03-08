package controllers.professorControllers;

import controllers.Utilities;
import models.users.Professor;
import org.hibernate.SessionFactory;
import services.ProfessorService;

import java.util.Scanner;

public class ProfessorController {
    private final EnterGradeController enterGradeController;
    private final GetSalaryController getSalaryController;
    private final ChangePasswordController changePasswordController;
    private final Professor professor;
    private final Scanner sc = new Scanner(System.in);

    public ProfessorController(SessionFactory sessionFactory, Integer professorId) {
        ProfessorService professorService = new ProfessorService(sessionFactory);
        professor = professorService.find(professorId);
        enterGradeController = new EnterGradeController(sessionFactory, professorId);
        getSalaryController = new GetSalaryController(sessionFactory, professorId);
        changePasswordController = new ChangePasswordController(sessionFactory, professorId);
    }

    public void entry() {
        Utilities.printGreen("Welcome to Professor Section," + professor.getFirstname() + " " + professor.getLastname() + ".\nChoose an Option:");
        label:
        while (true) {
            Utilities.printGreen("1-View Profile");
            Utilities.printGreen("2-Enter Grade");
            Utilities.printGreen("3-View Paycheck");
            Utilities.printGreen("4-Change Password");
            Utilities.printGreen("0-Exit");
            System.out.print("Option: ");
            String opt = sc.nextLine();

            switch (opt) {
                case "1":
                    Utilities.printGreen(String.valueOf(professor));
                    break;
                case "2":
                    enterGradeController.enterGrade();
                    break;
                case "3":
                    getSalaryController.initiation();
                    break;
                case "4":
                    changePasswordController.changePassword();
                    break;
                case "0":
                    break label;
            }
        }
    }


}
