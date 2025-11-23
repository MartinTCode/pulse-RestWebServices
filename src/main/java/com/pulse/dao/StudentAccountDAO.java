package com.pulse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.pulse.entity.StudentAccountEntity;

public class StudentAccountDAO {
    private final EntityManager em;

    public StudentAccountDAO(EntityManager em) {
        this.em = em;
    }

    // find by student id
    public StudentAccountEntity findByStudentId(String studentId) {
        return em.find(StudentAccountEntity.class, studentId);
    }

    // Find by personal number
    public List<StudentAccountEntity> findAccountsByPersonalNo(String personalNo) {
        TypedQuery<StudentAccountEntity> query = em.createQuery(
                "SELECT sa FROM StudentAccount sa WHERE sa.personalNo = :personalNo",
                StudentAccountEntity.class);
        query.setParameter("personalNo", personalNo);
        return query.getResultList();
    }

}
