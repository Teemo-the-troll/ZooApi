package cz.educanet.zoo;

import com.google.gson.Gson;
import cz.educanet.zoo.data.AnimalEntity;
import cz.educanet.zoo.data.CaretakerEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class DatabaseManager {
    @Inject
    EmManager em;

    @Inject
    CritManager cm;

    Gson gson = new Gson();

    public DatabaseManager() {
    }

    public Gson getGson() {
        return this.gson;
    }

    public <T> Response.ResponseBuilder getAllFromDat(Class<T> c) {
        return Response.ok(gson.toJson(cm.getTypedQuery(c).getResultList()));
    }

    public Response.ResponseBuilder edit(Integer id, CaretakerEntity updated) {
        EntityManager tempem = em.getEm();
        CaretakerEntity proxy = tempem.getReference(CaretakerEntity.class, id);
        tempem.getTransaction().begin();
        CaretakerEntity cm = new CaretakerEntity(proxy.getId(), updated);
        tempem.merge(cm);
        tempem.getTransaction().commit();
        return Response.ok(gson.toJson(cm));
    }

    public Response.ResponseBuilder edit(Integer id, AnimalEntity updated) {
        EntityManager tempem = em.getEm();
        AnimalEntity proxy = tempem.getReference(AnimalEntity.class, id);
        tempem.getTransaction().begin();
        AnimalEntity am = new AnimalEntity(proxy.getId(), updated);
        tempem.merge(am);
        tempem.getTransaction().commit();
        return Response.ok(gson.toJson(am));
    }

    public <T> Response.ResponseBuilder findById(Class<T> c, Integer id) {
        Object result = this.find(c, id);
        return Response.ok(gson.toJson(result));
    }

    private <T> Object find(Class<T> c, Integer id) {
        EntityManager tempem = em.getEm();
        Object result = tempem.find(c, id);
        if (result == null)
            return Response.status(404).build();
        else
            return result;
    }

    public <T> Response.ResponseBuilder deleteFromDat(Class<T> c, Integer id) {
        Object target = this.find(c, id);
        EntityManager tempem = em.getEm();
        tempem.getTransaction().begin();
        tempem.remove(target);
        tempem.getTransaction().commit();
        return Response.ok(gson.toJson(target));
    }

    public <T> Response.ResponseBuilder sendToDat(T o) {
        EntityManager tempem = em.getEm();
        tempem.getTransaction().begin();
        tempem.persist(o);
        tempem.getTransaction().commit();
        tempem.close();
        return Response.ok(gson.toJson(o));
    }

    public Response.ResponseBuilder addToDat(Caretaker ct) {
        return this.sendToDat(new CaretakerEntity(ct));
    }

    public Response.ResponseBuilder addToDat(Animal an) {
        return this.sendToDat(new AnimalEntity(an));
    }

}
