package com.pulse.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity class representing a Result in the Ladok system
 */
@Entity
@Table(name = "result", schema = "ladok")
public class LadokResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
    private int resultId;

    @Column(name = "personal_no", nullable = false, length = 20)
    private String personalNo;

    @Column(name = "course_id", nullable = false, length = 50)
    private String courseId;

    @Column(name = "module_code", nullable = false, length = 50)
    private String moduleCode;

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @Column(name = "grade", nullable = false, length = 5)
    private String grade;

    /**
     * Default constructor with no parameters to initialize the entity without setting any fields.
     */
    public LadokResultEntity() {
    }

    /**
     * Parameterized constructor to initialize the entity with specific values.
     * @param personalNo The personal number of the student 
     * @param courseId The ID of the course
     * @param moduleCode The code of the module
     * @param examDate The date of the exam
     * @param grade The grade received
     */
    public LadokResultEntity(String personalNo, String courseId, String moduleCode, LocalDate examDate, String grade) {
        this.personalNo = personalNo;
        this.courseId = courseId;
        this.moduleCode = moduleCode;
        this.examDate = examDate;
        this.grade = grade;
    }

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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
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