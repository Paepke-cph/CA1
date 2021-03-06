package rest;
/*
 * author benjaminp
 * version 1.0
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.dto.MemberDTO;
import facade.MemberFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
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
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByName(@PathParam("name") String name) {
        return Response.ok(FACADE.getByName(name)).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        MemberDTO member = FACADE.getById(id);
        if(member != null) {
            return Response.ok(member).build();
        } else {
            return Response.status(204).build();
        }
    }

    @GET
    @Path("/populate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response populate() {
        FACADE.populate();
        return Response.ok().build();
    }
}












