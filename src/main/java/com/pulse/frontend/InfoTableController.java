package com.pulse.frontend;

import com.pulse.api.LadokApiClient;
import com.pulse.api.dto.LadokResultDTO;
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
import java.util.stream.Collectors;

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

    @FXML private DatePicker datumMarkerade;
    @FXML private Button sattDatumButton;

    @FXML private Label antalMarkeradeLabel;

    private final ObservableList<StudentRow> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

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

        kurskodBox.setOnAction(e -> loadAssignments());

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

    //-------------------------
    // Helper methods which setup editable text columns
    //-------------------------

    private void setupEditableTextColumn(
            TableColumn<StudentRow, String> col,
            javafx.util.Callback<StudentRow, javafx.beans.property.StringProperty> prop,
            java.util.function.BiConsumer<StudentRow, String> setter) {

        col.setCellValueFactory(c -> prop.call(c.getValue()));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setOnEditCommit(e -> setter.accept(e.getRowValue(), e.getNewValue()));
        col.setEditable(true);
    }

    private static class DatePickerTableCell<S> extends TableCell<S, LocalDate> {
        private final DatePicker datePicker = new DatePicker();

        DatePickerTableCell() {
            datePicker.setOnAction(e -> commitEdit(datePicker.getValue()));
            setGraphic(datePicker);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

        @Override
        public void startEdit() {
            super.startEdit();
            datePicker.setValue(getItem());
            datePicker.requestFocus();
            datePicker.show();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem() == null ? "" : getItem().toString());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void commitEdit(LocalDate newValue) {
            super.commitEdit(newValue);

            StudentRow row = (StudentRow) getTableRow().getItem();
            if (row != null) row.setExDatum(newValue);

            setText(newValue == null ? "" : newValue.toString());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

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

    private void overforMarkerade() {
        //Get selected rows
        var selectedRows = data.stream().filter(StudentRow::isSelected).toList();

        if (selectedRows.isEmpty()) {
            showAlert ("Inga markerade rader", "Vänligen markera minst en rad för att överföra.");
            return;

        }

        if (kurskodBox.getValue() == null || modulBox.getValue() == null) {
            showAlert("Saknas val", "Välj både kurskod och modul innan du överför.");
            return;
        }

        // split module code if needed
        String moduleCode = modulBox.getValue().split(" ")[0];

        //Convert to DTO objects for backend transfer
        var dtoList = selectedRows.stream()
                .map(row -> new LadokResultDTO(
                        row.getPersonalNo(),
                        kurskodBox.getValue(), 
                        moduleCode, 
                        row.getBetyg(),
                        row.getExDatum()
                )).toList();

        // POST to backend
        new Thread(() -> {
            try {
                var responseList = LadokApiClient.sendResults(dtoList);

                //Update UI based on response
                javafx.application.Platform.runLater(() -> {
                    for (var res : responseList) {
                        selectedRows.stream()
                            .filter(row -> row.getPersonalNo().equals(res.personalNo()))
                            .forEach (r -> {
                                r.setStatus(res.status());
                                r.setInformation(res.info());

                            });

                    }
                    infoTableView.refresh();

                });

            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    showAlert("Fel vid överföring", e.getMessage());
                });

            }

        }).start();        

    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle(title);
            a.setHeaderText(null);
            a.setContentText(msg);
            a.showAndWait();
    }


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

    private void loadModules() {
        String assignment = uppgiftBox.getValue();
        if (assignment == null) return;

        List<String> modules = CanvasMockData.getInstance().getModulesForAssignment(assignment);

        modulBox.setItems(FXCollections.observableArrayList(modules));
    }

    private void loadStudents() {
        String courseId = kurskodBox.getValue();
        String assignment = uppgiftBox.getValue();
        String module = modulBox.getValue();
        
        if (courseId == null || assignment == null || module == null) {
            return;
        }
        
        // Extract module code from the string (e.g., "EA001 - Description" -> "EA001")
        String moduleCode = module.split(" ")[0];
        
        // Clear existing data
        data.clear();
        
        // Get student results from Canvas mock data
        List<CanvasStudentResult> results = CanvasMockData.getInstance()
            .getResults(courseId, assignment);
        
        // Filter results by module code and convert to StudentRow
        for (CanvasStudentResult result : results) {
            // Only add students for the selected module
            // You may need to adjust this if CanvasStudentResult has module information
            StudentRow row = new StudentRow(
                result.getStudentId(),            // personalNo
                result.getStudentName(),          // namn
                result.getCanvasGrade(),          // omdome (Canvas grade)
                "",                        // betyg (empty initially)
                null,                    // exDatum (null initially)
                "",                       // status (empty initially)
                ""                   // information (empty initially)
            );
            data.add(row);
        }
        
        updateAntalMarkerade();
    }
}
