package com.pulse.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EntityManagerFactoryProvider {

    private static final Map<String, EntityManagerFactory> factories = new HashMap<>();

    public static EntityManagerFactory getFactory(String puName) {
        return factories.computeIfAbsent(puName, Persistence::createEntityManagerFactory);
    }
    
}

