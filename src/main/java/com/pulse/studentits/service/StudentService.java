package com.pulse.studentits.service;

import com.pulse.studentits.dao.StudentAccountDAO;
import com.pulse.studentits.entity.StudentAccount;

public class StudentService {

    private final StudentAccountDAO studentAccountDAO;

    public StudentService(StudentAccountDAO studentAccountDAO) {
        this.studentAccountDAO = studentAccountDAO;
    }

    public StudentAccount getStudentById(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        return studentAccountDAO.findByStudentId(studentId);
    }

    public String getPersonalNumber(String studentId) {
        StudentAccount account = getStudentById(studentId);
        if (account == null) {
            throw new IllegalArgumentException("Student not found");
        }
        return account.getPersonalNo();
    }
}

