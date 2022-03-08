package controllers.clerkControllers;

import controllers.Utilities;
import models.things.Course;
import models.users.Clerk;
import models.users.Professor;
import models.users.Student;
import org.hibernate.SessionFactory;
import services.ClerkService;
import services.CourseService;
import services.ProfessorService;
import services.StudentService;

import java.util.List;
import java.util.Objects;

public class DeleteController {
    private final ClerkService clerkService;
    private final ProfessorService professorService;
    private final StudentService studentService;
    private final CourseService courseService;

    public DeleteController(SessionFactory sessionFactory) {
        clerkService = new ClerkService(sessionFactory);
        professorService = new ProfessorService(sessionFactory);
        studentService = new StudentService(sessionFactory);
        courseService = new CourseService(sessionFactory);
    }

    public void deleteClerk() {
        List<Clerk> clerks = clerkService.findAll();
        clerks.forEach(System.out::println);
        Utilities.printGreen("Enter clerk ID to delete: ");
        Integer clerkToDeleteID = Utilities.intReceiver();
        Clerk clerkToDelete = clerks
                .stream().filter(clerk -> Objects.equals(clerk.getId(),clerkToDeleteID))
                .findAny()
                .orElse(null);
        if (clerkToDelete != null) {
            clerkService.deleteClerk(clerkToDelete);
        } else Utilities.printGreen("Wrong ID");
    }

    public void deleteProfessor() {
        List<Professor> professors = professorService.findAll();
        professors.forEach(System.out::println);
        Utilities.printGreen("Professor ID: ");
        Integer profToDeleteID = Utilities.intReceiver();
        Professor professorToDelete = professors
                .stream()
                .filter(professor -> Objects.equals(professor.getId(), profToDeleteID))
                .findAny()
                .orElse(null);
        if (professorToDelete != null) {
            professorService.deleteProfessor(professorToDelete);
        } else Utilities.printGreen("Wrong ID");
    }

    public void deleteStudent() {
        List<Student> students = studentService.findAll();
        students.forEach(System.out::println);
        Utilities.printGreen("Student ID: ");
        Integer studentToDeleteID = Utilities.intReceiver();
        Student studentToDelete = students
                .stream()
                .filter(student -> Objects.equals(student.getId(),studentToDeleteID))
                .findAny()
                .orElse(null);
        if (studentToDelete != null) {
            studentService.delete(studentToDelete);
        } else Utilities.printGreen("Wrong ID");
    }

    public void deleteCourse() {
        List<Course> courses = courseService.findAll();
        courses.forEach(System.out::println);
        Utilities.printGreen("Course ID: ");
        Integer courseToDeleteID = Utilities.intReceiver();
        Course courseToDelete = courses
                .stream().filter(course -> Objects.equals(course.getId(),courseToDeleteID))
                .findAny()
                .orElse(null);
        if (courseToDelete != null) {
            courseService.deleteCourse(courseToDelete);
        } else Utilities.printGreen("Wrong ID");
    }
}
