package com.pulse.epok.dao;

import com.pulse.epok.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CourseDAO {
    private final EntityManager em;

    //Constructor
    public CourseDAO(EntityManager em){
        this.em = em;
    }

    //find course by course id
    public Course findCourseById(String courseId){
        return em.find(Course.class, courseId);
    }
    
    //find all courses
    public List<Course> findAllCourses(){
        TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c", Course.class);
        return query.getResultList();
    }

    
}
