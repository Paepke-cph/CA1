package facade;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Car;
import entity.dto.CarDTO;
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


public class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade carfacade;

    public CarFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        carfacade = CarFacade.getCarFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        // Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Car.Truncate").executeUpdate();
            em.persist(new Car(1000,"make1","model1",1000,"Owner1"));
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(new Car(2000, "make2","model2",2000,"Owner2"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAll() {
        List<CarDTO> cars = carfacade.getAll();
        assertEquals(2, cars.size());
        CarDTO dto1 = cars.get(0);
        CarDTO dto2 = cars.get(1);
        assertEquals(1000, dto1.getYear());
        assertEquals("make1", dto1.getMake());
        assertEquals("model1", dto1.getModel());
        assertEquals(1000, dto1.getPrice());

        assertEquals(2000, dto2.getYear());
        assertEquals("make2", dto2.getMake());
        assertEquals("model2", dto2.getModel());
        assertEquals(2000, dto2.getPrice());
    }

    @Test
    public void testGetId_with_invalid_id() {
        Long id = 11231451L;
        CarDTO car = carfacade.getById(id);
        assertNull(car);
    }

    @Test
    public void testGetById_with_valid_id() {
        Long id = 1L;
        CarDTO car = carfacade.getById(id);
        assertEquals(1000,car.getYear());
        assertEquals("make1", car.getMake());
        assertEquals("model1", car.getModel());
        assertEquals(1000,car.getPrice());
    }

    @Test
    public void testGetCount() {
        assertEquals(2, carfacade.getCount(), "Expects two rows in the database");
    }
}
