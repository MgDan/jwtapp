package org.dandroid.jwtapp.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaInitializer {
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("jwtappPU");
            // Optionally, create and close an EntityManager to trigger schema creation
            emf.createEntityManager().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        // This method can be called to ensure static block runs
    }

    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }
}