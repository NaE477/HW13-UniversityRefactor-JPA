package repos;

import models.users.Professor;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class ProfessorRep extends BaseRepository<Professor> {
    public ProfessorRep(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Professor read(Integer id) {
        try (var session = sessionFactory.openSession()) {
            try {
                return session.get(Professor.class, id);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public Professor read(String username){
        try (var session = sessionFactory.openSession()) {
            try {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                var criteriaQuery = criteriaBuilder.createQuery(Professor.class);
                var root = criteriaQuery.from(Professor.class);
                var query = criteriaQuery
                        .select(root)
                        .where(criteriaBuilder.equal(root.get("username"),username));
                return session.createQuery(query).getSingleResult();
            } catch (Exception e) {
                return null;
            }
        }
    }
    public List<Professor> readAll(){
        try (var session = sessionFactory.openSession()) {
            try {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                var criteriaQuery = criteriaBuilder.createQuery(Professor.class);
                var root = criteriaQuery.from(Professor.class);
                var query = criteriaQuery
                        .select(root);
                return session.createQuery(query).list();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
