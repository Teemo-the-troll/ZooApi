package cz.educanet.zoo;

import com.google.gson.JsonSyntaxException;
import cz.educanet.zoo.data.AnimalEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/animals")
@Produces(MediaType.APPLICATION_JSON)
public class Animals {


    @Inject
    private DatabaseManager datManager;

    /*[GET] /animals – vrátí všechny zvířata přidané v „databázi“*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return datManager.getAllFromDat(AnimalEntity.class).build();
    }

    /*[POST] /animals – přidá zvíře*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public Response addAnimal(String rawAnimal) {
        Animal animal;
        try {
            animal = datManager.getGson().fromJson(rawAnimal, Animal.class);
        } catch (JsonSyntaxException e) {
            return Response.status(400).type(MediaType.TEXT_PLAIN).entity("Invalid format: ".concat(e.getMessage())).build();
        }
        return datManager.addToDat(animal).build();
    }

    /*[PUT] /animals/{id} – upraví zvíře*/
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}")
    public Response editAnimal(@PathParam("id") String id, String oldAnimal) {
        return datManager.edit(Integer.parseInt(id), datManager.getGson().fromJson(oldAnimal, AnimalEntity.class)).build();
    }

    /*[DELETE] /animals/{id} – smaže zvíře*/
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}")
    public Response yeetAnimal(@PathParam("id") String id) {
        return datManager.deleteFromDat(AnimalEntity.class, Integer.parseInt(id)).build();
    }

    /*[GET] /animals/{id} – vrátí konkrétní zvíře*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getAnimal(@PathParam("id") String id) {
        return datManager.findById(AnimalEntity.class, Integer.parseInt(id)).build();
    }
}
