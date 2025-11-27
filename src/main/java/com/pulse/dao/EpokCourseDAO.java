package com.pulse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.pulse.entity.EpokCourseEntity;

/**
 * Data Access Object for EpokCourseEntity
 */
public class EpokCourseDAO {
    private final EntityManager em;

    /**
     * Constructor that initialize EntityManager
     * @param em EntityManager instance
     */
    public EpokCourseDAO(EntityManager em){
        this.em = em;
    }

    /**
     * Find course by its course ID
     * @param courseId The ID of the course to find
     * @return EpokCourseEntity instance if found, otherwise null
     */
    public EpokCourseEntity findCourseById(String courseId){
        return em.find(EpokCourseEntity.class, courseId);
    }
    
    /**
     * Retrieve all courses from the database
     * @return List of EpokCourseEntity instances
     */
    public List<EpokCourseEntity> findAllCourses(){
        TypedQuery<EpokCourseEntity> query = em.createQuery("SELECT c FROM Course c", EpokCourseEntity.class);
        return query.getResultList();
    }  
}
