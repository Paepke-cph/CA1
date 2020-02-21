package facade;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Car;
import entity.dto.CarDTO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;

    private CarFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CarFacade getCarFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CarDTO> toDTOList(List<Car> cars) {
        List<CarDTO> dtos = new ArrayList<>();
        cars.forEach(car -> {
            dtos.add(new CarDTO(car));
        });
        return dtos;
    }

    public long getCount() {
        EntityManager entityManager = getEntityManager();
        int count = Integer.parseInt(entityManager.createQuery("SELECT count(c) FROM Car c", Long.class).getSingleResult().toString());
        return count;
    }

    public List<CarDTO> getAll() {
        EntityManager entityManager = getEntityManager();
        List<Car> cars = entityManager.createNamedQuery("Car.getAll", Car.class).getResultList();
        return toDTOList(cars);
    }

    public CarDTO getById(long id) {
        EntityManager entityManager = getEntityManager();
        Car car = entityManager.find(Car.class, id);
        if(car == null) return null;
        else return new CarDTO(car);
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
            em.createNamedQuery("Car.Truncate").executeUpdate();
            em.persist(new Car());
        } finally {
            em.close();
        }
    }
}
