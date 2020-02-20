package entity;
/*
 * author benjaminp
 * version 1.0
 */

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class Car {

    private static Car instance;
    private static EntityManagerFactory emf;

    private Car() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static Car getCar(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Car();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getCount() {
        throw new UnsupportedOperationException();
    }

    public List<Car> getAll() {
        throw new UnsupportedOperationException();
    }

    public List<Car> getByName(String name) {
        throw new UnsupportedOperationException();
    }

    public Car getById(long id) {
        throw new UnsupportedOperationException();
    }

    public Car create(Car car) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return car;
    }

    public void populate() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            throw new UnsupportedOperationException();
        } finally {
            em.close();
        }
    }
}
