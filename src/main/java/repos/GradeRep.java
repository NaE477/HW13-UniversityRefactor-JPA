package repos;

import models.things.Course;
import models.things.Grade;
import models.users.Student;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class GradeRep extends BaseRepository<Grade> {

    public GradeRep(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public List<Grade> readAllByStudent(Student student) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("select g from Grade g where g.student.id = :sId", Grade.class)
                    .setParameter("sId", student.getId())
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public Grade read(Student student, Course course) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("select g from Grade g where g.course.id = :cId and g.student.id = :sId", Grade.class)
                    .setParameter("cId", course.getId())
                    .setParameter("sId", student.getId())
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
