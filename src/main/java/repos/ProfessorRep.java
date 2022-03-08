package repos;

import models.users.Professor;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class ProfessorRep extends BaseRepository<Professor> {
    public ProfessorRep(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public Professor read(Integer id) {
        var session = entityManagerFactory.createEntityManager();
        try {
            return session.find(Professor.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Professor read(String username) {
        var session = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Professor.class);
            var root = criteriaQuery.from(Professor.class);
            var query = criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get("username"), username));
            return session.createQuery(query).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Professor> readAll() {
        var session = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Professor.class);
            var root = criteriaQuery.from(Professor.class);
            var query = criteriaQuery
                    .select(root);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
