package services;

import models.things.Course;
import models.things.Grade;
import models.things.Term;
import models.users.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactorySingletonTest {
    private SessionFactorySingletonTest() {}

    private static class Holder {
        static SessionFactory INSTANCE;

        static {
            var registry = new StandardServiceRegistryBuilder()
                    .configure("hibernate-test.cfg.xml") //goes and fetches configurations from hibernate-bank.cfg.xml
                    .build();

            //registry is useful for creating session factory
            INSTANCE = new MetadataSources(registry)
                    .addAnnotatedClass(Course.class)
                    .addAnnotatedClass(Grade.class)
                    .addAnnotatedClass(Term.class)

                    .addAnnotatedClass(Clerk.class)
                    .addAnnotatedClass(Professor.class)
                    .addAnnotatedClass(ProfPosition.class)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(User.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
    }

    public static SessionFactory getInstance() {
        return Holder.INSTANCE;
    }
}
