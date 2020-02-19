package facade;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Joke;
import entity.dto.JokeDTO;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class JokeFacadeTest {

    private static EntityManagerFactory emf;
    private static JokeFacade jokefacade;

    public JokeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        jokefacade = JokeFacade.getJokeFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.Truncate").executeUpdate();
            em.persist(new Joke("This is just a Joke", "There is no reference", "What?"));
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(new Joke("Why did the chicken....", "Whoever made this", "Silly"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void getJokeById_with_invalid_id() {
        JokeDTO joke = jokefacade.getById(131314141);
        assertNull(joke);
    }

    @Test
    public void getJokeById_with_valid_id(){
        JokeDTO joke = jokefacade.getById(1);
        assertEquals("This is just a Joke", joke.getText());
        assertEquals("There is no reference", joke.getReference());
        assertEquals("What?", joke.getType());
        assertEquals(new Long(1L), joke.getId());
    }

    @Test
    public void testGetAll() {
        List<JokeDTO> jokes = jokefacade.getAll();
        assertEquals(2, jokes.size());
        JokeDTO joke1 = jokes.get(0);
        JokeDTO joke2 = jokes.get(1);
        assertEquals("This is just a Joke", joke1.getText());
        assertEquals("There is no reference", joke1.getReference());
        assertEquals("What?", joke1.getType());
        assertEquals(new Long(1L), joke1.getId());

        assertEquals("Why did the chicken....", joke2.getText());
        assertEquals("Whoever made this", joke2.getReference());
        assertEquals("Silly", joke2.getType());
        assertEquals(new Long(2L), joke2.getId());
    }

    @Test
    public void testGetCount() {
        assertEquals(2, jokefacade.getCount());
    }

}
