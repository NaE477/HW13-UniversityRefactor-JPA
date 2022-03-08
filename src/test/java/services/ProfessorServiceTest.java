package services;

import models.users.ProfPosition;
import models.users.Professor;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorServiceTest {
    static SessionFactory sessionFactory;
    static ProfessorService professorService;

    @BeforeAll
    static void initiate() {
        sessionFactory = SessionFactorySingletonTest.getInstance();
        professorService = new ProfessorService(sessionFactory);
        assertNotNull(sessionFactory);
    }

    @Test
    void sessionFactoryTest() {
        var sessionFactory = SessionFactorySingletonTest.getInstance();
        assertNotNull(sessionFactory);
    }

    @Test
    void signUpProfessor() {
        //Arrange
        var professor = new Professor
                (0,"pFirstname","pLastname","pUsername","pLastname", ProfPosition.C);
        var conflictMaker = new Professor
                (0,"pFirstname2","pLastname2","pUsername","pLastname2", ProfPosition.NC);
        //Act
        var toSign = professorService.signUpProfessor(professor);
        //Assert
        assertNotNull(toSign);
        assertEquals(toSign.getFirstname(),professor.getFirstname());
        assertEquals(1,professorService.findAll().size());
//        assertThrows(Exception.class, () -> professorService.signUpProfessor(conflictMaker));
    }

    @Test
    void findById() {
        //Arrange
        var professor = new Professor
                (0,"pFirstname","pLastname","pUsername","pLastname", ProfPosition.C);
        var toSign = professorService.signUpProfessor(professor);
        //Act
        var toFind = professorService.find(toSign.getId());
        //Assert
        assertNotNull(toFind);
        assertEquals(toFind.getFirstname(),toSign.getFirstname());
        assertEquals(toFind.getId(),toSign.getId());
    }

    @Test
    void findByUsername() {
        //Arrange
        var professor = new Professor
                (0,"pFirstname","pLastname","pUsername","pLastname", ProfPosition.C);
        var toSign = professorService.signUpProfessor(professor);
        //Act
        var toFind = professorService.find(toSign.getUsername());
        //Assert
        assertNotNull(toFind);
        assertEquals(toFind.getFirstname(),toSign.getFirstname());
        assertEquals(toFind.getId(),toSign.getId());
    }

    @Test
    void findAll() {
        //Arrange
        var professor1 = new Professor
                (0,"pFirstname","pLastname","pUsername1","pLastname", ProfPosition.C);
        var professor2 = new Professor
                (0,"pFirstname","pLastname","pUsername2","pLastname", ProfPosition.C);
        var professor3 = new Professor
                (0,"pFirstname","pLastname","pUsername3","pLastname", ProfPosition.C);
        professorService.signUpProfessor(professor1);
        professorService.signUpProfessor(professor2);
        professorService.signUpProfessor(professor3);
        //Act
        List<Professor> professors = professorService.findAll();
        //Assert
        assertEquals(3,professors.size());
        assertNotNull(professors);
    }

    @Test
    void editProfile() {
        //Arrange
        var professor = new Professor
                (0,"pFirstname","pLastname","pUsername1","pLastname", ProfPosition.C);
        var toAssertUsernameUniqueness = new Professor
                (0,"pFirstname","pLastname","pUsername2","pLastname", ProfPosition.C);
        var toEdit = professorService.signUpProfessor(professor);
        professorService.signUpProfessor(toAssertUsernameUniqueness);
        //Act
        toEdit.setProfPosition(ProfPosition.NC);
        toEdit.setFirstname("edited");
        toEdit.setUsername("edited");
        professorService.editProfile(toEdit);
        //Assert
        assertEquals("edited",professorService.find(toEdit.getId()).getUsername());
        assertEquals("edited",professorService.find("edited").getFirstname());
        assertEquals(ProfPosition.NC,professorService.find("edited").getProfPosition());
        assertThrows(PersistenceException.class, () -> {
            toEdit.setUsername("pUsername2");
            professorService.editProfile(toEdit);
        });
    }

    @Test
    void deleteProfessor() {
        //Arrange
        var professor = new Professor
                (0,"pFirstname","pLastname","pUsername1","pLastname", ProfPosition.C);
        var toSave = professorService.signUpProfessor(professor);
        //Act
        professorService.deleteProfessor(toSave);
        //Assert
        assertNull(professorService.find("pUsername1"));
        assertNull(professorService.find(professor.getId()));
    }

    @AfterEach
    void clear() {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.createSQLQuery("truncate table professor cascade").executeUpdate();
        transaction.commit();
        session.close();
    }
}