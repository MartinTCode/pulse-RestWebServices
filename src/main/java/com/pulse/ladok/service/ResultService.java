package com.pulse.ladok.service;

import com.pulse.ladok.dao.ResultDAO;
import com.pulse.ladok.entity.Result;

import java.time.LocalDate;

public class ResultService {

    private final ResultDAO resultDAO;

    public ResultService(ResultDAO resultDAO) {
        this.resultDAO = resultDAO;
    }

    public Result registerResult(String personalNo, String courseId, String moduleCode, LocalDate examDate, String grade) {
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

        Result result = new Result(personalNo, courseId, moduleCode, examDate, grade);
        resultDAO.saveResult(result);

        return result;
    }
}