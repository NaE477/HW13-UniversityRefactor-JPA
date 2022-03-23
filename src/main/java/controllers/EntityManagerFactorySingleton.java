package controllers;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {
    private EntityManagerFactorySingleton() {
    }

    private static class Holder {
        static EntityManagerFactory INSTANCE;

        static {
            //registry is useful for creating session factory
            INSTANCE = Persistence.createEntityManagerFactory("university");
        }
    }

    public static EntityManagerFactory getInstance() {
        return Holder.INSTANCE;
    }
}
