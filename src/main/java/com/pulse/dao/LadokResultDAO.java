package com.pulse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.pulse.entity.LadokResultEntity;

/**
 * Data Access Object for LadokResultEntity
 */
public class LadokResultDAO {
    private final EntityManager em;

    /**
     * Constructor that initialize EntityManager
     * @param em EntityManager instance
     */
    public LadokResultDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Finds result by its result ID
     * @param resultId The ID of the result to find
     * @return LadokResultEntity instance if found, otherwise null
     */
    public LadokResultEntity findResultById(int resultId) {
        return em.find(LadokResultEntity.class, resultId);
    }

    /**
     * Retrieves all results from the database
     * @return List of LadokResultEntity instances
     */
    public List<LadokResultEntity> findAllResults() {
        TypedQuery<LadokResultEntity> query = em.createQuery("SELECT r FROM Result r", LadokResultEntity.class);
        return query.getResultList();
    }

    /**
     * Finds results by personal number
     * @param personalNo The personal number to search results for
     * @return List of LadokResultEntity instances associated with the given personal number
     */
    public List<LadokResultEntity> findResultsByPersonalNo(String personalNo) {
        TypedQuery<LadokResultEntity> query = em.createQuery(
                "SELECT r FROM Result r WHERE r.personalNo = :personalNo", LadokResultEntity.class);
        query.setParameter("personalNo", personalNo);
        return query.getResultList();
    }

    /**
     * Finds results by course ID and module code
     * @param courseId The course ID to search results for
     * @param moduleCode The module code to search results for
     * @return List of LadokResultEntity instances associated with the given course ID and module code
     */
    public  List<LadokResultEntity> findByCourseIdAndModuleCode(String courseId, String moduleCode) {
        TypedQuery<LadokResultEntity> query = em.createQuery(
            "SELECT r FROM Result r WHERE r.courseId = :courseId AND r.moduleCode = :moduleCode",
            LadokResultEntity.class
        );
        query.setParameter("courseId", courseId);
        query.setParameter("moduleCode", moduleCode);
        return query.getResultList();
    }

    /**
     * Saves a LadokResultEntity to the database
     * @param result The LadokResultEntity instance to save
     */
    public void saveResult(LadokResultEntity result) {
        em.getTransaction().begin();
        em.persist(result); // INSERT into db
        em.getTransaction().commit();
    }

}
