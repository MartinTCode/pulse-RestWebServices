package com.pulse.service;

import com.pulse.dao.LadokResultDAO;
import com.pulse.entity.LadokResultEntity;

import java.time.LocalDate;

/**
 * Service responsible for registering exam results in the Ladok system.
 * The service validates input and delegates persistence to an injected DAO.
 */
public class LadokResultService {

    private final LadokResultDAO resultDAO;

    /**
     * Constructor that initializes the LadokResultService with the given LadokResultDAO.
     * @param resultDAO LadokResultDAO instance
     */
    public LadokResultService(LadokResultDAO resultDAO) {
        this.resultDAO = resultDAO;
    }

    /**
     * Registers a new exam result in the Ladok system.
     * @param personalNo The personal number of the student
     * @param courseId The ID of the course
     * @param moduleCode The code of the module
     * @param examDate The date of the exam
     * @param grade The grade received
     * @return The registered LadokResultEntity instance
     * @throws IllegalArgumentException if any required input is null or invalid
     */
    public LadokResultEntity registerResult(String personalNo, String courseId, String moduleCode, LocalDate examDate, String grade) {
        // Validate required inputs
        if (personalNo == null || personalNo.isBlank())
            throw new IllegalArgumentException("personalNo is required");

        if (courseId == null || courseId.isBlank())
            throw new IllegalArgumentException("courseId is required");

        if (moduleCode == null || moduleCode.isBlank())
            throw new IllegalArgumentException("moduleCode is required");

        if (examDate == null)
            throw new IllegalArgumentException("examDate is required");

        if (grade == null || grade.isBlank())
            throw new IllegalArgumentException("grade is required");

        // Build the result entity and persist it via the DAO
        LadokResultEntity result = new LadokResultEntity(personalNo, courseId, moduleCode, examDate, grade);
        resultDAO.saveResult(result);

        // Return the saved entity
        return result;
    }
}