package com.pulse.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EntityManagerFactoryProvider class to manage EntityManagerFactory instances.
 */
public class EntityManagerFactoryProvider {
    
    private static final ConcurrentHashMap<String, EntityManagerFactory> factories = new ConcurrentHashMap<>();
    
    public static EntityManagerFactory getFactory(String persistenceUnitName) {
        return factories.computeIfAbsent(persistenceUnitName, Persistence::createEntityManagerFactory);
    }
    
    public static void closeAll() {
        factories.values().forEach(EntityManagerFactory::close);
        factories.clear();
    }
}

