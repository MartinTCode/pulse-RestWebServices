package com.pulse.epok.dao;

import com.pulse.epok.entity.Module;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ModuleDAO {
    private final EntityManager em;

    //Constructor
    public ModuleDAO(EntityManager em){
        this.em = em;
    }

    //find module by module id
    public Module findModuleById(int moduleId){
        return em.find(Module.class, moduleId);
    }
    
    //find all modules
    public List<Module> findAllModules(){
        TypedQuery<Module> query = em.createQuery("SELECT m FROM Module m", Module.class);
        return query.getResultList();
    }

    //find modules by course id
    public List<Module> findModulesByCourseId(String courseId) {
        TypedQuery<Module> query =
                em.createQuery(
                        "SELECT m FROM Module m WHERE m.course.courseId = :courseId",
                        Module.class
                );
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

    
}
