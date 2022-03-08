package controllers.studentControllers;

import controllers.Utilities;
import models.things.Course;
import models.things.Grade;
import models.things.Term;
import models.users.Student;
import org.hibernate.SessionFactory;
import services.CourseService;
import services.GradeService;
import services.StudentService;
import services.TermService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PickCourseController {
    private final CourseService courseService;
    private final GradeService gradeService;
    private final GradeUtils gradeUtils;
    private final Term term;
    private final Student student;

    public PickCourseController(SessionFactory sessionFactory, Integer studentId) {
        courseService = new CourseService(sessionFactory);
        gradeService = new GradeService(sessionFactory);
        gradeUtils = new GradeUtils(sessionFactory);
        TermService termService = new TermService(sessionFactory);
        term = termService.findCurrentTerm();
        StudentService studentService = new StudentService(sessionFactory);
        student = studentService.find(studentId);
    }


    public void pickCourse() {
        List<Course> allCourses = courseService.findAll(term);

        List<Grade> reportCard = gradeService.findAllByStudent(student);

        Set<Course> pickedCourses = gradeUtils.pickedCourses(reportCard);

        allCourses.forEach(System.out::println);
        Utilities.printGreen("Enter Course ID you want to pick:");
        Integer courseToPickId = Utilities.intReceiver();

        Course courseToPick = allCourses
                .stream()
                .filter(c -> Objects.equals(c.getId(), courseToPickId))
                .findAny()
                .orElse(null);

        if (courseToPick != null) {
            if (!pickedCourses.contains(courseToPick)) {
                if (canPickByThreshold(courseToPick)) {
                    Grade grade = gradeService.pickCourse(new Grade(0, student, courseToPick, null));
                    if (grade != null) {
                        Utilities.printGreen(grade.getCourse().getCourseName() + " picked successfully");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else Utilities.printGreen("Something went wrong with database.");
                } else Utilities.printGreen("You Can't Pick this course.Your unit threshold is filled.");
            } else Utilities.printGreen("Course Already Picked");
        } else Utilities.printGreen("Wrong ID");
    }

    private Boolean canPickByThreshold(Course courseToPick) {
        Set<Grade> finishedCoursesLastTerm = gradeUtils.finishedCoursesLastTerm(student);

        Set<Grade> unfinishedCoursesThisTerm = gradeUtils.unfinishedCoursesThisTerm(student);

        Integer unitsPicked = gradeUtils.unitsPicked(unfinishedCoursesThisTerm);

        Double gradeSum = gradeUtils.gradeSum(finishedCoursesLastTerm);

        Integer unitsPassed = gradeUtils.unitsPassed(finishedCoursesLastTerm);

        double averageGrade;
        try {
            averageGrade = gradeSum / unitsPassed;
        } catch (ArithmeticException e) {
            averageGrade = 0.0;
        }
        int pickingThreshold;

        int unitToBePicked = unitsPicked + courseToPick.getUnits();

        if (finishedCoursesLastTerm.size() == 0) pickingThreshold = 20;
        else if (averageGrade > 18) pickingThreshold = 24;
        else if (averageGrade != 0 && averageGrade < 14) pickingThreshold = 12;
        else pickingThreshold = 20;

        return unitToBePicked <= pickingThreshold;
    }
}
