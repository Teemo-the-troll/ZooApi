package cz.educanet.zoo;

import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@RequestScoped
public class EmManager {
    @Inject
    private HibernateManager hm;

    private EntityManager em = null;

    public EntityManager getEm() {
        if (em == null)
            em = hm.getEmFac().createEntityManager();
        return em;
    }

    @PreDestroy
    public void preDestroy() {
        em.close();
    }
}
