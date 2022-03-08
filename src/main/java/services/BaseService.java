package services;

import lombok.Getter;
import org.hibernate.SessionFactory;

import java.sql.Connection;

@Getter
public abstract class BaseService {
    private final SessionFactory sessionFactory;

    public BaseService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
