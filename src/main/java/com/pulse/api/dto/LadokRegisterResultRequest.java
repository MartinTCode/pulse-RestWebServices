package com.pulse.api.dto;

import java.time.LocalDate;

public class LadokRegisterResultRequest {
    private String personalNo;
    private String courseId;
    private String moduleCode;
    private LocalDate examDate;
    private String grade;

    public LadokRegisterResultRequest() {}

    public String getPersonalNo() { return personalNo; }
    public void setPersonalNo(String personalNo) { this.personalNo = personalNo; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getModuleCode() { return moduleCode; }
    public void setModuleCode(String moduleCode) { this.moduleCode = moduleCode; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}