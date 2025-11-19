package com.pulse.tests;

import com.pulse.config.EntityManagerFactoryProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class TestStudentitsPU {

    public static void main(String[] args) {
        System.out.println("Startar StudentitsPU-test...");

        EntityManagerFactory emf = EntityManagerFactoryProvider.getFactory("studentitsPU");
        EntityManager em = emf.createEntityManager();

        em.close();

        System.out.println("studentitsPU-test klart, utan fel!");

    }

}
