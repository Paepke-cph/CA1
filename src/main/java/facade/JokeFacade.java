package facade;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Joke;
import entity.dto.JokeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class JokeFacade {

    private static JokeFacade instance;
    private static EntityManagerFactory emf;
    private Random random = new Random();

    private JokeFacade() {}
    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static JokeFacade getJokeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new JokeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<JokeDTO> toJokeDTOList(List<Joke> jokes) {
        List<JokeDTO> result = new ArrayList<>();
        jokes.forEach(joke -> {
            result.add(new JokeDTO(joke));
        });
        return result;
    }

    public int getCount() {
        EntityManager entityManager = getEntityManager();
        return Integer.parseInt(entityManager.createQuery("SELECT count(j) FROM Joke j", Long.class).getSingleResult().toString());
    }

    public List<JokeDTO> getAll() {
        EntityManager entityManager = getEntityManager();
        List<Joke> jokes = entityManager.createNamedQuery("Joke.getAll", Joke.class).getResultList();
        return toJokeDTOList(jokes);
    }

    public JokeDTO getById(long id) {
        EntityManager entityManager = getEntityManager();
        Joke joke = entityManager.find(Joke.class, id);
        if(joke != null) return new JokeDTO(joke);
        else return null;
    }

    public JokeDTO getRandomJoke() {
        EntityManager entityManager = getEntityManager();
        int count = getCount();
        int jokeID = random.nextInt(count)+1;
        return getById(jokeID);
    }


    public void populate() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.Truncate").executeUpdate();
            em.persist(new Joke("Hvorfor var blondinen glad for, at samle et puzzlespil på 6 måneder? – fordi der stod 2-4 år",
                    "https://www.vitser-jokes.dk/",
                    "Grov"));
            em.persist(new Joke("En røver kommer ind i butikken og stjæler et TV. blondinen løber efter ham og råber, “Vent, du har glemt fjernbetjeningen!",
                    "https://www.vitser-jokes.dk/",
                    "Grov"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
