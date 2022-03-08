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
            var registry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml") //goes and fetches configurations from hibernate-bank.cfg.xml
                    .build();

            //registry is useful for creating session factory
            INSTANCE = Persistence.createEntityManagerFactory("UNIVERSITY");
        }
    }

    public static EntityManagerFactory getInstance() {
        return Holder.INSTANCE;
    }
}
