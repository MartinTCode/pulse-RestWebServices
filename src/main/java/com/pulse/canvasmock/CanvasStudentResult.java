package com.pulse.canvasmock;

//import java.util.List;

public class CanvasStudentResult {

    private String studentId;
    private String studentName;
    private String canvasGrade;

    //Constructors
    public CanvasStudentResult() {
    }

    public CanvasStudentResult(String studentId, String studentName, String canvasGrade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.canvasGrade = canvasGrade;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCanvasGrade() {
        return canvasGrade;
    }

    public void setCanvasGrade(String canvasGrade) {
        this.canvasGrade = canvasGrade;
    }
}