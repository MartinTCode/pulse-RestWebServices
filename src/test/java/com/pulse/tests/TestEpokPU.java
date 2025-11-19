package com.pulse.tests;
import com.pulse.config.EntityManagerFactoryProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class TestEpokPU {

    public static void main(String[] args) {
        System.out.println("Startar EpokPU-test...");

        EntityManagerFactory emf = EntityManagerFactoryProvider.getFactory("epokPU");
        EntityManager em = emf.createEntityManager();

        em.close();

        System.out.println("EpokPU-test klart, utan fel!");

    }
    
}
