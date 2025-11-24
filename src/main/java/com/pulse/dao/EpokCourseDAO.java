package com.pulse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.pulse.entity.EpokCourseEntity;

public class EpokCourseDAO {
    private final EntityManager em;

    //Constructor
    public EpokCourseDAO(EntityManager em){
        this.em = em;
    }

    //find course by course id
    public EpokCourseEntity findCourseById(String courseId){
        return em.find(EpokCourseEntity.class, courseId);
    }
    
    //find all courses
    public List<EpokCourseEntity> findAllCourses(){
        TypedQuery<EpokCourseEntity> query = em.createQuery("SELECT c FROM Course c", EpokCourseEntity.class);
        return query.getResultList();
    }

    
}
