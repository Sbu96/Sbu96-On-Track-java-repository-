import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Scanner input =
            new Scanner(System.in);

    private static final StudentManager studentManager =
            new StudentManager();

    private static final CourseManager courseManager =
            new CourseManager();

    private static final EnrollmentManager enrollmentManager =
            new EnrollmentManager();

    private static final MarksManager marksManager =
            new MarksManager();

    private static final FileManager fileManager =
            new FileManager();

    public static void main(String[] args) {

        loadData();

        int choice;

        do {

            displayMainMenu();
            choice = readInteger("Choose an option: ");

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    updateStudent();
                    break;

                case 4:
                    deleteStudent();
                    break;

                case 5:
                    addCourse();
                    break;

                case 6:
                    viewCourses();
                    break;

                case 7:
                    updateCourse();
                    break;

                case 8:
                    deleteCourse();
                    break;

                case 9:
                    enrollStudent();
                    break;

                case 10:
                    viewEnrollments();
                    break;

                case 11:
                    captureMark();
                    break;

                case 12:
                    updateMark();
                    break;

                case 13:
                    removeMark();
                    break;

                case 14:
                    viewStudentResults();
                    break;

                case 15:
                    completeEnrollment();
                    break;

                case 16:
                    dropEnrollment();
                    break;

                case 17:
                    displayStatistics();
                    break;

                case 18:
                    saveData();
                    break;

                case 19:
                    saveData();
                    System.out.println(
                            "Thank you for using StudentSphere.");
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose 1 to 19.");
            }

        } while (choice != 19);

        input.close();
    }

    // ==============================
    // Main Menu
    // ==============================

    private static void displayMainMenu() {

        System.out.println();
        System.out.println(
                "========================================");
        System.out.println(
                "          STUDENTSPHERE SYSTEM");
        System.out.println(
                "========================================");

        System.out.println("1.  Add Student");
        System.out.println("2.  View Students");
        System.out.println("3.  Update Student");
        System.out.println("4.  Delete Student");

        System.out.println("5.  Add Course");
        System.out.println("6.  View Courses");
        System.out.println("7.  Update Course");
        System.out.println("8.  Delete Course");

        System.out.println("9.  Enroll Student");
        System.out.println("10. View Enrollments");

        System.out.println("11. Capture Mark");
        System.out.println("12. Update Mark");
        System.out.println("13. Remove Mark");
        System.out.println("14. View Student Results");

        System.out.println("15. Complete Enrollment");
        System.out.println("16. Drop Enrollment");

        System.out.println("17. View Statistics");
        System.out.println("18. Save Data");
        System.out.println("19. Exit");

        System.out.println(
                "========================================");
    }

    // ==============================
    // Student Methods
    // ==============================

    private static void addStudent() {

        System.out.println("\n===== ADD STUDENT =====");

        String studentID =
                readRequiredText("Student ID: ");

        if (studentManager.findStudentByID(
                studentID) != null) {

            System.out.println(
                    "A student with that ID already exists.");

            return;
        }

        String firstName =
                readRequiredText("First name: ");

        String lastName =
                readRequiredText("Last name: ");

        String email =
                readRequiredText("Email: ");

        Student student =
                new Student(
                        studentID,
                        firstName,
                        lastName,
                        email
                );

        if (studentManager.addStudent(student)) {

            System.out.println(
                    "Student added successfully.");

        } else {

            System.out.println(
                    "Student could not be added.");
        }
    }

    private static void viewStudents() {

        studentManager.displayAllStudents();
    }

    private static void updateStudent() {

        System.out.println("\n===== UPDATE STUDENT =====");

        String studentID =
                readRequiredText("Student ID: ");

        Student student =
                studentManager.findStudentByID(
                        studentID);

        if (student == null) {

            System.out.println("Student not found.");
            return;
        }

        String firstName =
                readRequiredText("New first name: ");

        String lastName =
                readRequiredText("New last name: ");

        String email =
                readRequiredText("New email: ");

        Student updatedStudent =
                new Student(
                        studentID,
                        firstName,
                        lastName,
                        email
                );

        if (studentManager.updateStudent(
                updatedStudent)) {

            System.out.println(
                    "Student updated successfully.");

        } else {

            System.out.println(
                    "Student could not be updated.");
        }
    }

    private static void deleteStudent() {

        System.out.println("\n===== DELETE STUDENT =====");

        String studentID =
                readRequiredText("Student ID: ");

        if (!enrollmentManager
                .findEnrollmentsByStudent(studentID)
                .isEmpty()) {

            System.out.println(
                    "This student has enrollment records.");

            System.out.println(
                    "Remove the enrollment records first.");

            return;
        }

        if (studentManager.removeStudent(studentID)) {

            System.out.println(
                    "Student deleted successfully.");

        } else {

            System.out.println("Student not found.");
        }
    }

    // ==============================
    // Course Methods
    // ==============================

    private static void addCourse() {

        System.out.println("\n===== ADD COURSE =====");

        String courseCode =
                readRequiredText("Course code: ");

        if (courseManager.findCourseByCode(
                courseCode) != null) {

            System.out.println(
                    "A course with that code already exists.");

            return;
        }

        String courseName =
                readRequiredText("Course name: ");

        String description =
                readRequiredText("Description: ");

        int credits =
                readPositiveInteger("Credits: ");

        String lecturer =
                readRequiredText("Lecturer: ");

        int maxStudents =
                readPositiveInteger(
                        "Maximum number of students: ");

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

            System.out.println(
                    "Course added successfully.");

        } else {

            System.out.println(
                    "Course could not be added.");
        }
    }

    private static void viewCourses() {

        courseManager.displayAllCourses();
    }

    private static void updateCourse() {

        System.out.println("\n===== UPDATE COURSE =====");

        String courseCode =
                readRequiredText("Course code: ");

        Course course =
                courseManager.findCourseByCode(
                        courseCode);

        if (course == null) {

            System.out.println("Course not found.");
            return;
        }

        String courseName =
                readRequiredText("New course name: ");

        String description =
                readRequiredText("New description: ");

        int credits =
                readPositiveInteger("New credits: ");

        String lecturer =
                readRequiredText("New lecturer: ");

        int maxStudents =
                readPositiveInteger(
                        "New maximum students: ");

        boolean updated =
                courseManager.updateCourse(
                        courseCode,
                        courseName,
                        description,
                        credits,
                        lecturer,
                        maxStudents
                );

        if (updated) {

            System.out.println(
                    "Course updated successfully.");

        } else {

            System.out.println(
                    "Course could not be updated.");

            System.out.println(
                    "Check that the capacity is not below "
                            + "the current enrollment count.");
        }
    }

    private static void deleteCourse() {

        System.out.println("\n===== DELETE COURSE =====");

        String courseCode =
                readRequiredText("Course code: ");

        if (!enrollmentManager
                .findEnrollmentsByCourse(courseCode)
                .isEmpty()) {

            System.out.println(
                    "This course has enrollment records.");

            System.out.println(
                    "Remove the enrollment records first.");

            return;
        }

        if (courseManager.removeCourse(courseCode)) {

            System.out.println(
                    "Course deleted successfully.");

        } else {

            System.out.println(
                    "Course not found or still has students.");
        }
    }

    // ==============================
    // Enrollment Methods
    // ==============================

    private static void enrollStudent() {

        System.out.println("\n===== ENROLL STUDENT =====");

        String studentID =
                readRequiredText("Student ID: ");

        Student student =
                studentManager.findStudentByID(
                        studentID);

        if (student == null) {

            System.out.println("Student not found.");
            return;
        }

        String courseCode =
                readRequiredText("Course code: ");

        Course course =
                courseManager.findCourseByCode(
                        courseCode);

        if (course == null) {

            System.out.println("Course not found.");
            return;
        }

        String academicYear =
                readRequiredText("Academic year: ");

        String semester =
                readRequiredText("Semester: ");

        Enrollment enrollment =
                enrollmentManager.enrollStudent(
                        student,
                        course,
                        academicYear,
                        semester
                );

        if (enrollment != null) {

            System.out.println(
                    "Enrollment successful.");

            System.out.println(
                    "Enrollment ID: "
                            + enrollment.getEnrollmentID());

        } else {

            System.out.println(
                    "Enrollment failed.");

            System.out.println(
                    "The student may already be enrolled "
                            + "or the course may be full.");
        }
    }

    private static void viewEnrollments() {

        enrollmentManager.displayAllEnrollments();
    }

    private static void completeEnrollment() {

        System.out.println(
                "\n===== COMPLETE ENROLLMENT =====");

        String enrollmentID =
                readRequiredText("Enrollment ID: ");

        Enrollment enrollment =
                enrollmentManager.findEnrollmentByID(
                        enrollmentID);

        if (enrollment == null) {

            System.out.println(
                    "Enrollment not found.");

            return;
        }

        if (!enrollment.getStudent()
                .hasMarksForCourse(
                        enrollment.getCourse())) {

            System.out.println(
                    "No marks have been captured "
                            + "for this course.");

            return;
        }

        String remarks =
                readOptionalText("Remarks: ");

        boolean completed =
                enrollmentManager
                        .completeEnrollmentFromMarks(
                                enrollmentID,
                                remarks
                        );

        if (completed) {

            System.out.println(
                    "Enrollment completed successfully.");

            System.out.printf(
                    "Final grade: %.2f%%%n",
                    enrollment.getFinalGrade());

            System.out.println(
                    "Result: " + enrollment.getResult());

        } else {

            System.out.println(
                    "Enrollment could not be completed.");
        }
    }

    private static void dropEnrollment() {

        System.out.println("\n===== DROP ENROLLMENT =====");

        String enrollmentID =
                readRequiredText("Enrollment ID: ");

        String remarks =
                readOptionalText(
                        "Reason for dropping: ");

        if (enrollmentManager.dropEnrollment(
                enrollmentID,
                remarks)) {

            System.out.println(
                    "Enrollment dropped successfully.");

        } else {

            System.out.println(
                    "Enrollment not found "
                            + "or is no longer active.");
        }
    }

    // ==============================
    // Mark Methods
    // ==============================

    private static void captureMark() {

        System.out.println("\n===== CAPTURE MARK =====");

        Student student =
                findStudentFromInput();

        if (student == null) {
            return;
        }

        Course course =
                findCourseFromInput();

        if (course == null) {
            return;
        }

        Enrollment enrollment =
                enrollmentManager.findActiveEnrollment(
                        student.getStudentID(),
                        course.getCourseCode());

        if (enrollment == null) {

            System.out.println(
                    "The student is not actively enrolled "
                            + "in this course.");

            return;
        }

        double mark =
                readMark("Enter mark from 0 to 100: ");

        if (marksManager.recordMark(
                enrollment,
                mark)) {

            System.out.println(
                    "Mark recorded successfully.");

            System.out.printf(
                    "Current course average: %.2f%%%n",
                    student.calculateCourseAverage(course));

        } else {

            System.out.println(
                    "Mark could not be recorded.");
        }
    }

    private static void updateMark() {

        System.out.println("\n===== UPDATE MARK =====");

        Student student =
                findStudentFromInput();

        if (student == null) {
            return;
        }

        Course course =
                findCourseFromInput();

        if (course == null) {
            return;
        }

        if (!student.hasMarksForCourse(course)) {

            System.out.println(
                    "No marks found for this course.");

            return;
        }

        displayCourseMarks(student, course);

        int assessmentNumber =
                readPositiveInteger(
                        "Assessment number to update: ");

        double newMark =
                readMark("New mark: ");

        if (marksManager.updateMark(
                student,
                course,
                assessmentNumber,
                newMark)) {

            System.out.println(
                    "Mark updated successfully.");

        } else {

            System.out.println(
                    "Invalid assessment number or mark.");
        }
    }

    private static void removeMark() {

        System.out.println("\n===== REMOVE MARK =====");

        Student student =
                findStudentFromInput();

        if (student == null) {
            return;
        }

        Course course =
                findCourseFromInput();

        if (course == null) {
            return;
        }

        if (!student.hasMarksForCourse(course)) {

            System.out.println(
                    "No marks found for this course.");

            return;
        }

        displayCourseMarks(student, course);

        int assessmentNumber =
                readPositiveInteger(
                        "Assessment number to remove: ");

        if (marksManager.removeMark(
                student,
                course,
                assessmentNumber)) {

            System.out.println(
                    "Mark removed successfully.");

        } else {

            System.out.println(
                    "Invalid assessment number.");
        }
    }

    private static void viewStudentResults() {

        System.out.println(
                "\n===== STUDENT RESULTS =====");

        String studentID =
                readRequiredText("Student ID: ");

        Student student =
                studentManager.findStudentByID(
                        studentID);

        marksManager.displayStudentResults(student);
    }

    private static void displayCourseMarks(
            Student student,
            Course course) {

        System.out.println(
                "\nMarks for "
                        + course.getCourseCode()
                        + ":");

        for (int index = 0;
             index < student
                     .getMarksForCourse(course)
                     .size();
             index++) {

            System.out.printf(
                    "%d. %.2f%n",
                    index + 1,
                    student
                            .getMarksForCourse(course)
                            .get(index)
            );
        }
    }

    // ==============================
    // Statistics
    // ==============================

    private static void displayStatistics() {

        System.out.println();
        System.out.println(
                "========================================");
        System.out.println(
                "             SYSTEM STATISTICS");
        System.out.println(
                "========================================");

        System.out.println(
                "Total Students    : "
                        + studentManager
                        .getStudentCount());

        System.out.println(
                "Total Courses     : "
                        + courseManager
                        .getCourseCount());

        System.out.println(
                "Total Enrollments : "
                        + enrollmentManager
                        .getEnrollmentCount());

        System.out.println(
                "Active            : "
                        + enrollmentManager
                        .getActiveEnrollmentCount());

        System.out.println(
                "Completed         : "
                        + enrollmentManager
                        .getCompletedEnrollmentCount());

        System.out.println(
                "Dropped           : "
                        + enrollmentManager
                        .getDroppedEnrollmentCount());

        System.out.printf(
                "Class Average     : %.2f%%%n",
                studentManager
                        .calculateClassAverage());

        System.out.printf(
                "Completion Rate   : %.2f%%%n",
                enrollmentManager
                        .calculateCompletionRate());

        System.out.printf(
                "Dropout Rate      : %.2f%%%n",
                enrollmentManager
                        .calculateDropoutRate());

        Student topStudent =
                studentManager.getTopStudent();

        if (topStudent != null) {

            System.out.println(
                    "Top Student       : "
                            + topStudent.getStudentID()
                            + " - "
                            + topStudent.getFirstName()
                            + " "
                            + topStudent.getLastName());
        }

        Course popularCourse =
                courseManager.getMostPopularCourse();

        if (popularCourse != null) {

            System.out.println(
                    "Popular Course    : "
                            + popularCourse.getCourseCode()
                            + " - "
                            + popularCourse.getCourseName());
        }

        System.out.println(
                "========================================");
    }

    // ==============================
    // File Methods
    // ==============================

    private static void loadData() {

        System.out.println(
                "Loading StudentSphere data...");

        fileManager.loadAllData(
                studentManager,
                courseManager,
                enrollmentManager
        );

        System.out.println(
                "Data loading completed.");
    }

    private static void saveData() {

        boolean saved =
                fileManager.saveAllData(
                        studentManager,
                        courseManager,
                        enrollmentManager
                );

        if (saved) {

            System.out.println(
                    "All data saved successfully.");

        } else {

            System.out.println(
                    "Some data could not be saved.");
        }
    }

    // ==============================
    // Search Helpers
    // ==============================

    private static Student findStudentFromInput() {

        String studentID =
                readRequiredText("Student ID: ");

        Student student =
                studentManager.findStudentByID(
                        studentID);

        if (student == null) {

            System.out.println("Student not found.");
        }

        return student;
    }

    private static Course findCourseFromInput() {

        String courseCode =
                readRequiredText("Course code: ");

        Course course =
                courseManager.findCourseByCode(
                        courseCode);

        if (course == null) {

            System.out.println("Course not found.");
        }

        return course;
    }

    // ==============================
    // Input Validation
    // ==============================

    private static String readRequiredText(
            String message) {

        String value;

        do {

            System.out.print(message);
            value = input.nextLine().trim();

            if (value.isEmpty()) {

                System.out.println(
                        "This field cannot be empty.");
            }

        } while (value.isEmpty());

        return value;
    }

    private static String readOptionalText(
            String message) {

        System.out.print(message);

        return input.nextLine().trim();
    }

    private static int readInteger(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                int value =
                        Integer.parseInt(
                                input.nextLine().trim());

                return value;

            } catch (NumberFormatException exception) {

                System.out.println(
                        "Enter a valid whole number.");
            }
        }
    }

    private static int readPositiveInteger(
            String message) {

        while (true) {

            int value = readInteger(message);

            if (value > 0) {
                return value;
            }

            System.out.println(
                    "Enter a number greater than zero.");
        }
    }

    private static double readMark(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                double mark =
                        Double.parseDouble(
                                input.nextLine().trim());

                if (mark >= 0 && mark <= 100) {
                    return mark;
                }

                System.out.println(
                        "The mark must be between 0 and 100.");

            } catch (NumberFormatException exception) {

                System.out.println(
                        "Enter a valid numerical mark.");
            }
        }
    }
}