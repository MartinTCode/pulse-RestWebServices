package com.pulse.epok.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "course", schema = "epok")
public class Course {

    @Id
    @Column(name = "course_id", nullable = false, length = 50)      
    private String courseId;

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    //One-to-Many relationship with Module
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Module> modules;

    //Constructor
    public Course() {
    }

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    //Getters and Setters
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

    //Modules Getter and Setter
    public List<Module> getModules() {
        return modules;
    }
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

}
