package org.dandroid.jwtapp.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.dandroid.jwtapp.config.JpaInitializer;
import org.dandroid.jwtapp.entity.User;


public class UserRepository {


    public User findByUsername(String username) {
        EntityManager entityManager = JpaInitializer.getEntityManagerFactory().createEntityManager();
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst().orElse(null);
    }
}