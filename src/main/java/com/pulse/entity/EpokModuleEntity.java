package com.pulse.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "module", schema = "epok")
public class EpokModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id", nullable = false)
    private int moduleId;

    @Column(name = "module_code", length = 50)
    private String moduleCode;

    @Column(name = "module_name", nullable = false, length = 100)
    private String moduleName;

    // FK: module.course_id -> course.course_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private EpokCourseEntity course;

    // Constructors
    public EpokModuleEntity() {
    }

    public EpokModuleEntity(String moduleCode, String moduleName, EpokCourseEntity course) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.course = course;
    }

    // Getters and Setters
    public int getModuleId() {
        return moduleId;
    }
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }
    
    public String getModuleCode() {
        return moduleCode;
    }
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public EpokCourseEntity getCourse() {
        return course;
    }
    public void setCourse(EpokCourseEntity course) {
        this.course = course;
    }
}

