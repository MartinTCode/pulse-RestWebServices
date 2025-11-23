package com.pulse.service;

import com.pulse.dao.StudentitsStudentAccountDAO;
import com.pulse.entity.StudentitsStudentAccountEntity;

public class StudentitsStudentService {

    private final StudentitsStudentAccountDAO studentAccountDAO;

    /**
     * Service that encapsulates lookup logic for Student accounts from the Studentits
     * system. This class delegates persistence operations to a DAO so it can be
     * unit-tested with a stub or mock DAO.
     *
     * Contract:
     * - Inputs: studentId (non-null, non-blank)
     * - Outputs: StudentitsStudentAccountEntity when found
     * - Errors: throws IllegalArgumentException for invalid input or when a student
     *           is not found (in getPersonalNumber)
     */
    public StudentitsStudentService(StudentitsStudentAccountDAO studentAccountDAO) {
        this.studentAccountDAO = studentAccountDAO;
    }

    public StudentitsStudentAccountEntity getStudentById(String studentId) {
        // Validate input early: null or blank student IDs are rejected.
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        // Delegate lookup to DAO. May return null if no matching student exists.
        return studentAccountDAO.findByStudentId(studentId);
    }

    public String getPersonalNumber(String studentId) {
        // Reuse getStudentById for consistent validation and lookup.
        StudentitsStudentAccountEntity account = getStudentById(studentId);
        // If DAO returned null (no matching student), surface a clear exception.
        if (account == null) {
            throw new IllegalArgumentException("Student not found");
        }
        // Return the personal number (identifier) from the account entity.
        return account.getPersonalNo();
    }
}
