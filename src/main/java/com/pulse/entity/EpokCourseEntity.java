package com.pulse.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entity class representing a Course in the Epok system
 */
@Entity
@Table(name = "course", schema = "epok")
public class EpokCourseEntity {

    @Id
    @Column(name = "course_id", nullable = false, length = 50)      
    private String courseId;

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    //One-to-Many relationship with Module
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<EpokModuleEntity> modules;

    /**
     * Default constructor with no parameters to initialize the entity without setting any fields.
     */
    public EpokCourseEntity() {
    }

    /**
     * Parameterized constructor to initialize the entity with specific values.
     * @param courseId The ID of the course
     * @param courseName The name of the course
     */
    public EpokCourseEntity(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<EpokModuleEntity> getModules() {
        return modules;
    }

    public void setModules(List<EpokModuleEntity> modules) {
        this.modules = modules;
    }

}
