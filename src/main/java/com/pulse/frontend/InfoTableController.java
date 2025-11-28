package com.pulse.frontend;

import com.pulse.api.EpokApiClient;
import com.pulse.api.LadokApiClient;
import com.pulse.api.StudentItsApiClient;
import com.pulse.api.dto.EpokModuleDTO;
import com.pulse.api.dto.LadokResultDTO;
import com.pulse.api.dto.StudentItsDTO;
import com.pulse.canvasmock.CanvasMockData;
import com.pulse.canvasmock.CanvasStudentResult;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for managing the Info Table User Interface
 */
public class InfoTableController {

    @FXML private TableView<StudentRow> infoTableView;
    @FXML private TableColumn<StudentRow, Boolean> rutColumn;
    @FXML private TableColumn<StudentRow, String> namnColumn;
    @FXML private TableColumn<StudentRow, String> omdomeColumn;
    @FXML private TableColumn<StudentRow, String> betygColumn;
    @FXML private TableColumn<StudentRow, LocalDate> exdatumColumn;
    @FXML private TableColumn<StudentRow, String> statusColumn;
    @FXML private TableColumn<StudentRow, String> informationColumn;

    @FXML private Button markeraAllaButton;
    @FXML private Button markeraBetygsattaButton;
    @FXML private Button markeraIngabutton;
    @FXML private Button overforMarkeradeButton;

    @FXML private ComboBox<String> kurskodBox;
    @FXML private ComboBox<String> modulBox;
    @FXML private ComboBox<String> uppgiftBox;
    @FXML private ComboBox<String> sparaUtkastBox;

    @FXML private DatePicker datumMarkerade;
    @FXML private Button sattDatumButton;

    @FXML private Label antalMarkeradeLabel;

    private final ObservableList<StudentRow> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. 
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the table columns, combo boxes, buttons, and their event handlers.
     */
    @FXML
    public void initialize() {

        sparaUtkastBox.setVisible(false);

        infoTableView.setEditable(true);

        namnColumn.setCellValueFactory(c -> c.getValue().namnProperty());
        namnColumn.setEditable(false);

        statusColumn.setCellValueFactory(c -> c.getValue().statusProperty());
        statusColumn.setEditable(false);

        informationColumn.setCellValueFactory(c -> c.getValue().informationProperty());
        informationColumn.setEditable(false);

        // Checkbox column
        rutColumn.setCellValueFactory(c -> c.getValue().selectedProperty());
        rutColumn.setCellFactory(CheckBoxTableCell.forTableColumn(rutColumn));
        rutColumn.setEditable(true);

        // Textcolumns
        setupEditableTextColumn(omdomeColumn, StudentRow::omdomeProperty, StudentRow::setOmdome);
        setupEditableTextColumn(betygColumn, StudentRow::betygProperty, StudentRow::setBetyg);

        // DatePicker column
        exdatumColumn.setCellValueFactory(c -> c.getValue().exDatumProperty());
        exdatumColumn.setCellFactory(col -> new DatePickerTableCell<>());
        exdatumColumn.setEditable(true);

        // Populate combobox with available courses
        kurskodBox.setItems(FXCollections.observableArrayList(
            CanvasMockData.getInstance().getAvailableCourses()
        ));

        kurskodBox.setOnAction(e -> {
            loadAssignments();
            loadModules(); //get modules via REST
        });

        uppgiftBox.setOnAction(e -> loadModules());

        modulBox.setOnAction(e -> loadStudents());

        // Add empty data
        infoTableView.setItems(data);

         // Listeners for existing rows
        data.forEach(row ->
            row.selectedProperty().addListener((obs, o, n) -> updateAntalMarkerade())
        );

        // Add Listener for future added rows
        data.addListener((javafx.collections.ListChangeListener.Change<? extends StudentRow> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (StudentRow r : c.getAddedSubList()) {
                        r.selectedProperty().addListener((obs, oldVal, newVal) -> updateAntalMarkerade());
                    }
                }
            }
        });


        // Buttons actions
        markeraAllaButton.setOnAction(e -> data.forEach(row -> row.setSelected(true)));
        markeraIngabutton.setOnAction(e -> data.forEach(row -> row.setSelected(false)));
        markeraBetygsattaButton.setOnAction(e ->
            data.forEach(row -> row.setSelected(!row.getBetyg().isEmpty()))
        );

        // Set date for selected rows
        sattDatumButton.setOnAction(e -> {
            LocalDate d = datumMarkerade.getValue();
            if (d == null) return;

            data.stream()
                .filter(StudentRow::isSelected)
                .forEach(row -> row.setExDatum(d));

            infoTableView.refresh();
        });

        overforMarkeradeButton.setOnAction(e -> overforMarkerade());

    }
    // Update label for "antal markerade"
    private void updateAntalMarkerade() {
        long count = data.stream().filter(StudentRow::isSelected).count();
        antalMarkeradeLabel.setText("Antal markerade: " + count);
    }

    /**
     * Sets up an editable text column in the table.
     * @param col The TableColumn to set up
     * @param prop A callback to get the StringProperty from the StudentRow
     * @param setter A BiConsumer to set the new value in the StudentRow
     */
    private void setupEditableTextColumn(
            TableColumn<StudentRow, String> col,
            javafx.util.Callback<StudentRow, javafx.beans.property.StringProperty> prop,
            java.util.function.BiConsumer<StudentRow, String> setter) {

        col.setCellValueFactory(c -> prop.call(c.getValue()));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setOnEditCommit(e -> setter.accept(e.getRowValue(), e.getNewValue()));
        col.setEditable(true);
    }

    /**
     * Custom TableCell implementation that uses a DatePicker for editing LocalDate values.
     * @param <S> The type of the TableView generic type
     * @param <T> The type of the item contained within the Cell
     */
    private static class DatePickerTableCell<S> extends TableCell<S, LocalDate> {
        private final DatePicker datePicker = new DatePicker();

        DatePickerTableCell() {
            datePicker.setOnAction(e -> commitEdit(datePicker.getValue()));
            setGraphic(datePicker);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

        /**
         * Starts editing the cell by showing the DatePicker.
         */
        @Override
        public void startEdit() {
            super.startEdit();
            datePicker.setValue(getItem());
            datePicker.requestFocus();
            datePicker.show();
        }

        /**
         * Cancels editing and reverts to displaying the text.
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem() == null ? "" : getItem().toString());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        /**
         * Commits the edit with the new LocalDate value.
         * @param newValue The new LocalDate value to commit
         */
        @Override
        public void commitEdit(LocalDate newValue) {
            super.commitEdit(newValue);

            StudentRow row = (StudentRow) getTableRow().getItem();
            if (row != null) row.setExDatum(newValue);

            setText(newValue == null ? "" : newValue.toString());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        /**
         * Updates the item in the cell, displaying either the DatePicker or the text.
         * @param item The LocalDate item
         * @param empty Whether the cell is empty
         */
        @Override
        protected void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                if (isEditing()) {
                    datePicker.setValue(item);
                    setGraphic(datePicker);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setGraphic(null);
                    setText(item == null ? "" : item.toString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }
    }

    /**
     * Transfers the selected student results to Ladok via the Ladok API.
     * Validates the selected rows and sends the data in a background thread.
     * Updates the UI with the results of the transfer.
     */
    private void overforMarkerade() {
        // Get selected rows
        var selectedRows = data.stream().filter(StudentRow::isSelected).toList();

        if (selectedRows.isEmpty()) {
            showAlert("Inga markerade rader", "Vänligen markera minst en rad för att överföra.");
            return;
        }

        if (kurskodBox.getValue() == null || modulBox.getValue() == null) {
            showAlert("Saknas val", "Välj både kurskod och modul innan du överför.");
            return;
        }

        // Validate that selected rows have required data
        boolean hasInvalidRows = selectedRows.stream()
            .anyMatch(row -> row.getBetyg() == null || row.getBetyg().isBlank() || 
                            row.getExDatum() == null ||
                            row.getPersonalNo() == null || row.getPersonalNo().isBlank());
        
        if (hasInvalidRows) {
            showAlert("Ofullständig data", 
                "Alla markerade rader måste ha betyg, examinationsdatum och personnummer.");
            return;
        }

        boolean hasRowWithRegisteredGrade = selectedRows.stream()
            .anyMatch(row -> row.getStatus().equals("SUCCESS"));

        if (hasRowWithRegisteredGrade) {
            showAlert("Redan överförda betyg", 
                "En eller flera markerade rader har redan ett registrerat betyg i Ladok.");
            return;
        }

        // Split module code if needed
        String moduleCode = modulBox.getValue().split(" ")[0];
        String courseId = kurskodBox.getValue();

        // Convert to DTO objects for backend transfer
        var dtoList = selectedRows.stream()
            .map(row -> new LadokResultDTO(
                row.getPersonalNo(),
                courseId,
                moduleCode,
                row.getBetyg(),
                row.getExDatum()
            )).toList();

        // Disable button during transfer
        overforMarkeradeButton.setDisable(true);
        
        // POST to backend
        new Thread(() -> {
            try {
                var responseList = LadokApiClient.sendResults(dtoList);

                // Update UI based on response
                javafx.application.Platform.runLater(() -> {
                    int successCount = 0;
                    int failCount = 0;
                    
                    for (var res : responseList) {
                        selectedRows.stream()
                            .filter(row -> row.getPersonalNo().equals(res.personalNo()))
                            .forEach(r -> {
                                r.setStatus(res.status());
                                r.setInformation(res.info());
                            });
                        
                        if ("SUCCESS".equals(res.status())) {
                            successCount++;
                        } else {
                            failCount++;
                        }
                    }
                    
                    infoTableView.refresh();
                    
                    // Show summary
                    String summary = String.format(
                        "Överföring klar!\n\nLyckade: %d\nMisslyckade: %d\nTotalt: %d",
                        successCount, failCount, responseList.size()
                    );
                    
                    showAlert(
                        failCount == 0 ? "Överföring lyckades" : "Överföring delvis lyckad",
                        summary
                    );
                    
                    // Re-enable button
                    overforMarkeradeButton.setDisable(false);
                });

            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    showAlert("Fel vid överföring", 
                        "Ett fel uppstod vid överföring till Ladok:\n" + e.getMessage());
                    overforMarkeradeButton.setDisable(false);
                });
                e.printStackTrace(); // Log to console for debugging
            }

        }).start();
    }

    /**
     * Shows an alert dialog with the given title and message.
     * @param title The title of the alert dialog
     * @param msg The message content of the alert dialog
     */
    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle(title);
            a.setHeaderText(null);
            a.setContentText(msg);
            a.showAndWait();
    }

    /**
     * Loads assignments for the selected course from the Canvas mock data.
     * Populates the assignment ComboBox and clears dependent selections.
     */
    private void loadAssignments() {
        String courseId = kurskodBox.getValue();
        if (courseId == null) return;

        var assignments = CanvasMockData.getInstance().getAssignmentsForCourse(courseId);

        uppgiftBox.setItems(FXCollections.observableArrayList( 
            assignments.stream().map(a -> a.getAssignmentName()).toList()
        ));

        uppgiftBox.getSelectionModel().clearSelection();
        modulBox.getSelectionModel().clearSelection();
        data.clear();

    }

    /**
     * Loads modules for the selected course from the Epok API.
     * Populates the module ComboBox and clears dependent selections.
     */
    private void loadModules() {
        String courseId = kurskodBox.getValue();
        if (courseId == null) return;

        new Thread(() -> {
            try {
                List<EpokModuleDTO> modules = EpokApiClient.getModulesByCourseId(courseId);

                javafx.application.Platform.runLater(() -> {
                    modulBox.setItems(FXCollections.observableArrayList(
                        modules.stream()
                               .map(m -> m.moduleCode() + " " + m.moduleName())
                               .toList()
                        ));
                        modulBox.getSelectionModel().clearSelection();
                    });

                } catch (Exception e) {
                    javafx.application.Platform.runLater(() ->
                        showAlert("Fel", "Kunde inte hämta moduler: " + e.getMessage())
                    );
                }
            }).start();
        }

    /**
     * Loads student results for the selected course and assignment from the Canvas mock data.
     * Populates the table with student rows including personal numbers fetched from StudentITS.
     */
    private void loadStudents() {
        String courseId = kurskodBox.getValue();
        String assignment = uppgiftBox.getValue();
        
        if (courseId == null || assignment == null) {
            return;
        }
        data.clear();
        
        List<CanvasStudentResult> results = CanvasMockData.getInstance()
            .getResults(courseId, assignment);

        // Fetch personal numbers in background thread
        new Thread(() -> {
            for (CanvasStudentResult result : results) {
                String personalNo = "";
                String info = "";
                
                try {
                    // Fetch personal number from StudentITS
                    StudentItsDTO student = StudentItsApiClient.getStudentByStudentId(result.getStudentId());
                    
                    if (student != null) {
                        personalNo = student.personalNo();
                    } else {
                        info = "Personnummer saknas i StudentITS";
                    }
                    
                } catch (Exception e) {
                    info = "Fel vid hämtning av personnummer: " + e.getMessage();
                }
                
                // Create row with fetched personal number
                final String finalPersonalNo = personalNo;
                final String finalInfo = info;
                
                javafx.application.Platform.runLater(() -> {
                    StudentRow row = new StudentRow(
                        finalPersonalNo,
                        result.getStudentName(),
                        result.getCanvasGrade(),
                        "",
                        null,
                        "",
                        finalInfo
                    );
                    data.add(row);
                });
            }
            
            // Update count after all students are loaded
            javafx.application.Platform.runLater(this::updateAntalMarkerade);
            
        }).start();
    }

}
