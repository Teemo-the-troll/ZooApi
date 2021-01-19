package cz.educanet.zoo;


import cz.educanet.zoo.data.DAOs.CaretakerDao;
import cz.educanet.zoo.data.entities.CaretakerEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/caretakers")
@Produces(MediaType.APPLICATION_JSON)
public class Caretakers {

    @Inject
    private CaretakerDao caretakerDao;

    /*[GET] /caretakers – vrátí všechny ošetřovatelata přidané v databázi*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(caretakerDao.getAll()).build();
    }

    /*[POST] /caretakers – přidá ošetřovatele*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCaretaker(CaretakerEntity caretaker) {
        Optional<CaretakerEntity> newCaretaker = caretakerDao.save(caretaker);
        if (newCaretaker.isEmpty())
            return Response.status(400).build();
        return Response.ok(newCaretaker.get()).build();
    }

    /*[PUT] /caretakers/{id} – upraví ošetřovatele*/
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editCaretaker(@PathParam("id") String id, CaretakerEntity newCaretaker) {
        Optional<CaretakerEntity> caretaker = caretakerDao.update(newCaretaker, Integer.parseInt(id));
        if (caretaker.isEmpty())
            return Response.status(404).build();
        return Response.ok(caretaker.get()).build();
    }

    /*[DELETE] /caretakers/{id} – smaže ošetřovatele*/
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response yeetCaretaker(@PathParam("id") String id) {
        Optional<CaretakerEntity> caretaker = caretakerDao.delete(Integer.parseInt(id));
        if (caretaker.isEmpty())
            return Response.status(404).build();
        return Response.ok(caretaker.get()).build();
    }

    /*[GET] /caretakers/{id} – vrátí konkrétní ošetřovatele*/
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCaretaker(@PathParam("id") String id) {
        Optional<CaretakerEntity> caretaker = caretakerDao.get(Integer.parseInt(id));
        if (caretaker.isEmpty())
            return Response.status(404).build();
        return Response.ok(caretaker.get()).build();
    }
}
