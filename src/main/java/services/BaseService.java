package services;

import lombok.Getter;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;

@Getter
public abstract class BaseService {
    private final EntityManagerFactory entityManagerFactory;

    public BaseService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
