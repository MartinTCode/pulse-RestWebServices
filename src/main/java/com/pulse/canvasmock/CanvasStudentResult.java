package com.pulse.canvasmock;

//import java.util.List;

public class CanvasStudentResult {

    private String studentId;
    private String studentName;
    private String canvasGrade;

    /**
     * Constructor with no arguments for instantiating without initial values
     */
    public CanvasStudentResult() {
    }

    /**
     * Constructor to initialize student result with given values
     * @param studentId   the ID of the student
     * @param studentName the name of the student
     * @param canvasGrade the grade of the student in Canvas
     */
    public CanvasStudentResult(String studentId, String studentName, String canvasGrade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.canvasGrade = canvasGrade;
    }

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