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

public class StudentController {

    @FXML
    private TextField studentIDField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> studentIDColumn;

    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    private StudentManager studentManager;

    private ObservableList<Student> studentData;

    @FXML
    public void initialize() {

        studentManager =
                ApplicationData
                        .getInstance()
                        .getStudentManager();

        studentData =
                FXCollections.observableArrayList();

        studentIDColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "studentID"));

        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "firstName"));

        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "lastName"));

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "email"));

        studentTable.setItems(studentData);

        studentTable
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable,
                         oldStudent,
                         selectedStudent) -> {

                            if (selectedStudent != null) {

                                populateFields(
                                        selectedStudent);
                            }
                        }
                );

        refreshTable();
    }

    @FXML
    public void addStudent() {

        String studentID =
                studentIDField
                        .getText()
                        .trim();

        String firstName =
                firstNameField
                        .getText()
                        .trim();

        String lastName =
                lastNameField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        if (!validateStudent(
                studentID,
                firstName,
                lastName,
                email)) {

            return;
        }

        if (studentManager
                .findStudentByID(studentID)
                != null) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Duplicate Student",
                    "A student with that ID already exists."
            );

            return;
        }

        Student student =
                new Student(
                        studentID,
                        firstName,
                        lastName,
                        email
                );

        if (studentManager
                .addStudent(student)) {

            ApplicationData
                    .getInstance()
                    .saveAllData();

            refreshTable();
            clearFields();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Student Added",
                    "The student was added successfully."
            );

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Add Failed",
                    "The student could not be added."
            );
        }
    }

    @FXML
    public void updateStudent() {

        Student selectedStudent =
                studentTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedStudent == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Selection",
                    "Select a student to update."
            );

            return;
        }

        String firstName =
                firstNameField
                        .getText()
                        .trim();

        String lastName =
                lastNameField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        if (!InputValidator
                .isValidName(firstName)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid First Name",
                    InputValidator.getNameMessage()
            );

            return;
        }

        if (!InputValidator
                .isValidName(lastName)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Last Name",
                    InputValidator.getNameMessage()
            );

            return;
        }

        if (!InputValidator
                .isValidEmail(email)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Email",
                    InputValidator.getEmailMessage()
            );

            return;
        }

        Student updatedStudent =
                new Student(
                        selectedStudent
                                .getStudentID(),
                        firstName,
                        lastName,
                        email
                );

        if (studentManager
                .updateStudent(updatedStudent)) {

            ApplicationData
                    .getInstance()
                    .saveAllData();

            refreshTable();
            clearFields();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Student Updated",
                    "The student was updated successfully."
            );

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Update Failed",
                    "The student could not be updated."
            );
        }
    }

    @FXML
    public void deleteStudent() {

        Student selectedStudent =
                studentTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedStudent == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Selection",
                    "Select a student to delete."
            );

            return;
        }

        EnrollmentManager enrollmentManager =
                ApplicationData
                        .getInstance()
                        .getEnrollmentManager();

        if (!enrollmentManager
                .findEnrollmentsByStudent(
                        selectedStudent
                                .getStudentID())
                .isEmpty()) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Delete Blocked",
                    "This student has enrollment records."
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
                "Delete student");

        confirmation.setContentText(
                "Delete "
                        + selectedStudent
                        .getFirstName()
                        + " "
                        + selectedStudent
                        .getLastName()
                        + "?"
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get()
                == ButtonType.OK) {

            boolean deleted =
                    studentManager
                            .removeStudent(
                                    selectedStudent
                                            .getStudentID());

            if (deleted) {

                ApplicationData
                        .getInstance()
                        .saveAllData();

                refreshTable();
                clearFields();
            }
        }
    }

    @FXML
    public void searchStudents() {

        String keyword =
                searchField
                        .getText()
                        .trim();

        studentData.clear();

        if (keyword.isEmpty()) {

            studentData.addAll(
                    studentManager
                            .getStudents());

            return;
        }

        Student exactStudent =
                studentManager
                        .findStudentByID(keyword);

        if (exactStudent != null) {

            studentData.add(exactStudent);
            return;
        }

        studentData.addAll(
                studentManager
                        .findStudentsByName(
                                keyword));
    }

    @FXML
    public void clearSearch() {

        searchField.clear();
        refreshTable();
    }

    @FXML
    public void clearFields() {

        studentIDField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();

        studentIDField.setDisable(false);

        studentTable
                .getSelectionModel()
                .clearSelection();
    }

    private void refreshTable() {

        studentData.setAll(
                studentManager
                        .getStudents());

        studentTable.refresh();
    }

    private void populateFields(
            Student student) {

        studentIDField.setText(
                student.getStudentID());

        firstNameField.setText(
                student.getFirstName());

        lastNameField.setText(
                student.getLastName());

        emailField.setText(
                student.getEmail());

        studentIDField.setDisable(true);
    }

    private boolean validateStudent(
            String studentID,
            String firstName,
            String lastName,
            String email) {

        if (!InputValidator
                .isValidStudentID(
                        studentID)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Student ID",
                    InputValidator
                            .getStudentIDMessage()
            );

            return false;
        }

        if (!InputValidator
                .isValidName(
                        firstName)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid First Name",
                    InputValidator
                            .getNameMessage()
            );

            return false;
        }

        if (!InputValidator
                .isValidName(
                        lastName)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Last Name",
                    InputValidator
                            .getNameMessage()
            );

            return false;
        }

        if (!InputValidator
                .isValidEmail(email)) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Email",
                    InputValidator
                            .getEmailMessage()
            );

            return false;
        }

        return true;
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