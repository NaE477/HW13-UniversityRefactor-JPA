package controllers.studentControllers;

import controllers.Utilities;
import models.things.Course;
import models.things.Grade;
import models.users.Student;
import org.hibernate.SessionFactory;
import services.CourseService;
import services.GradeService;
import services.StudentService;
import services.TermService;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ViewCoursesController {
    private final GradeService gradeService;
    private final CourseService courseService;
    private final TermService termService;
    private final Student student;

    public ViewCoursesController(EntityManagerFactory entityManagerFactory, Integer studentId) {
        gradeService = new GradeService(entityManagerFactory);
        courseService = new CourseService(entityManagerFactory);
        termService = new TermService(entityManagerFactory);
        StudentService studentService = new StudentService(entityManagerFactory);
        student = studentService.find(studentId);
    }

    public void viewCourses() {
        List<Course> courses = courseService.findAll(termService.findCurrentTerm());
        courses.forEach(course -> Utilities.printGreen(course.toString()));
    }

    public void viewPickedCourses() {
        List<Grade> grades = gradeService.findAllByStudent(student);
        if(grades.size() > 0)
        grades.forEach((grade) -> {
            if (grade.getGrade() != null) {
                Utilities.printGreen(grade.getCourse() + "\nGrade: " + grade.getGrade());
            } else Utilities.printGreen(grade.getCourse() + "\nProfessor haven't entered a grade for this course yet.");
        });
        else Utilities.printGreen("No courses picked yet");
    }
}
