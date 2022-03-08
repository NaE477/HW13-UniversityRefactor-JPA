package services;

import models.things.Course;
import models.things.Term;
import models.users.Professor;
import org.hibernate.SessionFactory;
import repos.CourseRep;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CourseService extends BaseService {
    private final CourseRep courseRep;

    public CourseService(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
        courseRep = new CourseRep(super.getEntityManagerFactory());
    }

    public Course createNewCourse(Course course) {
        return courseRep.ins(course);
    }

    public Course find(Integer id) {
        return courseRep.read(id);
    }

    public List<Course> findAll() {
        return courseRep.readAll();
    }

    public List<Course> findAll(Term term) {
        return courseRep.readAll(term);
    }

    public List<Course> findAllByProfessor(Professor professor) {
        return courseRep.readAllByProfessor(professor);
    }

    public void detachProfessor(Professor professor) {
        courseRep.detachProfessor(professor);
    }

    public void editCourse(Course course) {
        courseRep.update(course);
    }

    public void deleteCourse(Course course) {
        courseRep.delete(course);
    }
}
