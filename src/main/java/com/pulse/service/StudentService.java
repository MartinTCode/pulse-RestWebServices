package com.pulse.service;

import com.pulse.dao.StudentAccountDAO;
import com.pulse.entity.StudentAccountEntity;

public class StudentService {

    private final StudentAccountDAO studentAccountDAO;

    public StudentService(StudentAccountDAO studentAccountDAO) {
        this.studentAccountDAO = studentAccountDAO;
    }

    public StudentAccountEntity getStudentById(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        return studentAccountDAO.findByStudentId(studentId);
    }

    public String getPersonalNumber(String studentId) {
        StudentAccountEntity account = getStudentById(studentId);
        if (account == null) {
            throw new IllegalArgumentException("Student not found");
        }
        return account.getPersonalNo();
    }
}

