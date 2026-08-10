import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MarksController {

    @FXML
    private ComboBox<Student> studentComboBox;

    @FXML
    private ComboBox<Course> courseComboBox;

    @FXML
    private TextField markField;

    @FXML
    private TextField assessmentNumberField;

    @FXML
    private TableView<MarkRecord> marksTable;

    @FXML
    private TableColumn<MarkRecord, Integer> assessmentColumn;

    @FXML
    private TableColumn<MarkRecord, Double> markColumn;

    @FXML
    private Label averageLabel;

    @FXML
    private Label gradeLabel;

    @FXML
    private Label resultLabel;

    @FXML
    private Label statusLabel;

    private MarksManager marksManager;

    private ObservableList<MarkRecord> markRecords;

    @FXML
    public void initialize() {

        marksManager = new MarksManager();

        markRecords =
                FXCollections.observableArrayList();

        assessmentColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "assessmentNumber"
                )
        );

        markColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "mark"
                )
        );

        marksTable.setItems(markRecords);

        loadStudents();
        loadCourses();

        studentComboBox.setOnAction(
                event -> refreshMarks()
        );

        courseComboBox.setOnAction(
                event -> refreshMarks()
        );

        marksTable
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable,
                         oldValue,
                         selectedRecord) -> {

                            if (selectedRecord != null) {

                                assessmentNumberField.setText(
                                        String.valueOf(
                                                selectedRecord
                                                        .getAssessmentNumber()
                                        )
                                );

                                markField.setText(
                                        String.valueOf(
                                                selectedRecord.getMark()
                                        )
                                );
                            }
                        }
                );

        setStatus("Marks management ready.");
    }

    private void loadStudents() {

        studentComboBox.getItems().clear();

        try {

            List<Student> students =
        ApplicationData
                .getInstance()
                .getStudentManager()
                .getStudents();

            studentComboBox
                    .getItems()
                    .addAll(students);

        } catch (Exception exception) {

            setStatus(
                    "Could not load students."
            );

            exception.printStackTrace();
        }
    }

    private void loadCourses() {

        courseComboBox.getItems().clear();

        try {

            List<Course> courses =
        ApplicationData
                .getInstance()
                .getCourseManager()
                .getCourses();

            courseComboBox
                    .getItems()
                    .addAll(courses);

        } catch (Exception exception) {

            setStatus(
                    "Could not load courses."
            );

            exception.printStackTrace();
        }
    }

    @FXML
    public void addMark() {

        Student student =
                studentComboBox.getValue();

        Course course =
                courseComboBox.getValue();

        if (student == null) {

            setStatus(
                    "Please select a student."
            );

            return;
        }

        if (course == null) {

            setStatus(
                    "Please select a course."
            );

            return;
        }

        double mark;

        try {

            mark =
                    Double.parseDouble(
                            markField
                                    .getText()
                                    .trim()
                    );

        } catch (NumberFormatException exception) {

            setStatus(
                    "Enter a valid mark."
            );

            return;
        }

        boolean recorded =
                marksManager.recordMark(
                        student,
                        course,
                        mark
                );

        if (recorded) {

            setStatus(
                    "Mark recorded successfully."
            );

            markField.clear();

            refreshMarks();

        } else {

            setStatus(
                    "Could not record mark."
            );
        }
    }

    @FXML
    public void updateMark() {

        Student student =
                studentComboBox.getValue();

        Course course =
                courseComboBox.getValue();

        if (student == null ||
                course == null) {

            setStatus(
                    "Select a student and course."
            );

            return;
        }

        int assessmentNumber;
        double mark;

        try {

            assessmentNumber =
                    Integer.parseInt(
                            assessmentNumberField
                                    .getText()
                                    .trim()
                    );

            mark =
                    Double.parseDouble(
                            markField
                                    .getText()
                                    .trim()
                    );

        } catch (NumberFormatException exception) {

            setStatus(
                    "Enter a valid assessment number and mark."
            );

            return;
        }

        boolean updated =
                marksManager.updateMark(
                        student,
                        course,
                        assessmentNumber,
                        mark
                );

        if (updated) {

            setStatus(
                    "Mark updated successfully."
            );

            refreshMarks();

        } else {

            setStatus(
                    "Could not update mark."
            );
        }
    }

    @FXML
    public void removeMark() {

        Student student =
                studentComboBox.getValue();

        Course course =
                courseComboBox.getValue();

        if (student == null ||
                course == null) {

            setStatus(
                    "Select a student and course."
            );

            return;
        }

        int assessmentNumber;

        try {

            assessmentNumber =
                    Integer.parseInt(
                            assessmentNumberField
                                    .getText()
                                    .trim()
                    );

        } catch (NumberFormatException exception) {

            setStatus(
                    "Select or enter an assessment number."
            );

            return;
        }

        boolean removed =
                marksManager.removeMark(
                        student,
                        course,
                        assessmentNumber
                );

        if (removed) {

            setStatus(
                    "Mark removed successfully."
            );

            clearFields();

            refreshMarks();

        } else {

            setStatus(
                    "Could not remove mark."
            );
        }
    }

    @FXML
    public void refreshMarks() {

        markRecords.clear();

        Student student =
                studentComboBox.getValue();

        Course course =
                courseComboBox.getValue();

        if (student == null ||
                course == null) {

            resetResults();
            return;
        }

        List<Double> marks =
                student.getMarksForCourse(course);

        for (int index = 0;
             index < marks.size();
             index++) {

            markRecords.add(
                    new MarkRecord(
                            index + 1,
                            marks.get(index)
                    )
            );
        }

        updateResults(
                student,
                course
        );
    }

    private void updateResults(
            Student student,
            Course course) {

        double average =
                marksManager
                        .calculateCourseAverage(
                                student,
                                course
                        );

        averageLabel.setText(
                String.format(
                        "%.2f%%",
                        average
                )
        );

        gradeLabel.setText(
                marksManager
                        .getGradeSymbol(
                                average
                        )
        );

        resultLabel.setText(
                marksManager
                        .getResult(
                                average
                        )
        );
    }

    @FXML
    public void clearForm() {

        clearFields();

        marksTable
                .getSelectionModel()
                .clearSelection();

        setStatus(
                "Form cleared."
        );
    }

    private void clearFields() {

        assessmentNumberField.clear();
        markField.clear();
    }

    private void resetResults() {

        averageLabel.setText("0.00%");
        gradeLabel.setText("-");
        resultLabel.setText("-");
    }

    private void setStatus(
            String message) {

        if (statusLabel != null) {

            statusLabel.setText(message);
        }
    }

    /*
     * Small helper class used only by the
     * JavaFX table.
     */
    public static class MarkRecord {

        private final int assessmentNumber;
        private final double mark;

        public MarkRecord(
                int assessmentNumber,
                double mark) {

            this.assessmentNumber =
                    assessmentNumber;

            this.mark = mark;
        }

        public int getAssessmentNumber() {

            return assessmentNumber;
        }

        public double getMark() {

            return mark;
        }
    }
}