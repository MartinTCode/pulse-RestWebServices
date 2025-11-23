package com.pulse.service;

import com.pulse.dao.StudentitsStudentAccountDAO;
import com.pulse.entity.StudentitsStudentAccountEntity;

public class StudentitsStudentService {

    private final StudentitsStudentAccountDAO studentAccountDAO;

    public StudentitsStudentService(StudentitsStudentAccountDAO studentAccountDAO) {
        this.studentAccountDAO = studentAccountDAO;
    }

    public StudentitsStudentAccountEntity getStudentById(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        return studentAccountDAO.findByStudentId(studentId);
    }

    public String getPersonalNumber(String studentId) {
        StudentitsStudentAccountEntity account = getStudentById(studentId);
        if (account == null) {
            throw new IllegalArgumentException("Student not found");
        }
        return account.getPersonalNo();
    }
}

