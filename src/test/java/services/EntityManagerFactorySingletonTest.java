package services;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingletonTest {
    private EntityManagerFactorySingletonTest() {}

    private static class Holder {
        static EntityManagerFactory INSTANCE;

        static {
            INSTANCE = Persistence.createEntityManagerFactory("university-test");
        }
    }

    public static EntityManagerFactory getInstance() {
        return Holder.INSTANCE;
    }
}
