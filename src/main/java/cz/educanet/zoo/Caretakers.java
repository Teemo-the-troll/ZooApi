package cz.educanet.zoo;


import com.google.gson.JsonSyntaxException;
import cz.educanet.zoo.data.CaretakerEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/caretakers")
@Produces(MediaType.APPLICATION_JSON)
public class Caretakers {
    @Inject
    private DatabaseManager datManager;

    /*[GET] /caretakers – vrátí všechny ošetřovatelata přidané v databázi*/
    @GET
    public Response getAll() {
        return datManager.getAllFromDat(CaretakerEntity.class).build();
    }

    /*[POST] /caretakers – přidá ošetřovatele*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public Response addCaretaker(String rawCaretaker) {
        Caretaker caretaker;
        try {
            caretaker = datManager.getGson().fromJson(rawCaretaker, Caretaker.class);
        } catch (JsonSyntaxException e) {
            return Response.status(400).type(MediaType.TEXT_PLAIN).entity("Invalid format: ".concat(e.getMessage())).build();
        }
        return datManager.addToDat(caretaker).build();
    }

    /*[PUT] /caretakers/{id} – upraví ošetřovatele*/
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editCaretaker(@PathParam("id") String id, String oldCaretaker) {
        return datManager.edit(Integer.parseInt(id), datManager.getGson().fromJson(oldCaretaker, CaretakerEntity.class)).build();
    }


    /*[DELETE] /caretakers/{id} – smaže ošetřovatele*/
    @DELETE
    @Path("{id}")
    public Response yeetCaretaker(@PathParam("id") String id) {
        return datManager.deleteFromDat(CaretakerEntity.class, Integer.parseInt(id)).build();
    }

    /*[GET] /caretakers/{id} – vrátí konkrétní ošetřovatele*/
    @GET
    @Path("{id}")
    public Response getCaretaker(@PathParam("id") String id) {
        return datManager.findById(CaretakerEntity.class, Integer.parseInt(id)).build();
    }
}
