package rest;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Joke;
import org.hamcrest.Matchers;
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

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;


public class JokeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Joke r1, r2;

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
        r1 = new Joke("Joke1" , "Ref1", "Type1");
        r2 = new Joke("Joke2", "Ref2", "Type2");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.Truncate").executeUpdate();
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
    public void testGetRandomJoke() {
        when().get("joke/random")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetById_with_invalid_id(){
        Long id = 131231L;
                when().get("joke/{id}", id)
                        .then()
                        .statusCode(204);
    }

    @Test
    public void testGetById_with_valid_id() {
        Long id = 1L;
        when().get("joke/{id}", id)
                .then()
                .statusCode(200)
                .body("text", equalTo("Joke1"),
                        "reference", equalTo("Ref1"),

                        "type", equalTo("Type1"));
    }

    @Test
    public void testGetAll() {
        given().when().get("joke/all")
                .then()
                .statusCode(200)
                .body("text", hasItems("Joke1","Joke2"),
                        "reference", hasItems("Ref1", "Ref2"),
                        "type", hasItems("Type1", "Type2"));
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/joke").then().statusCode(200);
    }
}
