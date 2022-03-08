package repos;

import models.users.Clerk;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class ClerkRep extends BaseRepository<Clerk> {
    public ClerkRep(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public Clerk read(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Clerk.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Clerk read(String username) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("from Clerk where username = :username", Clerk.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Clerk> readAll() {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            /*return entityManager.createQuery("select c from Clerk c",Clerk.class).list();*/
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Clerk.class);
            var root = criteriaQuery.from(Clerk.class);
            var query = criteriaQuery
                    .select(root);
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database changes rolled back due to error.");
            throw e;
        }
    }
}
