package rest;
/*
 * author benjaminp
 * version 1.0
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facade.MemberFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/groupmembers")
public class MemberResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final MemberFacade FACADE =  MemberFacade.getMemberFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response get() {
        return Response
                .status(Response.Status.OK)
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
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByName(@PathParam("name") String name) {
        return Response.ok(FACADE.getByName(name)).build();

    }
}












