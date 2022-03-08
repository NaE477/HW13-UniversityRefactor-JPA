package services;

import models.things.Course;
import models.things.Term;
import models.users.ProfPosition;
import models.users.Professor;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {
    private static CourseService courseService;
    private static ProfessorService professorService;
    private static SessionFactory sessionFactory;
    private static TermService termService;
    private Term term;
    private Professor professor;

    @BeforeAll
    static void initiate() {
        sessionFactory = SessionFactorySingletonTest.getInstance();
        courseService = new CourseService(sessionFactory);
        professorService = new ProfessorService(sessionFactory);
        termService = new TermService(sessionFactory);

        assertNotNull(sessionFactory);
    }

    @BeforeEach
    void fillDependencies() {
        professor = new Professor(0,"pFirstname","pLastname","pUsername","pPassword", ProfPosition.C);
        professorService.signUpProfessor(professor);

        term = new Term(0,1,null);
        termService.initiate(term);
    }

    @Test
    void createNewCourse() {
        //Arrange
        Course course = new Course(0,3,"course",professor,term,null);
        //Act
        Course courseToSave = courseService.createNewCourse(course);
        //Assert
        assertNotNull(courseToSave);
        assertEquals(course.getId(),courseToSave.getId());
        assertEquals(1,courseService.findAll().size());
    }

    @Test
    void find() {
        //Arrange
        Course course = new Course(0,3,"course",professor,term,null);
        Course courseToSave = courseService.createNewCourse(course);
        //Act
        Course courseToFind = courseService.find(courseToSave.getId());
        //Assert
        assertNotNull(courseToFind);
        assertEquals(courseToFind,courseToSave);
    }

    @Test
    void findAll() {
        //Arrange
        Course course1 = new Course(0,3,"course1",professor,term,null);
        Course course2 = new Course(0,3,"course2",professor,term,null);
        Course course3 = new Course(0,3,"course3",professor,term,null);
        courseService.createNewCourse(course1);
        courseService.createNewCourse(course2);
        courseService.createNewCourse(course3);
        //Act
        List<Course> courses = courseService.findAll();
        //Assert
        assertEquals(courses.size(),3);
    }

    @Test
    void findAllByProfessor() {
        //Arrange
        Course course1 = new Course(0,3,"course1",professor,term,null);
        Course course2 = new Course(0,3,"course2",professor,term,null);
        Course course3 = new Course(0,3,"course3",professor,term,null);
        Course course4 = new Course(0,3,"course4",null,term,null);
        courseService.createNewCourse(course1);
        courseService.createNewCourse(course2);
        courseService.createNewCourse(course3);
        courseService.createNewCourse(course4);
        //Act
        List<Course> courses = courseService.findAllByProfessor(professor);
        //Assert
        assertEquals(3,courses.size());
        assertFalse(courses.contains(course4));
    }

    @Test
    void detachProfessor() {
        //Arrange
        Course course1 = new Course(0,3,"course1",professor,term,null);
        Course course2 = new Course(0,3,"course2",professor,term,null);
        Course course3 = new Course(0,3,"course3",professor,term,null);
        Course course4 = new Course(0,3,"course4",null,term,null);
        courseService.createNewCourse(course1);
        courseService.createNewCourse(course2);
        courseService.createNewCourse(course3);
        courseService.createNewCourse(course4);
        //Act
        courseService.detachProfessor(professor);
        //Assert
        assertEquals(0,courseService.findAllByProfessor(professor).size());
    }

    @Test
    void editCourse() {
        //Arrange
        Course course = new Course(0,3,"course",professor,term,null);
        Course courseToSave = courseService.createNewCourse(course);
        //Act
        Course courseToEdit = courseService.find(courseToSave.getId());
        courseToEdit.setCourseName("edited name");
        courseToEdit.setUnits(300);
        courseService.editCourse(courseToEdit);
        //Assert
        assertEquals("edited name",courseService.find(courseToEdit.getId()).getCourseName());
        assertEquals(300,courseService.find(courseToEdit.getId()).getUnits());
    }

    @Test
    void deleteCourse() {
        //Arrange
        Course course = new Course(0,3,"course",professor,term,null);
        Course courseToSave = courseService.createNewCourse(course);
        //Act
        courseService.deleteCourse(courseToSave);
        //Assert
        assertNull(courseService.find(course.getId()));
        assertNull(courseService.find(courseToSave.getId()));
    }

    @AfterEach
    void cleanCourse() {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.createSQLQuery("truncate table course cascade ").executeUpdate();
        session.createSQLQuery("truncate table professor cascade").executeUpdate();
        session.createSQLQuery("truncate table term cascade").executeUpdate();
        transaction.commit();
        session.close();
    }
}