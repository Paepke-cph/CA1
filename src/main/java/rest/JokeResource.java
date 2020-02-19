package rest;
/*
 * author benjaminp
 * version 1.0
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.dto.JokeDTO;
import facade.JokeFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/joke")
public class JokeResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final JokeFacade FACADE = JokeFacade.getJokeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response get() {
        return Response
                .ok()
                .build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response
                .ok(FACADE.getAll())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) {
        JokeDTO joke = FACADE.getById(id);
        if(joke != null) {
            return Response
                    .ok(joke)
                    .build();
        } else {
            return Response
                    .noContent()
                    .build();
        }
    }

    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomJoke() {
        return Response
                .ok(FACADE.getRandomJoke())
                .build();
    }

    @GET
    @Path("/populate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response populate() {
        FACADE.populate();
        return Response
                .ok()
                .build();
    }
}
