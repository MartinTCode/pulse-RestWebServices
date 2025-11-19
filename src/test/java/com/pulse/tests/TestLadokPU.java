package com.pulse.tests;
import com.pulse.config.EntityManagerFactoryProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class TestLadokPU {
    public static void main(String[] args) {
        System.out.println("Startar LadokPU-test...");

        EntityManagerFactory emf = EntityManagerFactoryProvider.getFactory("ladokPU");
        EntityManager em = emf.createEntityManager();

        em.close();

        System.out.println("LadokPU-test klart, utan fel!");

    }
    
}
