package controllers.clerkControllers;

import controllers.Utilities;
import models.things.Term;
import models.users.Clerk;
import org.hibernate.SessionFactory;
import services.*;

import java.util.*;

public class ClerkController {
    private final Clerk clerk;
    private final TermService termService;
    private final SignUpController signUpController;
    private final EditController editController;
    private final DeleteController deleteController;
    private final EndTermController endTermController;
    private final Scanner sc = new Scanner(System.in);

    public ClerkController(SessionFactory sessionFactory, Integer clerkId) {
        ClerkService clerkService = new ClerkService(sessionFactory);
        clerk = clerkService.find(clerkId);
        termService = new TermService(sessionFactory);
        signUpController = new SignUpController(sessionFactory);
        editController = new EditController(sessionFactory);
        deleteController = new DeleteController(sessionFactory);
        endTermController = new EndTermController(sessionFactory);
    }

    public void entry() {
        initiateTerm();
        Utilities.printGreen("Welcome to Clerk Section.\nChoose an option:");
        label:
        while (true) {
            Utilities.printGreen("1-Add Clerk");
            Utilities.printGreen("2-Add Professor");
            Utilities.printGreen("3-Add Student");
            Utilities.printGreen("4-Add Course");
            Utilities.printGreen("5-Edit Clerk");
            Utilities.printGreen("6-Edit Professor");
            Utilities.printGreen("7-Edit Student");
            Utilities.printGreen("8-Edit Course");
            Utilities.printGreen("9-Delete Clerk");
            Utilities.printGreen("10-Delete Professor");
            Utilities.printGreen("11-Delete Student");
            Utilities.printGreen("12-Delete Course");
            Utilities.printGreen("13-View Pay Check");
            Utilities.printGreen("14-End Term");
            Utilities.printGreen("0-Exit");
            System.out.print("Option: ");
            String opt = sc.nextLine();

            switch (opt) {
                case "1":
                    signUpController.addClerk();
                    break;
                case "2":
                    signUpController.addProfessor();
                    break;
                case "3":
                    signUpController.addStudent();
                    break;
                case "4":
                    signUpController.addCourse();
                    break;
                case "5":
                    editController.editClerk();
                    break;
                case "6":
                    editController.editProfessor();
                    break;
                case "7":
                    editController.editStudent();
                    break;
                case "8":
                    editController.editCourse();
                    break;
                case "9":
                    deleteController.deleteClerk();
                    break;
                case "10":
                    deleteController.deleteProfessor();
                    break;
                case "11":
                    deleteController.deleteStudent();
                    break;
                case "12":
                    deleteController.deleteCourse();
                    break;
                case "13":
                    Utilities.printGreen("Pay Check: " + clerk.getSalary().toString());
                    break;
                case "14":
                    endTermController.endTerm();
                    break;
                case "0":
                    break label;
                default:
                    Utilities.printGreen("Wrong Option");
                    break;
            }
        }
    }

    private void initiateTerm() {
        if(termService.findAll().size() == 0) {
            Utilities.printGreen("Enter term prefix: ");
            Integer termPrefix = Utilities.intReceiver();
            Integer firstTermNumber = termPrefix * 10 + 1;
            Term firstTerm = new Term(0,firstTermNumber,null);
            var term = termService.initiate(firstTerm);
            Utilities.printGreen("First term initiated as: " + term.getTerm());
        }
    }
}
