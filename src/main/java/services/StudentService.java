package services;

import models.things.Course;
import models.users.Student;
import org.hibernate.SessionFactory;
import repos.StudentRep;

import java.util.List;

public class StudentService extends BaseService {
    private final StudentRep studentRep;

    public StudentService(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.studentRep = new StudentRep(sessionFactory);
    }

    public Student signUpStudent(Student student) {
        return studentRep.ins(student);
    }
    public Student find(Integer studentId){
        return studentRep.read(studentId);
    }
    public Student find(String username){
        return studentRep.read(username);
    }
    public List<Student> findAll(){
        return studentRep.readAll();
    }
    public List<Student> findAll(Course course){
        return studentRep.readAll(course);
    }
    public void editProfile(Student student){
        studentRep.update(student);
    }
    public void delete(Student student){
        studentRep.delete(student);
    }
}
