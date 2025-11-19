package com.pulse.ladok.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "result", schema = "ladok")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
    private int resultId;

    @Column(name = "personal_no", nullable = false, length = 20)
    private String personalNo;

    @Column(name = "course_id", nullable = false, length = 50)
    private int courseId;

    @Column(name = "module_code", nullable = false, length = 50)
    private String moduleCode;

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @Column(name = "grade", nullable = false, length = 5)
    private String grade;

    // Constructors
    public Result() {
    }
    public Result(String personalNo, int courseId, String moduleCode, LocalDate examDate, String grade) {
        this.personalNo = personalNo;
        this.courseId = courseId;
        this.moduleCode = moduleCode;
        this.examDate = examDate;
        this.grade = grade;
    }

    // Getters and Setters
    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public String getPersonalNo() {
        return personalNo;
    }

    public void setPersonalNo(String personalNo) {
        this.personalNo = personalNo;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }
}