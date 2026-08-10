import java.util.ArrayList;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class ReportsController {

    @FXML
    private ComboBox<Student> studentComboBox;

    @FXML
    private ComboBox<Course> courseComboBox;

    @FXML
    private TextArea reportArea;

    private StudentManager studentManager;
    private CourseManager courseManager;
    private EnrollmentManager enrollmentManager;
    private MarksManager marksManager;

    // ==============================
    // Initialize
    // ==============================

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

        marksManager =
                data.getMarksManager();

        refreshData();

        generateSystemSummary();
    }

    // ==============================
    // Refresh Dropdowns
    // ==============================

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
    }

    // ==============================
    // System Summary
    // ==============================

    @FXML
    public void generateSystemSummary() {

        StringBuilder report =
                new StringBuilder();

        report.append(
                "========================================\n");

        report.append(
                "          STUDENTSPHERE REPORT\n");

        report.append(
                "========================================\n\n");

        report.append("Total Students    : ")
                .append(
                        studentManager
                                .getStudentCount())
                .append("\n");

        report.append("Total Courses     : ")
                .append(
                        courseManager
                                .getCourseCount())
                .append("\n");

        report.append("Total Enrollments : ")
                .append(
                        enrollmentManager
                                .getEnrollmentCount())
                .append("\n");

        report.append("Active            : ")
                .append(
                        enrollmentManager
                                .getActiveEnrollmentCount())
                .append("\n");

        report.append("Completed         : ")
                .append(
                        enrollmentManager
                                .getCompletedEnrollmentCount())
                .append("\n");

        report.append("Dropped           : ")
                .append(
                        enrollmentManager
                                .getDroppedEnrollmentCount())
                .append("\n\n");

        report.append(
                String.format(
                        "Class Average     : %.2f%%%n",
                        studentManager
                                .calculateClassAverage()
                )
        );

        report.append(
                String.format(
                        "Completion Rate   : %.2f%%%n",
                        enrollmentManager
                                .calculateCompletionRate()
                )
        );

        report.append(
                String.format(
                        "Dropout Rate      : %.2f%%%n",
                        enrollmentManager
                                .calculateDropoutRate()
                )
        );

        // ==============================
        // Top Student
        // ==============================

        Student topStudent =
                studentManager.getTopStudent();

        if (topStudent != null) {

            report.append("\nTop Student       : ")
                    .append(
                            topStudent.getStudentID())
                    .append(" - ")
                    .append(
                            topStudent.getFirstName())
                    .append(" ")
                    .append(
                            topStudent.getLastName())
                    .append("\n");

            report.append(
                    String.format(
                            "Top Average       : %.2f%%%n",
                            topStudent
                                    .calculateAverage()
                    )
            );

        } else {

            report.append(
                    "\nTop Student       : None\n");
        }

        // ==============================
        // Popular Course
        // ==============================

        Course popularCourse =
                courseManager.getMostPopularCourse();

        if (popularCourse != null) {

            report.append("\nPopular Course    : ")
                    .append(
                            popularCourse
                                    .getCourseCode())
                    .append(" - ")
                    .append(
                            popularCourse
                                    .getCourseName())
                    .append("\n");

            report.append("Enrollment Count  : ")
                    .append(
                            popularCourse
                                    .getEnrollmentCount())
                    .append("\n");

        } else {

            report.append(
                    "\nPopular Course    : None\n");
        }

        report.append(
                "\n========================================");

        reportArea.setText(
                report.toString());
    }

    // ==============================
    // Student Report
    // ==============================

    @FXML
    public void generateStudentReport() {

        Student student =
                studentComboBox.getValue();

        if (student == null) {

            reportArea.setText(
                    "Please select a student.");

            return;
        }

        StringBuilder report =
                new StringBuilder();

        report.append(
                "========================================\n");

        report.append(
                "           STUDENT REPORT\n");

        report.append(
                "========================================\n\n");

        report.append("Student ID : ")
                .append(
                        student.getStudentID())
                .append("\n");

        report.append("Name       : ")
                .append(
                        student.getFirstName())
                .append(" ")
                .append(
                        student.getLastName())
                .append("\n");

        report.append("Email      : ")
                .append(
                        student.getEmail())
                .append("\n\n");

        if (student.getGrades().isEmpty()) {

            report.append(
                    "No marks have been captured.\n");

        } else {

            for (Map.Entry<Course,
                    ArrayList<Double>> entry
                    : student
                    .getGrades()
                    .entrySet()) {

                Course course =
                        entry.getKey();

                ArrayList<Double> marks =
                        entry.getValue();

                report.append(
                        "----------------------------------------\n");

                report.append(
                        course.getCourseCode())
                        .append(" - ")
                        .append(
                                course.getCourseName())
                        .append("\n");

                for (int index = 0;
                     index < marks.size();
                     index++) {

                    report.append(
                            String.format(
                                    "Assessment %d : %.2f%%%n",
                                    index + 1,
                                    marks.get(index)
                            )
                    );
                }

                double average =
                        student
                                .calculateCourseAverage(
                                        course);

                report.append(
                        String.format(
                                "Course Average : %.2f%%%n",
                                average
                        )
                );

                report.append("Grade          : ")
                        .append(
                                marksManager
                                        .getGradeSymbol(
                                                average))
                        .append("\n");

                report.append("Result         : ")
                        .append(
                                marksManager
                                        .getResult(
                                                average))
                        .append("\n");
            }
        }

        report.append(
                "\n========================================\n");

        report.append(
                String.format(
                        "Overall Average : %.2f%%%n",
                        student.calculateAverage()
                )
        );

        report.append(
                String.format(
                        "Highest Mark    : %.2f%%%n",
                        student.getHighestMark()
                )
        );

        report.append(
                String.format(
                        "Lowest Mark     : %.2f%%%n",
                        student.getLowestMark()
                )
        );

        report.append("Courses Passed  : ")
                .append(
                        marksManager
                                .countPassedCourses(
                                        student))
                .append("\n");

        report.append("Courses Failed  : ")
                .append(
                        marksManager
                                .countFailedCourses(
                                        student))
                .append("\n");

        report.append(
                "========================================");

        reportArea.setText(
                report.toString());
    }

    // ==============================
    // Course Report
    // ==============================

    @FXML
    public void generateCourseReport() {

        Course course =
                courseComboBox.getValue();

        if (course == null) {

            reportArea.setText(
                    "Please select a course.");

            return;
        }

        StringBuilder report =
                new StringBuilder();

        report.append(
                "========================================\n");

        report.append(
                "            COURSE REPORT\n");

        report.append(
                "========================================\n\n");

        report.append("Course Code : ")
                .append(
                        course.getCourseCode())
                .append("\n");

        report.append("Course Name : ")
                .append(
                        course.getCourseName())
                .append("\n");

        report.append("Lecturer    : ")
                .append(
                        course.getLecturer())
                .append("\n");

        report.append("Credits     : ")
                .append(
                        course.getCredits())
                .append("\n");

        report.append("Enrollment  : ")
                .append(
                        course.getEnrollmentCount())
                .append("/")
                .append(
                        course.getMaxStudents())
                .append("\n\n");

        report.append(
                String.format(
                        "Class Average : %.2f%%%n",
                        marksManager
                                .calculateCourseClassAverage(
                                        course)
                )
        );

        report.append(
                String.format(
                        "Pass Rate     : %.2f%%%n",
                        marksManager
                                .calculateCoursePassRate(
                                        course)
                )
        );

        report.append("Passes        : ")
                .append(
                        marksManager
                                .countCoursePasses(
                                        course))
                .append("\n");

        report.append("Failures      : ")
                .append(
                        marksManager
                                .countCourseFailures(
                                        course))
                .append("\n");

        Student topStudent =
                marksManager
                        .getTopStudentForCourse(
                                course);

        if (topStudent != null) {

            report.append("\nTop Student   : ")
                    .append(
                            topStudent
                                    .getStudentID())
                    .append(" - ")
                    .append(
                            topStudent
                                    .getFirstName())
                    .append(" ")
                    .append(
                            topStudent
                                    .getLastName())
                    .append("\n");
        }

        report.append(
                "\n========================================");

        reportArea.setText(
                report.toString());
    }

    // ==============================
    // Enrollment Report
    // ==============================

    @FXML
    public void generateEnrollmentReport() {

        StringBuilder report =
                new StringBuilder();

        report.append(
                "========================================\n");

        report.append(
                "          ENROLLMENT REPORT\n");

        report.append(
                "========================================\n\n");

        report.append("Total Enrollments : ")
                .append(
                        enrollmentManager
                                .getEnrollmentCount())
                .append("\n");

        report.append("Active            : ")
                .append(
                        enrollmentManager
                                .getActiveEnrollmentCount())
                .append("\n");

        report.append("Completed         : ")
                .append(
                        enrollmentManager
                                .getCompletedEnrollmentCount())
                .append("\n");

        report.append("Dropped           : ")
                .append(
                        enrollmentManager
                                .getDroppedEnrollmentCount())
                .append("\n\n");

        report.append(
                String.format(
                        "Completion Rate : %.2f%%%n",
                        enrollmentManager
                                .calculateCompletionRate()
                )
        );

        report.append(
                String.format(
                        "Dropout Rate    : %.2f%%%n",
                        enrollmentManager
                                .calculateDropoutRate()
                )
        );

        report.append(
                "\n----------------------------------------\n");

        report.append(
                "ENROLLMENT RECORDS\n");

        report.append(
                "----------------------------------------\n");

        for (Enrollment enrollment
                : enrollmentManager
                .getEnrollments()) {

            report.append(
                    enrollment
                            .getEnrollmentID())
                    .append(" | ");

            report.append(
                    enrollment
                            .getStudent()
                            .getStudentID())
                    .append(" | ");

            report.append(
                    enrollment
                            .getCourse()
                            .getCourseCode())
                    .append(" | ");

            report.append(
                    enrollment
                            .getAcademicYear())
                    .append(" | ");

            report.append(
                    enrollment
                            .getSemester())
                    .append(" | ");

            report.append(
                    enrollment
                            .getStatus())
                    .append("\n");
        }

        report.append(
                "\n========================================");

        reportArea.setText(
                report.toString());
    }

    // ==============================
    // Clear Report
    // ==============================

    @FXML
    public void clearReport() {

        reportArea.clear();

        studentComboBox
                .getSelectionModel()
                .clearSelection();

        courseComboBox
                .getSelectionModel()
                .clearSelection();
    }
}