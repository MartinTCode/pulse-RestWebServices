package com.pulse.canvasmock;

import java.util.List;

public class CanvasCourse {
    private String courseId;
    private String courseName;
    private List<CanvasAssignment> assignments;

    // Getters and Setters
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

    public List<CanvasAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<CanvasAssignment> assignments) {
        this.assignments = assignments;
    }
}