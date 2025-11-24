package com.pulse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.pulse.entity.LadokResultEntity;

public class LadokResultDAO {
    private final EntityManager em;

    public LadokResultDAO(EntityManager em) {
        this.em = em;
    }

    // find by result id
    public LadokResultEntity findResultById(int resultId) {
        return em.find(LadokResultEntity.class, resultId);
    }

    // find all results
    public List<LadokResultEntity> findAllResults() {
        TypedQuery<LadokResultEntity> query = em.createQuery("SELECT r FROM Result r", LadokResultEntity.class);
        return query.getResultList();
    }

    // Find result by personal number - NOT SURE IF NEEDED
    public List<LadokResultEntity> findResultsByPersonalNo(String personalNo) {
        TypedQuery<LadokResultEntity> query = em.createQuery(
                "SELECT r FROM Result r WHERE r.personalNo = :personalNo", LadokResultEntity.class);
        query.setParameter("personalNo", personalNo);
        return query.getResultList();
    }

    // Find results by courseId and moduleCode
    public  List<LadokResultEntity> findByCourseIdAndModuleCode(String courseId, String moduleCode) {
        TypedQuery<LadokResultEntity> query = em.createQuery(
            "SELECT r FROM Result r WHERE r.courseId = :courseId AND r.moduleCode = :moduleCode",
            LadokResultEntity.class
        );
        query.setParameter("courseId", courseId);
        query.setParameter("moduleCode", moduleCode);
        return query.getResultList();
    }

    // Save result
    public void saveResult(LadokResultEntity result) {
        em.getTransaction().begin();
        em.persist(result); // INSERT into db
        em.getTransaction().commit();
    }

}
