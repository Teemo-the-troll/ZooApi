package cz.educanet.zoo;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class HibernateManager {
    private final EntityManagerFactory em = Persistence.createEntityManagerFactory("zoo");

    @PreDestroy
    public void preDestroy() {
        em.close();
    }

    public EntityManagerFactory getEmFac() {
        return em;
    }


}
