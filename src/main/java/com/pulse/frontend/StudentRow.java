package com.pulse.frontend;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

/**
 * Model class representing a row in the student results table
 */
public class StudentRow {

    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final StringProperty namn = new SimpleStringProperty("");
    private final StringProperty omdome = new SimpleStringProperty("");
    private final StringProperty betyg = new SimpleStringProperty("");
    private final ObjectProperty<LocalDate> exDatum = new SimpleObjectProperty<>(null);
    private final StringProperty status = new SimpleStringProperty("");
    private final StringProperty information = new SimpleStringProperty("");
    private final StringProperty personalNo = new SimpleStringProperty("");

    /**
     * Constructor to initialize all fields of the StudentRow
     * @param personalNo The personal number of the student
     * @param namn The name of the student
     * @param omdome The assessment of the student from Canvas
     * @param betyg The grade of the student to be recorded to Ladok
     * @param exDatum The exam date of the student grade
     * @param status The status of the student grade entry
     * @param information Additional information about the student grade entry
     */
    public StudentRow(String personalNo, String namn, String omdome, String betyg, LocalDate exDatum, String status, String information) {
        this.personalNo.set(personalNo);
        this.namn.set(namn);
        this.omdome.set(omdome);
        this.betyg.set(betyg);
        this.exDatum.set(exDatum);
        this.status.set(status);
        this.information.set(information);
        
    }

    // PersonalNo
    public StringProperty personalNoProperty() { 
        return personalNo; 
    }

    public String getPersonalNo() { 
        return personalNo.get(); 
    }

    public void setPersonalNo(String v) { 
        personalNo.set(v); 
    }

    // Checkbox
    public BooleanProperty selectedProperty() { 
        return selected; 
    }

    public boolean isSelected() { 
        return selected.get(); 
    }

    public void setSelected(boolean v) { 
        selected.set(v); 
    }

    // Name
    public StringProperty namnProperty() { 
        return namn; 
    }

    public String getNamn() { 
        return namn.get(); 
    }

    public void setNamn(String v) { 
        namn.set(v); 
    }

    // Assessment
    public StringProperty omdomeProperty() { 
        return omdome; 
    }

    public String getOmdome() { 
        return omdome.get(); 
    }

    public void setOmdome(String v) { 
        omdome.set(v); 
    }

    // Grade
    public StringProperty betygProperty() { 
        return betyg; 
    }

    public String getBetyg() { 
        return betyg.get(); 
    }

    public void setBetyg(String v) { 
        betyg.set(v); 
    }

    // Exdate
    public ObjectProperty<LocalDate> exDatumProperty() { 
        return exDatum; 
    }

    public LocalDate getExDatum() { 
        return exDatum.get(); 
    }

    public void setExDatum(LocalDate v) { 
        exDatum.set(v); 
    }

    // Status
    public StringProperty statusProperty() { 
        return status; 
    }

    public String getStatus() { 
        return status.get(); 
    }

    public void setStatus(String v) { 
        status.set(v); 
    }

    // Information
    public StringProperty informationProperty() { 
        return information; 
    }

    public String getInformation() { 
        return information.get(); 
    }
    
    public void setInformation(String v) { 
        information.set(v); 
    }
}
