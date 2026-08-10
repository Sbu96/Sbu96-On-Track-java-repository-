import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.*;

public class MainController {

    // ==============================
    // Dashboard Labels
    // ==============================

    @FXML
    private Label studentCountLabel;

    @FXML
    private Label courseCountLabel;

    @FXML
    private Label enrollmentCountLabel;

    @FXML
    private Label activeEnrollmentLabel;

    @FXML
    private Label completedEnrollmentLabel;

    @FXML
    private Label droppedEnrollmentLabel;

    @FXML
    private Label classAverageLabel;

    @FXML
    private Label statusLabel;

    // ==============================
    // Shared Application Data
    // ==============================

    private ApplicationData applicationData;

    private StudentManager studentManager;
    private CourseManager courseManager;
    private EnrollmentManager enrollmentManager;

    // ==============================
    // JavaFX Initialization
    // ==============================

    @FXML
    public void initialize() {

        applicationData =
                ApplicationData.getInstance();

        studentManager =
                applicationData.getStudentManager();

        courseManager =
                applicationData.getCourseManager();

        enrollmentManager =
                applicationData.getEnrollmentManager();

        boolean loaded =
                applicationData.loadAllData();

        if (loaded) {

            setStatus(
                    Constants.MESSAGE_LOAD_COMPLETE);

        } else {

            setStatus(
                    "Some data could not be loaded.");
        }

        refreshDashboard();
    }

    // ==============================
    // Refresh Dashboard
    // ==============================

    @FXML
    public void refreshDashboard() {

        studentCountLabel.setText(
                String.valueOf(
                        studentManager.getStudentCount()));

        courseCountLabel.setText(
                String.valueOf(
                        courseManager.getCourseCount()));

        enrollmentCountLabel.setText(
                String.valueOf(
                        enrollmentManager
                                .getEnrollmentCount()));

        activeEnrollmentLabel.setText(
                String.valueOf(
                        enrollmentManager
                                .getActiveEnrollmentCount()));

        completedEnrollmentLabel.setText(
                String.valueOf(
                        enrollmentManager
                                .getCompletedEnrollmentCount()));

        droppedEnrollmentLabel.setText(
                String.valueOf(
                        enrollmentManager
                                .getDroppedEnrollmentCount()));

        classAverageLabel.setText(
                String.format(
                        "%.2f%%",
                        studentManager
                                .calculateClassAverage()));
    }

    // ==============================
    // Save Data
    // ==============================

    @FXML
    public void saveData() {

        boolean saved =
                applicationData.saveAllData();

        if (saved) {

            setStatus(
                    Constants.MESSAGE_SAVE_SUCCESS);

        } else {

            setStatus(
                    Constants.MESSAGE_SAVE_FAILURE);
        }
    }

    // ==============================
    // Navigation Methods
    // ==============================

    @FXML
public void openStudents() {

    try {

        FXMLLoader loader =
                new FXMLLoader(
                        new java.io.File(
                                "StudentView.fxml"
                        ).toURI().toURL()
                );

        Parent root =
                loader.load();

        Stage stage =
                new Stage();

        stage.setTitle(
                "Student Management"
        );

        stage.setScene(
                new Scene(
                        root,
                        1000,
                        650
                )
        );

        stage.show();

        setStatus(
                "Student management opened."
        );

    } catch (Exception exception) {

        setStatus(
                "Could not open student management."
        );

        exception.printStackTrace();
    }
}

    @FXML
public void openCourses() {

    try {

        FXMLLoader loader =
                new FXMLLoader(
                        new java.io.File(
                                "CourseView.fxml"
                        ).toURI().toURL()
                );

        Parent root =
                loader.load();

        Stage stage =
                new Stage();

        stage.setTitle(
                "Course Management"
        );

        stage.setScene(
                new Scene(
                        root,
                        1050,
                        680
                )
        );

        stage.show();

        setStatus(
                "Course management opened."
        );

    } catch (Exception exception) {

        setStatus(
                "Could not open course management."
        );

        exception.printStackTrace();
    }
}

    @FXML
public void openEnrollments() {

    try {

        FXMLLoader loader =
                new FXMLLoader(
                        new java.io.File(
                                "EnrollmentView.fxml"
                        ).toURI().toURL()
                );

        Parent root =
                loader.load();

        Stage stage =
                new Stage();

        stage.setTitle(
                "Enrollment Management"
        );

        stage.setScene(
                new Scene(
                        root,
                        1100,
                        700
                )
        );

        stage.show();

        setStatus(
                "Enrollment management opened."
        );

    } catch (Exception exception) {

        setStatus(
                "Could not open enrollment management."
        );

        exception.printStackTrace();
    }
}

    @FXML
public void openMarks() {

    try {

        FXMLLoader loader =
                new FXMLLoader(
                        new java.io.File(
                                "MarksView.fxml"
                        ).toURI().toURL()
                );

        Parent root =
                loader.load();

        Stage stage =
                new Stage();

        stage.setTitle(
                "Marks Management"
        );

        stage.setScene(
                new Scene(
                        root,
                        1050,
                        680
                )
        );

        stage.show();

        setStatus(
                "Marks management opened."
        );

    } catch (Exception exception) {

        setStatus(
                "Could not open marks management."
        );

        exception.printStackTrace();
    }
}

    @FXML
public void openReports() {

    try {

        FXMLLoader loader =
                new FXMLLoader(
                        new java.io.File(
                                "ReportsView.fxml"
                        ).toURI().toURL()
                );

        Parent root =
                loader.load();

        Stage stage =
                new Stage();

        stage.setTitle(
                "Reports"
        );

        stage.setScene(
                new Scene(
                        root,
                        1100,
                        700
                )
        );

        stage.show();

        setStatus(
                "Reports opened."
        );

    } catch (Exception exception) {

        setStatus(
                "Could not open reports."
        );

        exception.printStackTrace();
    }
}

    // ==============================
    // Status Message
    // ==============================

    private void setStatus(String message) {

        if (statusLabel != null) {

            statusLabel.setText(message);
        }
    }
}