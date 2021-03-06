package services;

import models.things.Course;
import models.things.Grade;
import models.users.Student;
import org.hibernate.SessionFactory;
import repos.GradeRep;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class GradeService extends BaseService{
    private final GradeRep gradeRep;
    public GradeService(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
        gradeRep = new GradeRep(super.getEntityManagerFactory());
    }

    public Grade pickCourse(Grade grade) {
        if(find(grade.getStudent(),grade.getCourse()) == null) {
            grade.setGrade(0.0); //to control course won't be picked with a number
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
