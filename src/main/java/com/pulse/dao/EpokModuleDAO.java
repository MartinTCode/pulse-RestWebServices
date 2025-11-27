package com.pulse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.pulse.entity.EpokModuleEntity;

/**
 * Data Access Object for EpokModuleEntity
 */
public class EpokModuleDAO {
    private final EntityManager em;

    /**
     * Constructor that initialize EntityManager
     * @param em EntityManager instance
     */
    public EpokModuleDAO(EntityManager em){
        this.em = em;
    }

    /**
     * Finds moduler by its module ID
     * @param moduleId The ID of the module to find
     * @return EpokModuleEntity instance if found, otherwise null
     */
    public EpokModuleEntity findModuleById(int moduleId){
        return em.find(EpokModuleEntity.class, moduleId);
    }
    
    /**
     * Retrieves all modules from the database
     * @return List of EpokModuleEntity instances
     */
    public List<EpokModuleEntity> findAllModules(){
        TypedQuery<EpokModuleEntity> query = em.createQuery("SELECT m FROM EpokModuleEntity m", EpokModuleEntity.class);
        return query.getResultList();
    }

    /**
     * Finds modules by their course ID
     * @param courseId The ID of the course whose modules to find
     * @return List of EpokModuleEntity instances associated with the given course ID
     */
    public List<EpokModuleEntity> findModulesByCourseId(String courseId) {
        TypedQuery<EpokModuleEntity> query =
                em.createQuery(
                        "SELECT m FROM EpokModuleEntity m WHERE m.course.courseId = :courseId",
                        EpokModuleEntity.class
                );
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

    
}
