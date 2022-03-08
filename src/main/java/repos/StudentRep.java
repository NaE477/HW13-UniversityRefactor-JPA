package repos;

import models.things.Course;
import models.users.Student;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class StudentRep extends BaseRepository<Student> {
    public StudentRep(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public Student read(Integer id) {
        var session = entityManagerFactory.createEntityManager();
        try {
            return session.find(Student.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Student read(String username) {
        var session = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Student.class);
            var root = criteriaQuery.from(Student.class);
            var query = criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get("username"), username));
            return session.createQuery(query).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Student> readAll() {
        var session = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Student.class);
            var root = criteriaQuery.from(Student.class);
            var query = criteriaQuery
                    .select(root);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> readAll(Course course) {
        var session = entityManagerFactory.createEntityManager();
        try {
            return session
                    .createQuery("select s from Student s left join fetch s.grades g where g.course.id = :courseId", Student.class)
                    .setParameter("courseId", course.getId())
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
