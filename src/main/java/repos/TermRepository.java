package repos;

import models.things.Term;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class TermRepository extends BaseRepository<Term> {

    public TermRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public Term read() {
        var session = entityManagerFactory.createEntityManager();
        return session.createQuery("select t from Term t where t.term = (select max(term) from t)", Term.class).getSingleResult();
    }

    public Term readFirst() {
        var session = entityManagerFactory.createEntityManager();
        return session.createQuery("select t from Term t where t.term = (select min(term) from t)", Term.class).getSingleResult();
    }

    public List<Term> readAll() {
        var session = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Term.class);
        var root = criteriaQuery.from(Term.class);
        var query = criteriaQuery.select(root);
        return session.createQuery(query).getResultList();
    }

    @Override
    public void update(Term term) {
    }

    @Override
    public void delete(Term term) {
    }
}
