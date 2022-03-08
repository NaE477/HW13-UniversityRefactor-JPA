package controllers.professorControllers;

import controllers.Utilities;
import models.things.Course;
import models.things.Grade;
import models.users.Professor;
import models.users.Student;
import org.hibernate.SessionFactory;
import services.CourseService;
import services.GradeService;
import services.ProfessorService;
import services.StudentService;

import java.util.List;
import java.util.Objects;

public class EnterGradeController {
    private final CourseService courseService;
    private final StudentService studentService;
    private final GradeService gradeService;
    private final Professor professor;

    public EnterGradeController(SessionFactory sessionFactory,Integer professorId) {
        courseService = new CourseService(sessionFactory);
        studentService = new StudentService(sessionFactory);
        gradeService = new GradeService(sessionFactory);
        ProfessorService professorService = new ProfessorService(sessionFactory);
        professor = professorService.find(professorId);
    }

    public void enterGrade() {
        List<Course> courses = courseService.findAllByProfessor(professor);
        courses.forEach(System.out::println);
        Utilities.printGreen("Enter Course ID: ");
        Integer courseID = Utilities.intReceiver();
        Course course = courses
                .stream().filter(c -> Objects.equals(c.getId(),courseID))
                .findAny()
                .orElse(null);
        if (course != null) {
            List<Student> students = studentService.findAll(course);
            if (students.size() > 0) {
                students.forEach(System.out::println);
                Utilities.printGreen("Enter Student ID: ");
                Integer studentID = Utilities.intReceiver();
                Student student = students
                        .stream().filter(s -> Objects.equals(s.getId(),studentID))
                        .findAny()
                        .orElse(null);
                if (student != null) {
                    Grade grade = gradeService.find(student,course);
                    if (grade.getGrade() == null) {
                        Utilities.printGreen("Enter Grade: ");
                        Double gradeToSave = gradeReceiver();
                        grade.setGrade(gradeToSave);
                        gradeService.updateGrade(grade);
                        Utilities.printGreen("Grade inserted for student");
                    } else Utilities.printGreen("Grade already inserted for student,try editing it.");
                } else Utilities.printGreen("Wrong Student ID");
            } else Utilities.printGreen("No Students for this course yet");
        } else Utilities.printGreen("Wrong Course ID");
    }

    private Double gradeReceiver() {
        while (true) {
            Double grade = Utilities.doubleReceiver();
            if (grade <= 20.0 && grade > 0.0) {
                return grade;
            } else Utilities.printGreen("Wrong Grade,Enter a number between 0-20");
        }
    }
}
