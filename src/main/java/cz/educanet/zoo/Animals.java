package cz.educanet.zoo;

import cz.educanet.zoo.data.DAOs.AnimalDao;
import cz.educanet.zoo.data.entities.AnimalEntity;
import cz.educanet.zoo.data.entities.CaretakerEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/animals")
@Produces(MediaType.APPLICATION_JSON)
public class Animals {

    @Inject
    private AnimalDao animalDao;

    /*[GET] /animals – vrátí všechny zvířata přidané v „databázi“*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(animalDao.getAll()).build();
    }

    /*[GET] /animals/{id} – vrátí konkrétní zvíře*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getAnimal(@PathParam("id") String id) {
        Optional<AnimalEntity> animal = animalDao.get(Integer.parseInt(id));
        if (animal.isEmpty())
            return Response.status(404).build();
        return Response.ok(animal.get()).build();

    }

    /*[POST] /animals – přidá zvíře*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAnimal(AnimalEntity rawAnimal) {
        Optional<AnimalEntity> animal = animalDao.save(rawAnimal);
        if (animal.isEmpty())
            return Response.status(400).build();
        return Response.ok(animal.get()).build();

    }

    /*[PUT] /animals/{id} – upraví zvíře*/
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response editAnimal(@PathParam("id") String id, AnimalEntity newAnimal) {
        Optional<AnimalEntity> animal = animalDao.update(newAnimal, Integer.parseInt(id));
        if (animal.isEmpty())
            return Response.status(404).build();
        return Response.ok(animal.get()).build();
    }

    /*[DELETE] /animals/{id} – smaže zvíře*/
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response yeetAnimal(@PathParam("id") String id) {
        Optional<AnimalEntity> animal = animalDao.delete(Integer.parseInt(id));
        if (animal.isEmpty())
            return Response.status(404).build();
        return Response.ok(animal.get()).build();
    }


}
