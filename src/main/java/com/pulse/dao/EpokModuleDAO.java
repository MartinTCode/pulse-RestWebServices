package com.pulse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.pulse.entity.EpokModuleEntity;

public class EpokModuleDAO {
    private final EntityManager em;

    //Constructor
    public EpokModuleDAO(EntityManager em){
        this.em = em;
    }

    //find module by module id
    public EpokModuleEntity findModuleById(int moduleId){
        return em.find(EpokModuleEntity.class, moduleId);
    }
    
    //find all modules
    public List<EpokModuleEntity> findAllModules(){
        TypedQuery<EpokModuleEntity> query = em.createQuery("SELECT m FROM EpokModuleEntity m", EpokModuleEntity.class);
        return query.getResultList();
    }

    //find modules by course id
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
