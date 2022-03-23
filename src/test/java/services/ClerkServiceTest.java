package services;

import models.users.Clerk;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClerkServiceTest {
    static EntityManagerFactory entityManagerFactory;
    static ClerkService clerkService;

    @BeforeAll
    static void initiate() {
        entityManagerFactory = EntityManagerFactorySingletonTest.getInstance();
        clerkService = new ClerkService(entityManagerFactory);
        assertNotNull(entityManagerFactory);
    }

    @Test
    void sessionFactoryTest() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactorySingletonTest.getInstance();
        assertNotNull(entityManagerFactory);
    }

    @Test
    void signUpClerk() {
        //Arrange
        Clerk clerk = new Clerk(null,"firstname","lastname","username","password");

        //Act
        Clerk clerkToSign = clerkService.signUpClerk(clerk);

        //Assert
        assertNotNull(clerkToSign);
        assertNotEquals(0,clerkToSign.getId());
        assertEquals(1,clerkService.findAll().size());
        assertEquals(clerkToSign.getId(),clerk.getId());
    }

    @Test
    void findById() {
        //Arrange
        var clerk = new Clerk(null,"firstname","lastname","username","password");
        var clerkToSign = clerkService.signUpClerk(clerk);

        //Act
        var clerkToFind = clerkService.find(clerkToSign.getId());

        //Assert
        assertEquals(clerkToSign,clerkToFind);
        assertEquals(1,clerkService.findAll().size());
        assertNotNull(clerkToFind);
    }

    @Test
    void findByUsername() {
        //Arrange
        var clerk = new Clerk(null,"firstname","lastname","username","password");
        var clerkToSign = clerkService.signUpClerk(clerk);

        //Act
        var clerkToFind = clerkService.find("username");

        //Assert
        assertEquals(clerkToSign,clerkToFind);
        assertEquals(1,clerkService.findAll().size());
        assertNotNull(clerkToFind);
    }

    @Test
    void findAll() {
        //Arrange
        var clerk1 = new Clerk(null,"firstname1","lastname1","username1","password");
        var clerk2 = new Clerk(null,"firstname2","lastname2","username2","password");
        var clerk3 = new Clerk(null,"firstname3","lastname3","username3","password");
        clerkService.signUpClerk(clerk1);
        clerkService.signUpClerk(clerk2);
        clerkService.signUpClerk(clerk3);

        //Act
        List<Clerk> clerks = clerkService.findAll();

        //Assert
        assertAll(
                () -> assertEquals(3,clerks.size()),
                () -> assertTrue(clerks.contains(clerk1)),
                () -> assertTrue(clerks.contains(clerk2)),
                () -> assertTrue(clerks.contains(clerk3))
        );
    }

    @Test
    void editProfile() {
        //Arrange
        var clerk = new Clerk(null,"firstname","lastname","username","password");
        clerkService.signUpClerk(clerk);
        var clerkToFind = clerkService.find("username");

        //Act
        clerkToFind.setFirstname("edited firstname");
        clerkToFind.setLastname("edited lastname");
        clerkToFind.setUsername("edited username");
        clerkToFind.setPassword("edited password");
        clerkService.editProfile(clerkToFind);

        //Assert
        assertAll(
                () -> assertNotNull(clerkService.find("edited username")),
                () -> assertEquals(clerkToFind.getFirstname(),clerkService.find("edited username").getFirstname())
        );
    }

    @Test
    void deleteClerk() {
        //Arrange
        var clerk = new Clerk(null,"firstname","lastname","username","password");
        clerkService.signUpClerk(clerk);
        var clerkToFind = clerkService.find("username");

        //Act
        clerkService.deleteClerk(clerkToFind);

        //Assert
        assertNull(clerkService.find("username"));
        assertNull(clerkService.find(clerk.getId()));
    }

    @AfterEach
    void clear() {
        var entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createNativeQuery("truncate table clerk").executeUpdate();
        transaction.commit();
        entityManager.close();
    }
}