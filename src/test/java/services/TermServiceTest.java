package services;

import models.things.Term;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TermServiceTest {
    private static TermService termService;
    private static EntityManagerFactory entityManagerFactory;
    @BeforeAll
    static void initiate() {
        entityManagerFactory = EntityManagerFactorySingletonTest.getInstance();
        termService = new TermService(entityManagerFactory);
    }

    @Test
    void sessionFactoryTest() {
        assertDoesNotThrow(() -> {
            EntityManagerFactory entityManagerFactory = EntityManagerFactorySingletonTest.getInstance();
            TermService termService = new TermService(entityManagerFactory);
            var size = termService.findAll();
            assertNotNull(size);
        });
    }

    @Test
    void initiateTest() {
        //Arrange
        Term toInitiate = new Term(null,14001,null);
        //Act
        var toIns = termService.initiate(toInitiate);
        //Assert
        assertNotNull(toIns);
        assertEquals(termService.findFirstTerm(),toIns);
        assertEquals(termService.findCurrentTerm(),toIns);
    }

    @Test
    void endTerm() {
        //Arrange
        Term firstTerm = new Term(null,14001,null);
        termService.initiate(firstTerm);
        //Act
        termService.endTerm();
        //Assert
        assertEquals(firstTerm.getTerm() + 1,termService.findCurrentTerm().getTerm());
    }

    @Test
    void findCurrentTerm() {
        //Arrange
        Term firstTerm = new Term(null,14001,null);
        termService.initiate(firstTerm);
        termService.endTerm();
        termService.endTerm();
        //Act
        Term currentTerm = termService.findCurrentTerm();
        //Assert
        assertEquals(firstTerm.getTerm() + 2,currentTerm.getTerm());
    }

    @Test
    void findFirstTerm() {
        //Arrange
        Term firstTerm = new Term(null,14001,null);
        termService.initiate(firstTerm);
        termService.endTerm();
        termService.endTerm();
        //Act
        Term firstTermToFind = termService.findFirstTerm();
        //Assert
        assertEquals(firstTerm.getTerm(),firstTermToFind.getTerm());
        assertEquals(firstTerm,firstTermToFind);
    }

    @Test
    void findAll() {
        //Arrange
        Term firstTerm = new Term(null,14001,null);
        termService.initiate(firstTerm);
        termService.endTerm();
        termService.endTerm();
        //Act
        List<Term> terms = termService.findAll();
        //Assert
        assertNotNull(terms);
        assertEquals(3,terms.size());
    }

    @AfterEach
    void cleanUp() {
        var session = entityManagerFactory.createEntityManager();
        var transaction = session.getTransaction();
        transaction.begin();
        session.createNativeQuery("truncate table term cascade ").executeUpdate();
        transaction.commit();
        session.close();
    }
}