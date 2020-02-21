package rest;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Car;
import utils.EMF_Creator;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;

import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;


public class CarResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Car r1, r2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);

        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        r1 = new Car(1000,"make1","model1",1000,"Owner1");
        r2 = new Car(2000,"make2","model2",2000,"Owner2");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Car.Truncate").executeUpdate();
            em.persist(r1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(r2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        given().get("car/{id}", id)
                .then()
                .statusCode(200)
                .body("year", equalTo(1000),
                        "make", equalTo("make1"),
                        "model", equalTo("model1"),
                        "price", equalTo(1000));
    }

    @Test
    public void testGetAll() {
        given().get("car/all")
                .then()
                .statusCode(200)
                .body("year", hasItems(1000,2000),
                        "make", hasItems("make1","make2"),
                        "model", hasItems("model1", "model2"),
                        "price", hasItems(1000,2000));
    }

    @Test
    public void testPopulate() {
        given().get("car/populate")
                .then()
                .statusCode(200);
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/car").then().statusCode(200);
    }
}
