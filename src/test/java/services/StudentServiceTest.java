package services;

import models.things.Course;
import models.things.Grade;
import models.things.Term;
import models.users.ProfPosition;
import models.users.Professor;
import models.users.Student;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private static StudentService studentService;
    private static SessionFactory sessionFactory;

    @BeforeAll
    static void initialize() {
        sessionFactory = SessionFactorySingletonTest.getInstance();
        studentService = new StudentService(sessionFactory);
    }

    @Test
    void sessionFactoryTest() {
        assertDoesNotThrow(() -> {
            SessionFactory sessionFactory = SessionFactorySingletonTest.getInstance();
            StudentService studentService = new StudentService(sessionFactory);
            studentService.findAll();
        });
    }

    @Test
    void signUpStudent() {
        //Arrange
        var student = new Student
                (0,"sFirstname","sLastname","sUsername","sLastname");
        //Act
        var toSave = studentService.signUpStudent(student);
        //Assert
        assertNotNull(toSave);
        assertEquals(student,toSave);
        assertEquals(student.getFirstname(),toSave.getFirstname());
    }

    @Test
    void findById() {
        //Arrange
        var student = new Student
                (0,"sFirstname","sLastname","sUsername","sLastname");
        studentService.signUpStudent(student);
        //Act
        var toFind = studentService.find(student.getId());
        //Assert
        assertNotNull(toFind);
        assertEquals(student,toFind);
    }

    @Test
    void findByUsername() {
        //Arrange
        var student = new Student
                (0,"sFirstname","sLastname","sUsername","sLastname");
        studentService.signUpStudent(student);
        //Act
        var toFind = studentService.find(student.getUsername());
        //Assert
        assertNotNull(toFind);
        assertEquals(student,toFind);
    }

    @Test
    void findAll() {
        //Arrange
        var student1 = new Student
                (0,"sFirstname","sLastname","sUsername1","sLastname");
        var student2 = new Student
                (0,"sFirstname","sLastname","sUsername2","sLastname");
        var student3 = new Student
                (0,"sFirstname","sLastname","sUsername3","sLastname");
        studentService.signUpStudent(student1);
        studentService.signUpStudent(student2);
        studentService.signUpStudent(student3);
        //Act
        List<Student> students = studentService.findAll();
        //Assert
        assertNotNull(students);
        assertEquals(3,students.size());
    }

    @Test
    void findAllByCourse() {
        //Arrange
        CourseService courseService = new CourseService(SessionFactorySingletonTest.getInstance());
        GradeService gradeService = new GradeService(SessionFactorySingletonTest.getInstance());
        ProfessorService professorService = new ProfessorService(SessionFactorySingletonTest.getInstance());
        TermService termService = new TermService(SessionFactorySingletonTest.getInstance());
        Term term = new Term(0,3,null);
        termService.initiate(term);
        var professor = new Professor(0,"pFirstname","pLastname","pUsername","pLastname", ProfPosition.C);
        professorService.signUpProfessor(professor);
        var course1 = new Course(0,3,"course1",professor,term,null);
        var course2 = new Course(0,2,"course2",professor,term,null);
        var course3 = new Course(0,1,"course3",professor,term,null);
        courseService.createNewCourse(course1);
        courseService.createNewCourse(course2);
        courseService.createNewCourse(course3);
        var student1 = new Student
                (0,"sFirstname","sLastname","sUsername1","sLastname");
        var student2 = new Student
                (0,"sFirstname","sLastname","sUsername2","sLastname");
        var student3 = new Student
                (0,"sFirstname","sLastname","sUsername3","sLastname");
        studentService.signUpStudent(student1);
        studentService.signUpStudent(student2);
        studentService.signUpStudent(student3);
        Grade grade1 = new Grade(0,student1,course1,null);
        Grade grade2 = new Grade(0,student1,course2,null);
        Grade grade3 = new Grade(0,student1,course3,null);
        Grade grade4 = new Grade(0,student2,course3,null);
        Grade grade5 = new Grade(0,student3,course3,null);
        gradeService.pickCourse(grade1);
        gradeService.pickCourse(grade2);
        gradeService.pickCourse(grade3);
        gradeService.pickCourse(grade4);
        gradeService.pickCourse(grade5);
        //Act
        List<Student> studentsByCourse1 = studentService.findAll(course1);
        List<Student> studentsByCourse2 = studentService.findAll(course2);
        List<Student> studentsByCourse3 = studentService.findAll(course3);
        //Assert
        assertNotNull(studentsByCourse1);
        assertNotNull(studentsByCourse2);
        assertNotNull(studentsByCourse3);
        assertEquals(1,studentsByCourse1.size());
        assertEquals(1,studentsByCourse2.size());
        assertEquals(3,studentsByCourse3.size());
    }

    @Test
    void editProfile() {
        //Arrange
        var student = new Student
                (0,"sFirstname","sLastname","sUsername","sPassword");
        var toEdit = studentService.signUpStudent(student);
        //Act
        toEdit.setPassword("edited");
        toEdit.setUsername("edited");
        studentService.editProfile(toEdit);
        var edited = studentService.find("edited");
        //Assert
        assertEquals("edited",edited.getUsername());
        assertEquals("edited",edited.getPassword());
        assertNotNull(edited);
    }

    @Test
    void delete() {
        //Arrange
        var toDelete = new Student
                (0,"sFirstname","sLastname","sUsername","sLastname");
        studentService.signUpStudent(toDelete);
        //Act
        studentService.delete(toDelete);
        var toNotFind = studentService.find(toDelete.getUsername());
        var toNotFindById = studentService.find(toDelete.getId());
        //Assert
        assertNull(toNotFind);
        assertNull(toNotFindById);
    }

    @AfterEach
    void cleanDependencies() {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.createSQLQuery("truncate table student cascade ").executeUpdate();
        //performed only for findAllByCourse test
        session.createSQLQuery("truncate table professor cascade ").executeUpdate();
        session.createSQLQuery("truncate table course cascade ").executeUpdate();
        session.createSQLQuery("truncate table grade cascade ").executeUpdate();
        session.createSQLQuery("truncate table term cascade ").executeUpdate();

        transaction.commit();
        session.close();
    }
}