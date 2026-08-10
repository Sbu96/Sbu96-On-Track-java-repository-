import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class CourseController {

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField creditsField;

    @FXML
    private TextField lecturerField;

    @FXML
    private TextField maxStudentsField;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TableColumn<Course, String> courseCodeColumn;

    @FXML
    private TableColumn<Course, String> courseNameColumn;

    @FXML
    private TableColumn<Course, String> lecturerColumn;

    @FXML
    private TableColumn<Course, Integer> creditsColumn;

    @FXML
    private TableColumn<Course, Integer> capacityColumn;

    private CourseManager courseManager;

    private EnrollmentManager enrollmentManager;

    private ObservableList<Course> courseData;

    // ==============================
    // Initialize
    // ==============================

    @FXML
    public void initialize() {

        ApplicationData applicationData =
                ApplicationData.getInstance();

        courseManager =
                applicationData.getCourseManager();

        enrollmentManager =
                applicationData.getEnrollmentManager();

        courseData =
                FXCollections.observableArrayList();

        courseCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "courseCode"));

        courseNameColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "courseName"));

        lecturerColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "lecturer"));

        creditsColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "credits"));

        capacityColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "maxStudents"));

        courseTable.setItems(courseData);

        courseTable
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable,
                         oldCourse,
                         selectedCourse) -> {

                            if (selectedCourse != null) {

                                populateFields(
                                        selectedCourse);
                            }
                        }
                );

        refreshTable();
    }

    // ==============================
    // Add Course
    // ==============================

    @FXML
    public void addCourse() {

        String courseCode =
                courseCodeField
                        .getText()
                        .trim();

        String courseName =
                courseNameField
                        .getText()
                        .trim();

        String description =
                descriptionField
                        .getText()
                        .trim();

        String lecturer =
                lecturerField
                        .getText()
                        .trim();

        int credits;
        int maxStudents;

        try {

            credits =
                    Integer.parseInt(
                            creditsField
                                    .getText()
                                    .trim());

            maxStudents =
                    Integer.parseInt(
                            maxStudentsField
                                    .getText()
                                    .trim());

        } catch (NumberFormatException exception) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Number",
                    "Credits and maximum students must be whole numbers."
            );

            return;
        }

        if (!validateCourse(
                courseCode,
                courseName,
                description,
                credits,
                lecturer,
                maxStudents)) {

            return;
        }

        if (courseManager
                .findCourseByCode(courseCode)
                != null) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Duplicate Course",
                    "A course with that code already exists."
            );

            return;
        }

        Course course =
                new Course(
                        courseCode,
                        courseName,
                        description,
                        credits,
                        lecturer,
                        maxStudents
                );

        if (courseManager.addCourse(course)) {

            ApplicationData
                    .getInstance()
                    .saveAllData();

            refreshTable();
            clearFields();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Course Added",
                    "Course added successfully."
            );

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Add Failed",
                    "The course could not be added."
            );
        }
    }

    // ==============================
    // Update Course
    // ==============================

    @FXML
    public void updateCourse() {

        Course selectedCourse =
                courseTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedCourse == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Selection",
                    "Select a course to update."
            );

            return;
        }

        String courseName =
                courseNameField
                        .getText()
                        .trim();

        String description =
                descriptionField
                        .getText()
                        .trim();

        String lecturer =
                lecturerField
                        .getText()
                        .trim();

        int credits;
        int maxStudents;

        try {

            credits =
                    Integer.parseInt(
                            creditsField
                                    .getText()
                                    .trim());

            maxStudents =
                    Integer.parseInt(
                            maxStudentsField
                                    .getText()
                                    .trim());

        } catch (NumberFormatException exception) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Number",
                    "Credits and maximum students must be whole numbers."
            );

            return;
        }

        if (!validateCourse(
                selectedCourse.getCourseCode(),
                courseName,
                description,
                credits,
                lecturer,
                maxStudents)) {

            return;
        }

        boolean updated =
                courseManager.updateCourse(
                        selectedCourse.getCourseCode(),
                        courseName,
                        description,
                        credits,
                        lecturer,
                        maxStudents
                );

        if (updated) {

            ApplicationData
                    .getInstance()
                    .saveAllData();

            refreshTable();
            clearFields();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Course Updated",
                    "Course updated successfully."
            );

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Update Failed",
                    "The course could not be updated. "
                            + "The new capacity may be lower "
                            + "than the current enrollment count."
            );
        }
    }

    // ==============================
    // Delete Course
    // ==============================

    @FXML
    public void deleteCourse() {

        Course selectedCourse =
                courseTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedCourse == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Selection",
                    "Select a course to delete."
            );

            return;
        }

        if (!enrollmentManager
                .findEnrollmentsByCourse(
                        selectedCourse
                                .getCourseCode())
                .isEmpty()) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Delete Blocked",
                    "This course has enrollment records."
            );

            return;
        }

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Confirm Deletion");

        confirmation.setHeaderText(
                "Delete Course");

        confirmation.setContentText(
                "Delete "
                        + selectedCourse
                        .getCourseCode()
                        + " - "
                        + selectedCourse
                        .getCourseName()
                        + "?"
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get()
                == ButtonType.OK) {

            boolean deleted =
                    courseManager
                            .removeCourse(
                                    selectedCourse
                                            .getCourseCode());

            if (deleted) {

                ApplicationData
                        .getInstance()
                        .saveAllData();

                refreshTable();
                clearFields();

            } else {

                showAlert(
                        Alert.AlertType.ERROR,
                        "Delete Failed",
                        "The course could not be deleted."
                );
            }
        }
    }

    // ==============================
    // Search
    // ==============================

    @FXML
    public void searchCourses() {

        String keyword =
                searchField
                        .getText()
                        .trim();

        courseData.clear();

        if (keyword.isEmpty()) {

            courseData.addAll(
                    courseManager
                            .getCourses());

            return;
        }

        Course exactCourse =
                courseManager
                        .findCourseByCode(
                                keyword);

        if (exactCourse != null) {

            courseData.add(exactCourse);
            return;
        }

        courseData.addAll(
                courseManager
                        .findCoursesByName(
                                keyword));
    }

    @FXML
    public void clearSearch() {

        searchField.clear();
        refreshTable();
    }

    // ==============================
    // Clear Form
    // ==============================

    @FXML
    public void clearFields() {

        courseCodeField.clear();
        courseNameField.clear();
        descriptionField.clear();
        creditsField.clear();
        lecturerField.clear();
        maxStudentsField.clear();

        courseCodeField.setDisable(false);

        courseTable
                .getSelectionModel()
                .clearSelection();
    }

    // ==============================
    // Refresh
    // ==============================

    private void refreshTable() {

        courseData.setAll(
                courseManager
                        .getCourses());

        courseTable.refresh();
    }

    // ==============================
    // Populate Fields
    // ==============================

    private void populateFields(
            Course course) {

        courseCodeField.setText(
                course.getCourseCode());

        courseNameField.setText(
                course.getCourseName());

        descriptionField.setText(
                course.getDescription());

        creditsField.setText(
                String.valueOf(
                        course.getCredits()));

        lecturerField.setText(
                course.getLecturer());

        maxStudentsField.setText(
                String.valueOf(
                        course.getMaxStudents()));

        courseCodeField.setDisable(true);
    }

    // ==============================
    // Validation
    // ==============================

    private boolean validateCourse(
            String courseCode,
            String courseName,
            String description,
            int credits,
            String lecturer,
            int maxStudents) {

        if (!InputValidator
                .isValidCourseCode(
                        courseCode)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Course Code",
                    InputValidator
                            .getCourseCodeMessage()
            );

            return false;
        }

        if (!InputValidator
                .isValidCourseName(
                        courseName)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Course Name",
                    "Course name must contain "
                            + "between 3 and 100 characters."
            );

            return false;
        }

        if (!InputValidator
                .isValidDescription(
                        description)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Description",
                    "Description cannot exceed "
                            + Constants.MAX_DESCRIPTION_LENGTH
                            + " characters."
            );

            return false;
        }

        if (!InputValidator
                .isValidCredits(
                        credits)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Credits",
                    "Credits must be between "
                            + Constants.MIN_CREDITS
                            + " and "
                            + Constants.MAX_CREDITS
                            + "."
            );

            return false;
        }

        if (!InputValidator
                .isValidLecturerName(
                        lecturer)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Lecturer",
                    "Enter a valid lecturer name."
            );

            return false;
        }

        if (!InputValidator
                .isValidCapacity(
                        maxStudents)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Capacity",
                    "Course capacity must be between "
                            + Constants.MIN_COURSE_CAPACITY
                            + " and "
                            + Constants.MAX_COURSE_CAPACITY
                            + "."
            );

            return false;
        }

        return true;
    }

    // ==============================
    // Alert
    // ==============================

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