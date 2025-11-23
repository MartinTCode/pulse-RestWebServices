package com.pulse.service;

import com.pulse.dao.LadokResultDAO;
import com.pulse.entity.LadokResultEntity;

import java.time.LocalDate;

public class LadokResultService {

    private final LadokResultDAO resultDAO;

    public LadokResultService(LadokResultDAO resultDAO) {
        this.resultDAO = resultDAO;
    }

    public LadokResultEntity registerResult(String personalNo, String courseId, String moduleCode, LocalDate examDate, String grade) {
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

        LadokResultEntity result = new LadokResultEntity(personalNo, courseId, moduleCode, examDate, grade);
        resultDAO.saveResult(result);

        return result;
    }
}