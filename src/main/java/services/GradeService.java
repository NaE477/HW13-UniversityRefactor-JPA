package services;

import models.things.Course;
import models.things.Grade;
import models.users.Student;
import org.hibernate.SessionFactory;
import repos.GradeRep;

import java.util.List;

public class GradeService extends BaseService{
    private final GradeRep gradeRep;
    public GradeService(SessionFactory sessionFactory) {
        super(sessionFactory);
        gradeRep = new GradeRep(super.getSessionFactory());
    }

    public Grade pickCourse(Grade grade) {
        if(find(grade.getStudent(),grade.getCourse()) == null) {
            grade.setGrade(null); //to control course won't be picked with a number
            return gradeRep.ins(grade);
        } else return null;
    }

    public void updateGrade(Grade grade) {
        gradeRep.update(grade);
    }

    public void deleteGrade(Grade grade) {
        gradeRep.delete(grade);
    }

    public Grade find(Student student,Course course){
        return gradeRep.read(student,course);
    }

    public List<Grade> findAllByStudent(Student student) {
        return gradeRep.readAllByStudent(student);
    }
}
