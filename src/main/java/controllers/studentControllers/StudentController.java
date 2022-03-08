package controllers.studentControllers;

import controllers.Utilities;
import models.users.Student;
import org.hibernate.SessionFactory;
import services.StudentService;

import java.util.*;

public class StudentController {
    private final Scanner sc = new Scanner(System.in);
    private final PickCourseController pickCourseController;
    private final ChangePasswordController changePasswordController;
    private final ViewCoursesController viewCoursesController;
    private final Student student;

    public StudentController(SessionFactory sessionFactory, Integer studentId) {
        StudentService studentService = new StudentService(sessionFactory);
        student = studentService.find(studentId);
        pickCourseController = new PickCourseController(sessionFactory, studentId);
        changePasswordController = new ChangePasswordController(sessionFactory,studentId);
        viewCoursesController = new ViewCoursesController(sessionFactory,studentId);
    }

    public void entry() {
        Utilities.printGreen("Welcome to Student Section," + student.getFirstname() + " " + student.getLastname() + "\nChoose an option:");
        label:
        while (true) {
            Utilities.printGreen("1-View Profile");
            Utilities.printGreen("2-View Presented Courses");
            Utilities.printGreen("3-Pick Courses");
            Utilities.printGreen("4-View Picked Courses");
            Utilities.printGreen("5-Change Password");
            Utilities.printGreen("0-Exit");
            Utilities.printGreen("Option: ");
            String opt = sc.nextLine();

            switch (opt) {
                case "1":
                    Utilities.printGreen(String.valueOf(student));
                    break;
                case "2":
                    viewCoursesController.viewCourses();
                    break;
                case "3":
                    pickCourseController.pickCourse();
                    break;
                case "4":
                    viewCoursesController.viewPickedCourses();
                    break;
                case "5":
                    changePasswordController.changePassword();
                case "0":
                    break label;
                default:
                    Utilities.printGreen("Wrong Option.");
                    break;
            }
        }
    }
}
