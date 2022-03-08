package controllers.studentControllers;

import models.things.Course;
import models.things.Grade;
import models.things.Term;
import models.users.Student;
import org.hibernate.SessionFactory;
import services.TermService;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GradeUtils {
    private final TermService termService;
    private final Term term;

    public GradeUtils(SessionFactory sessionFactory) {
        termService = new TermService(sessionFactory);
        term = termService.findCurrentTerm();
    }


    public Set<Grade> finishedCoursesLastTerm(Student student) {
        if (term.equals(termService.findFirstTerm())) return new HashSet<>();
        else return student
                .getGrades()
                .stream()
                .filter(grade -> Objects.nonNull(grade.getGrade()))
                .filter(grade -> grade.getCourse().getTerm().getTerm() == term.getTerm() - 1)
                .collect(Collectors.toSet());
    }

    public Set<Grade> unfinishedCoursesThisTerm(Student student) {
        return student
                .getGrades()
                .stream()
                .filter(grade -> Objects.isNull(grade.getGrade()))
                .filter(grade -> grade.getCourse().getTerm().equals(term))
                .collect(Collectors.toSet());
    }

    public Set<Course> pickedCourses(List<Grade> reportCard) {
        return reportCard
                .stream()
                .map(Grade::getCourse)
                .collect(Collectors.toSet());
    }

    public Integer unitsPicked(Set<Grade> anySetOfGrades) {
        AtomicReference<Integer> unitsPicked = new AtomicReference<>(0);
        if (anySetOfGrades.size() > 0)
            anySetOfGrades.stream().filter(grade -> grade.getCourse() != null).forEach(grade -> unitsPicked.updateAndGet(v -> v + (grade.getCourse().getUnits())));
        return unitsPicked.get();
    }

    public Integer unitsPassed(Set<Grade> anySetOfGrades) {
        AtomicReference<Integer> unitsPassed = new AtomicReference<>(0);
        if (anySetOfGrades.size() > 0)
            anySetOfGrades.stream().filter(grade -> grade.getCourse() != null).forEach((grade) -> unitsPassed.updateAndGet(v -> v + grade.getCourse().getUnits()));
        return unitsPassed.get();
    }

    public Double gradeSum(Set<Grade> anySetOfGrades) {
        AtomicReference<Double> gradeSum = new AtomicReference<>(0.0);
        if (anySetOfGrades.size() > 0)
            anySetOfGrades.stream().filter(grade -> grade.getCourse() != null).forEach((grade) -> gradeSum.updateAndGet(v -> v + grade.getCourse().getUnits() * grade.getGrade()));
        return gradeSum.get();
    }
}
