package com.pulse.ladok.dao;

import com.pulse.ladok.entity.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ResultDAO {
    private final EntityManager em;

    public ResultDAO(EntityManager em) {
        this.em = em;
    }

    // find by result id
    public Result findResultById(int resultId) {
        return em.find(Result.class, resultId);
    }

    // find all results
    public List<Result> findAllResults() {
        TypedQuery<Result> query = em.createQuery("SELECT r FROM Result r", Result.class);
        return query.getResultList();
    }

    // Find result by personal number - NOT SURE IF NEEDED
    public List<Result> findResultsByPersonalNo(String personalNo) {
        TypedQuery<Result> query = em.createQuery(
                "SELECT r FROM Result r WHERE r.personalNo = :personalNo", Result.class);
        query.setParameter("personalNo", personalNo);
        return query.getResultList();
    }

    // Find results by courseId and moduleCode
    public  List<Result> findByCourseIdAndModuleCode(String courseId, String moduleCode) {
        TypedQuery<Result> query = em.createQuery(
            "SELECT r FROM Result r WHERE r.courseId = :courseId AND r.moduleCode = :moduleCode",
            Result.class
        );
        query.setParameter("courseId", courseId);
        query.setParameter("moduleCode", moduleCode);
        return query.getResultList();
    }

    // Save result
    public void saveResult(Result result) {
        em.getTransaction().begin();
        em.persist(result); // INSERT into db
        em.getTransaction().commit();
    }

}
