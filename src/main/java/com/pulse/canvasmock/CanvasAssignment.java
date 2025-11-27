package com.pulse.canvasmock;

import java.util.List;

/**
 * Represents a Canvas assignment with its name, associated student results and module code.
 */
public class CanvasAssignment {
    private String assignmentName;
    private List<CanvasStudentResult> results;
    private String moduleCode;

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

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}