package com.pulse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.pulse.entity.StudentitsStudentAccountEntity;

/**
 * Data Access Object for StudentitsStudentAccountEntity
 */
public class StudentitsStudentAccountDAO {
    private final EntityManager em;

    /**
     * Constructor that initialize EntityManager
     * @param em EntityManager instance
     */
    public StudentitsStudentAccountDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Finds student account by student ID
     * @param studentId The student ID to search for
     * @return StudentitsStudentAccountEntity instance if found, otherwise null
     */
    public StudentitsStudentAccountEntity findByStudentId(String studentId) {
        return em.find(StudentitsStudentAccountEntity.class, studentId);
    }

    /**
     * Finds student accounts by personal number
     * @param personalNo The personal number to search accounts for
     * @return List of StudentitsStudentAccountEntity instances associated with the given personal number
     */
    public List<StudentitsStudentAccountEntity> findAccountsByPersonalNo(String personalNo) {
        TypedQuery<StudentitsStudentAccountEntity> query = em.createQuery(
                "SELECT sa FROM StudentitsStudentAccountEntity sa WHERE sa.personalNo = :personalNo",
                StudentitsStudentAccountEntity.class);
        query.setParameter("personalNo", personalNo);
        return query.getResultList();
    }
}
