package rest;
/*
 * author benjaminp
 * version 1.0
 */

import facade.CarFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/car")
public class CarResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final CarFacade FACADE = CarFacade.getCarFacade(EMF);

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
    public Response getById(@PathParam("id") Long id) {
        return Response
                .ok(FACADE.getById(id))
                .build();
    }

    @GET
    @Path("/populate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response populate() {
        FACADE.populate();
        return Response
                .status(200)
                .build();
    }
}
