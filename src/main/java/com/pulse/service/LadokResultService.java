package com.pulse.service;

import com.pulse.dao.LadokResultDAO;
import com.pulse.entity.LadokResultEntity;

import java.time.LocalDate;

public class LadokResultService {

    private final LadokResultDAO resultDAO;

    /**
     * Service responsible for registering exam results in the Ladok system.
     *
     * The service validates input at the boundary and delegates persistence to
     * an injected DAO. This separation makes the class easy to unit-test with
     * a stub DAO and keeps validation logic centralized.
     *
     * Contract:
     * - Inputs: personalNo, courseId, moduleCode, examDate and grade (all required)
     * - Outputs: a persisted LadokResultEntity
     * - Errors: IllegalArgumentException for any missing/blank required field
     */
    public LadokResultService(LadokResultDAO resultDAO) {
        this.resultDAO = resultDAO;
    }

    public LadokResultEntity registerResult(String personalNo, String courseId, String moduleCode, LocalDate examDate, String grade) {
        // Validate required inputs. Using explicit checks keeps error messages
        // clear and avoids creating invalid entities.
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

        // Build the result entity and persist it via the DAO. The DAO handles
        // underlying persistence concerns (JPA/entity manager), so the service
        // remains focused on business logic and validation.
        LadokResultEntity result = new LadokResultEntity(personalNo, courseId, moduleCode, examDate, grade);
        resultDAO.saveResult(result);

        // Return the saved entity (may include generated ids or timestamps added by DAO)
        return result;
    }
}