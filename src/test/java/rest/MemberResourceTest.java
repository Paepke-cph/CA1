package rest;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Member;
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


public class MemberResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Member r1, r2;

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
        r1 = new Member("Person1", "11111", "Red");
        r2 = new Member("Person2", "22222", "Green");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Member.Truncate").executeUpdate();
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
    public void testGetByName_with_invalid_name() {
        String name = "This is not an actual name";
        when().get("/groupmembers/name/{name}", name)
                .then()
                .statusCode(200)
                .body("", hasSize(0));
    }

    @Test
    public void testGetByName_with_valid_name() {
        String name = "Person1";
        when().get("groupmembers/name/{name}", name)
                .then()
                .statusCode(200)
                .body("name[0]", equalTo("Person1"), "studentId[0]", equalTo("11111"),  "colorLevel[0]", equalTo("Red"));

    }

    @Test
    public void testGetById_with_invalid_id() {
        Long id = 10313131L;
        when().get("groupmembers/{id}", id)
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetById_with_valid_id() {
        Long id = 1L;
        when().get("groupmembers/{id}", id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Person1"), "studentId", equalTo("11111"),  "colorLevel", equalTo("Red"));
    }

    @Test
    public void testGetAll() {
        given().when().get("/groupmembers/all")
                .then()
                .statusCode(200)
                .body("name", hasItems("Person1","Person2"));
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/groupmembers").then().statusCode(200);
    }
}

