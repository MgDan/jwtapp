package org.dandroid.jwtapp.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductRepository {

    @PersistenceContext(unitName = "jwtAppPU")
    private EntityManager em;

    @Transactional
    public void save(Product product) {
        em.persist(product);
    }

}