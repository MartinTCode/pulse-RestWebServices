package com.pulse.canvasmock;

import java.util.List;

public class CanvasAssignment {
    private String assignmentName;
    private List<CanvasStudentResult> results;

    // Getters and Setters
    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public List<CanvasStudentResult> getResults() {
        return results;
    }

    public void setResults(List<CanvasStudentResult> results) {
        this.results = results;
    }
}