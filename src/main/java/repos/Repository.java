package repos;

public interface Repository<T> {
    T ins(T t);
    void update(T t);
    void delete(T t);
}
