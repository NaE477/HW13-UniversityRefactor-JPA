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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradeServiceTest {
    private static SessionFactory sessionFactory;

    private static GradeService gradeService;     //main service

    private static CourseService courseService;   //dependency
    private static StudentService studentService; //dependency

    private Student student; //main entity
    private Course course1;   //main entity
    private Course course2;   //main entity
    private Course course3;   //main entity

    private static TermService termService;           //course dependency
    private static ProfessorService professorService; //course dependency

    @BeforeAll
    static void initiate() {
        sessionFactory = SessionFactorySingletonTest.getInstance();
        gradeService = new GradeService(sessionFactory);
        courseService = new CourseService(sessionFactory);
        studentService = new StudentService(sessionFactory);
        termService = new TermService(sessionFactory);
        professorService = new ProfessorService(sessionFactory);

        assertNotNull(sessionFactory);
    }

    @BeforeEach
    void fillDependencies() {
        student = new Student
                (0,"studentFname","studentLname","studentUsername","studentPassword");
        Professor professor = new Professor
                (0, "pFirstname", "pLastname", "pUsername", "pPassword", ProfPosition.C);
        Term term = new Term(0, 141, null);

        course1 = new Course(0,3,"course", professor, term,null);
        course2 = new Course(0,3,"course", professor, term,null);
        course3 = new Course(0,3,"course", professor, term,null);

        studentService.signUpStudent(student);
        professorService.signUpProfessor(professor);
        termService.initiate(term);
        courseService.createNewCourse(course1);
        courseService.createNewCourse(course2);
        courseService.createNewCourse(course3);
    }

    @Test
    void pickCourse() {
        //Arrange
        Grade toSave = new Grade(0,student,course1,18.0);
        Grade toSave2 = new Grade(0,student,course2,18.0);
        //Act
        Grade toInsert = gradeService.pickCourse(toSave);
        Grade toInsert2 = gradeService.pickCourse(toSave2);
        //Assert
        assertNotNull(toInsert);
        assertNotNull(toInsert2);
        assertNull(toInsert.getGrade());
        assertNull(toInsert2.getGrade());
        assertEquals(1,courseService.find(course1.getId()).getGrades().size());
    }

    @Test
    void updateGrade() {
        //Arrange
        Grade toSave = new Grade(0,student,course1,18.0);
        Grade toInsert = gradeService.pickCourse(toSave);
        Course newCourse = new Course(0,10,"new course",null,null,null);
        courseService.createNewCourse(newCourse);
        //Act
        Grade toEdit = gradeService.find(student,course1);
        toEdit.setGrade(20.0);
        toEdit.setCourse(newCourse);
        gradeService.updateGrade(toEdit);
        //Assert
        assertNotNull(gradeService.find(student,newCourse));
        assertNull(gradeService.find(student,course1));
        assertEquals(20,gradeService.find(student,newCourse).getGrade());
    }

    @Test
    void deleteGrade() {
        //Arrange
        Grade toSave = new Grade(0,student,course1,18.0);
        Grade toInsert = gradeService.pickCourse(toSave);
        //Act
        Grade toDelete = gradeService.find(student,course1);
        gradeService.deleteGrade(toDelete);
        //Assert
        assertNull(gradeService.find(student,course1));
    }

    @Test
    void find() {
        //Arrange
        var grade = new Grade(0,student,course1,18.0);
        Grade toSave = gradeService.pickCourse(grade);
        //Act
        Grade toFind = gradeService.find(student,course1);
        //Assert
        assertNotNull(toFind);
    }

    @Test
    void findAllByStudent() {
        //Arrange
        Student newStudent = new Student(0,"newStudent","newStudent","newStudent","newStudent");
        studentService.signUpStudent(newStudent);
        Grade grade1 = new Grade(0,student,course1,13.0);
        Grade grade2 = new Grade(0,student,course2,15.0);
        Grade shouldNotBeFound = new Grade(0,newStudent,course3,18.0);
        gradeService.pickCourse(grade1);
        gradeService.pickCourse(grade2);
        gradeService.pickCourse(shouldNotBeFound);
        //Act
        List<Grade> grades = gradeService.findAllByStudent(student);
        //Assert
        assertNotNull(grades);
        assertEquals(2,grades.size());
    }

    @AfterEach
    void cleanDependencies() {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.createSQLQuery("truncate table student cascade ").executeUpdate();
        session.createSQLQuery("truncate table professor cascade").executeUpdate();
        session.createSQLQuery("truncate table term cascade").executeUpdate();
        session.createSQLQuery("truncate table course cascade").executeUpdate();
        session.createSQLQuery("truncate table grade cascade").executeUpdate();
        transaction.commit();
        session.close();
    }
}