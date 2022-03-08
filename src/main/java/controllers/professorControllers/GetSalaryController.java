package controllers.professorControllers;

import controllers.Utilities;
import models.things.Course;
import models.things.Term;
import models.users.ProfPosition;
import models.users.Professor;
import org.hibernate.SessionFactory;
import services.ProfessorService;
import services.TermService;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GetSalaryController {
    private final Professor professor;
    private final TermService termService;
    private final Term term;
    public GetSalaryController(SessionFactory sessionFactory, Integer professorId) {
        ProfessorService professorService = new ProfessorService(sessionFactory);
        professor = professorService.find(professorId);
        termService = new TermService(sessionFactory);
        term = termService.findCurrentTerm();
    }

    public void initiation() {
        termService.findAll().forEach(t -> Utilities.printGreen(t.getTerm().toString()));
        Utilities.printGreen("Which Term: ");
        Integer term = termReceiver();
        Utilities.printGreen(getSalary(term).toString());
    }

    private Long getSalary(Integer term) {
        AtomicReference<Long> salary = new AtomicReference<>((long) 0);

        List<Course> termCourses = professor
                .getCourses()
                .stream()
                .filter(course -> course.getTerm().getTerm().equals(term))
                .collect(Collectors.toList());

        termCourses
                .stream()
                .mapToLong(Course::getUnits)
                .forEach(unit -> salary.updateAndGet(v -> v + unit * 1000000));

        if (professor.getProfPosition().equals(ProfPosition.C)) {
            return salary.updateAndGet(v -> v + 5000000);
        } else {
            return salary.get();
        }
    }

    private Integer termReceiver() {
        while (true) {
            Term firstTerm = termService.findFirstTerm();
            Integer term = Utilities.intReceiver();
            if (term <= this.term.getTerm() && term > 0) {
                return term;
            } else Utilities.printGreen("Wrong term,Choose a term between: " + firstTerm.getTerm() + "-" + this.term.getTerm());
        }
    }
}
