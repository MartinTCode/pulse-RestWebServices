package com.pulse.service;

import com.pulse.dao.StudentitsStudentAccountDAO;
import com.pulse.entity.StudentitsStudentAccountEntity;

/**
 * Service that encapsulates lookup logic for Student accounts from the Studentits
 * system. 
 * Delegates persistence operations to the DAO.
 */
public class StudentitsStudentService {

    private final StudentitsStudentAccountDAO studentAccountDAO;

    /**
     * Constructor that initializes the StudentitsStudentService with the given StudentitsStudentAccountDAO.
     * @param studentAccountDAO StudentitsStudentAccountDAO instance
     */
    public StudentitsStudentService(StudentitsStudentAccountDAO studentAccountDAO) {
        this.studentAccountDAO = studentAccountDAO;
    }

    /**
     * Retrieves a student account by student ID.
     * @param studentId The ID of the student to retrieve
     * @return StudentitsStudentAccountEntity instance if found, otherwise null
     * @throws IllegalArgumentException if studentId is null or empty
     */
    public StudentitsStudentAccountEntity getStudentById(String studentId) {
        // Validate input early: null or blank student IDs are rejected.
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        // Delegate lookup to DAO. Returns null if no matching student exists.
        return studentAccountDAO.findByStudentId(studentId);
    }

    /**
     * Retrieves the personal number for a given student ID.
     * @param studentId The ID of the student whose personal number to retrieve
     * @return The personal number associated with the student ID
     * @throws IllegalArgumentException if studentId is null/empty or student not found
     */
    public String getPersonalNumber(String studentId) {
        StudentitsStudentAccountEntity account = getStudentById(studentId);

        // If DAO returned null (no matching student), surface a clear exception.
        if (account == null) {
            throw new IllegalArgumentException("Student not found");
        }
        // Return the personal number from the account entity.
        return account.getPersonalNo();
    }
}
