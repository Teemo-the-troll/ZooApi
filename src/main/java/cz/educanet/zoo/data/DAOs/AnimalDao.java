package cz.educanet.zoo.data.DAOs;

import cz.educanet.zoo.EmManager;
import cz.educanet.zoo.data.entities.AnimalEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AnimalDao implements Dao<AnimalEntity> {
    @Inject
    private EmManager emManager;


    @Override
    public Optional<AnimalEntity> get(int id) {
        AnimalEntity result = emManager.getEm().find(AnimalEntity.class, id);
        return Optional.ofNullable(result);
    }

    @Override
    public List<AnimalEntity> getAll() {
        EntityManager em = emManager.getEm();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AnimalEntity> cq = cb.createQuery(AnimalEntity.class);
        Root<AnimalEntity> rootEntry = cq.from(AnimalEntity.class);
        CriteriaQuery<AnimalEntity> all = cq.select(rootEntry);
        return em.createQuery(all).getResultList();
    }

    @Override
    public Optional<AnimalEntity> save(AnimalEntity animalEntity) {
        EntityManager tempem = emManager.getEm();
        tempem.getTransaction().begin();
        tempem.persist(animalEntity);
        tempem.getTransaction().commit();
        tempem.close();
        return Optional.ofNullable(animalEntity);
    }

    @Override
    public Optional<AnimalEntity> update(AnimalEntity updated, int id) {
        EntityManager tempem = emManager.getEm();
        AnimalEntity proxy = tempem.getReference(AnimalEntity.class, id);
        if (proxy == null)
            return Optional.empty();
        tempem.getTransaction().begin();
        tempem.merge(new AnimalEntity(proxy.getId(), updated));
        tempem.getTransaction().commit();
        return Optional.ofNullable(updated);
    }

    @Override
    public Optional<AnimalEntity> delete(int id) {
        Optional<AnimalEntity> target = get(id);
        if (target.isEmpty())
            return target;
        EntityManager tempem = emManager.getEm();
        tempem.getTransaction().begin();
        tempem.remove(target.get());
        tempem.getTransaction().commit();
        return target;
    }
}
