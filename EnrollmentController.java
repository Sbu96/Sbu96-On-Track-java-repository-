import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EnrollmentController {

    @FXML
    private ComboBox<Student> studentComboBox;

    @FXML
    private ComboBox<Course> courseComboBox;

    @FXML
    private TextField academicYearField;

    @FXML
    private ComboBox<String> semesterComboBox;

    @FXML
    private TableView<Enrollment> enrollmentTable;

    @FXML
    private TableColumn<Enrollment, String> enrollmentIDColumn;

    @FXML
    private TableColumn<Enrollment, Student> studentColumn;

    @FXML
    private TableColumn<Enrollment, Course> courseColumn;

    @FXML
    private TableColumn<Enrollment, String> academicYearColumn;

    @FXML
    private TableColumn<Enrollment, String> semesterColumn;

    @FXML
    private TableColumn<Enrollment, EnrollmentStatus> statusColumn;

    private StudentManager studentManager;
    private CourseManager courseManager;
    private EnrollmentManager enrollmentManager;

    private ObservableList<Enrollment> enrollmentData;

    @FXML
    public void initialize() {

        ApplicationData data =
                ApplicationData.getInstance();

        studentManager =
                data.getStudentManager();

        courseManager =
                data.getCourseManager();

        enrollmentManager =
                data.getEnrollmentManager();

        studentComboBox.setItems(
                FXCollections.observableArrayList(
                        studentManager.getStudents()
                )
        );

        courseComboBox.setItems(
                FXCollections.observableArrayList(
                        courseManager.getCourses()
                )
        );

        semesterComboBox.setItems(
                FXCollections.observableArrayList(
                        Constants.SEMESTER_ONE,
                        Constants.SEMESTER_TWO,
                        Constants.SEMESTER_THREE,
                        Constants.FULL_YEAR
                )
        );

        enrollmentIDColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "enrollmentID"));

        studentColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "student"));

        courseColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "course"));

        academicYearColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "academicYear"));

        semesterColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "semester"));

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "status"));

        enrollmentData =
                FXCollections.observableArrayList();

        enrollmentTable.setItems(
                enrollmentData);

        refreshTable();
    }

    @FXML
    public void enrollStudent() {

        Student student =
                studentComboBox.getValue();

        Course course =
                courseComboBox.getValue();

        String academicYear =
                academicYearField
                        .getText()
                        .trim();

        String semester =
                semesterComboBox
                        .getValue();

        if (student == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Student Required",
                    "Select a student."
            );

            return;
        }

        if (course == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Course Required",
                    "Select a course."
            );

            return;
        }

        if (!InputValidator
                .isValidAcademicYear(
                        academicYear)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Academic Year",
                    InputValidator
                            .getAcademicYearMessage()
            );

            return;
        }

        if (semester == null
                || !InputValidator
                .isValidSemester(
                        semester)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Semester",
                    InputValidator
                            .getSemesterMessage()
            );

            return;
        }

        Enrollment enrollment =
                enrollmentManager
                        .enrollStudent(
                                student,
                                course,
                                academicYear,
                                semester
                        );

        if (enrollment != null) {

            ApplicationData
                    .getInstance()
                    .saveAllData();

            refreshTable();
            clearForm();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Enrollment Successful",
                    "Student enrolled successfully.\n"
                            + "Enrollment ID: "
                            + enrollment
                            .getEnrollmentID()
            );

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Enrollment Failed",
                    "The student may already be enrolled "
                            + "or the course may be full."
            );
        }
    }

    @FXML
    public void dropEnrollment() {

        Enrollment selected =
                enrollmentTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Selection",
                    "Select an enrollment to drop."
            );

            return;
        }

        if (selected.getStatus()
                != EnrollmentStatus.ACTIVE) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Status",
                    "Only active enrollments can be dropped."
            );

            return;
        }

        boolean dropped =
                enrollmentManager
                        .dropEnrollment(
                                selected
                                        .getEnrollmentID(),
                                "Dropped through GUI"
                        );

        if (dropped) {

            ApplicationData
                    .getInstance()
                    .saveAllData();

            refreshTable();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Enrollment Dropped",
                    "Enrollment dropped successfully."
            );
        }
    }

    @FXML
    public void removeEnrollment() {

        Enrollment selected =
                enrollmentTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Selection",
                    "Select an enrollment record."
            );

            return;
        }

        boolean removed =
                enrollmentManager
                        .removeEnrollmentRecord(
                                selected
                                        .getEnrollmentID()
                        );

        if (removed) {

            ApplicationData
                    .getInstance()
                    .saveAllData();

            refreshTable();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Enrollment Removed",
                    "Enrollment record removed."
            );
        }
    }

    @FXML
    public void refreshData() {

        studentComboBox.setItems(
                FXCollections.observableArrayList(
                        studentManager.getStudents()
                )
        );

        courseComboBox.setItems(
                FXCollections.observableArrayList(
                        courseManager.getCourses()
                )
        );

        refreshTable();
    }

    @FXML
    public void clearForm() {

        studentComboBox
                .getSelectionModel()
                .clearSelection();

        courseComboBox
                .getSelectionModel()
                .clearSelection();

        academicYearField.clear();

        semesterComboBox
                .getSelectionModel()
                .clearSelection();
    }

    private void refreshTable() {

        enrollmentData.setAll(
                enrollmentManager
                        .getEnrollments());

        enrollmentTable.refresh();
    }

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert =
                new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}