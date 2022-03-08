package services;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingletonTest {
    private EntityManagerFactorySingletonTest() {
    }

    private static class Holder {
        static EntityManagerFactory INSTANCE;

        static {
            //registry is useful for creating session factory
            INSTANCE = Persistence.createEntityManagerFactory("UNIVERSITY");
        }
    }

    public static EntityManagerFactory getInstance() {
        return Holder.INSTANCE;
    }
}
