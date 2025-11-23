package com.pulse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.pulse.entity.StudentitsStudentAccountEntity;

public class StudentitsStudentAccountDAO {
    private final EntityManager em;

    public StudentitsStudentAccountDAO(EntityManager em) {
        this.em = em;
    }

    // find by student id
    public StudentitsStudentAccountEntity findByStudentId(String studentId) {
        return em.find(StudentitsStudentAccountEntity.class, studentId);
    }

    // Find by personal number
    public List<StudentitsStudentAccountEntity> findAccountsByPersonalNo(String personalNo) {
        TypedQuery<StudentitsStudentAccountEntity> query = em.createQuery(
                "SELECT sa FROM StudentAccount sa WHERE sa.personalNo = :personalNo",
                StudentitsStudentAccountEntity.class);
        query.setParameter("personalNo", personalNo);
        return query.getResultList();
    }

}
