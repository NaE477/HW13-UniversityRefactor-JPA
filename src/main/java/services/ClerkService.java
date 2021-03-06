package services;

import models.users.Clerk;
import org.hibernate.SessionFactory;
import repos.ClerkRep;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ClerkService extends BaseService {
    private final ClerkRep clerkRep;

    public ClerkService(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
        clerkRep = new ClerkRep(super.getEntityManagerFactory());
    }

    public Clerk signUpClerk(Clerk clerk) {
        return clerkRep.ins(clerk);
    }

    public Clerk find(Integer clerkId) {
        return clerkRep.read(clerkId);
    }

    public Clerk find(String username) {
        return clerkRep.read(username);
    }

    public List<Clerk> findAll() {
        return clerkRep.readAll();
    }

    public void editProfile(Clerk clerk) {
        clerkRep.update(clerk);
    }

    public void deleteClerk(Clerk clerk){
        clerkRep.delete(clerk);
    }
}
