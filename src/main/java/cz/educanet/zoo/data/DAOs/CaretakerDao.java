package cz.educanet.zoo.data.DAOs;

import cz.educanet.zoo.EmManager;
import cz.educanet.zoo.data.entities.CaretakerEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CaretakerDao implements Dao<CaretakerEntity> {
    @Inject
    private EmManager emManager;

    @Override
    public Optional<CaretakerEntity> get(int id) {
        CaretakerEntity result = emManager.getEm().find(CaretakerEntity.class, id);
        return Optional.ofNullable(result);
    }

    @Override
    public List<CaretakerEntity> getAll() {
        EntityManager em = emManager.getEm();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CaretakerEntity> cq = cb.createQuery(CaretakerEntity.class);
        Root<CaretakerEntity> rootEntry = cq.from(CaretakerEntity.class);
        CriteriaQuery<CaretakerEntity> all = cq.select(rootEntry);
        return em.createQuery(all).getResultList();
    }

    @Override
    public Optional<CaretakerEntity> save(CaretakerEntity caretakerEntity) {
        EntityManager tempem = emManager.getEm();
        tempem.getTransaction().begin();
        tempem.persist(caretakerEntity);
        tempem.getTransaction().commit();
        tempem.close();
        return Optional.ofNullable(caretakerEntity);
    }

    @Override
    public Optional<CaretakerEntity> update(CaretakerEntity updated, int id) {
        EntityManager tempem = emManager.getEm();
        CaretakerEntity proxy = tempem.getReference(CaretakerEntity.class, id);
        if (proxy == null)
            return Optional.empty();
        tempem.getTransaction().begin();
        tempem.merge(new CaretakerEntity(proxy.getId(), updated));
        tempem.getTransaction().commit();
        return Optional.of(updated);
    }


    @Override
    public Optional<CaretakerEntity> delete(int id) {
        Optional<CaretakerEntity> target = get(id);
        if (target.isEmpty())
            return target;
        EntityManager tempem = emManager.getEm();
        tempem.getTransaction().begin();
        tempem.remove(target.get());
        tempem.getTransaction().commit();
        return target;
    }
}
