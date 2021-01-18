package cz.educanet.zoo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@RequestScoped
public class CritManager {
    @Inject
    private EmManager emManager;

    public <T> TypedQuery<T> getTypedQuery(Class<T> c) {
        EntityManager em = emManager.getEm();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(c);
        Root<T> rootEntry = cq.from(c);
        CriteriaQuery<T> all = cq.select(rootEntry);
        return em.createQuery(all);
    }

}
