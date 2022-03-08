package repos;

import models.things.Course;
import models.things.Term;
import models.users.Professor;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class CourseRep extends BaseRepository<Course> {
    public CourseRep(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public Course read(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Course.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Course> readAll() {
        var entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Course.class);
        var root = criteriaQuery.from(Course.class);
        var query = criteriaQuery.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Course> readAll(Term term) {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager
                .createQuery("from Course c where c.term.term = :term", Course.class)
                .setParameter("term", term.getTerm());
        return query.getResultList();
    }

    public List<Course> readAllByProfessor(Professor professor) {
        try {
            var entityManager = entityManagerFactory.createEntityManager();
            return entityManager
                    .createQuery("select c from Course c left join fetch c.professor where c.professor.id = :pId", Course.class)
                    .setParameter("pId", professor.getId())
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public void detachProfessor(Professor professor) {
        var entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager
                    .createQuery("update Course c set c.professor = null where c.professor.id = :pId")
                    .setParameter("pId", professor.getId())
                    .executeUpdate();
            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
