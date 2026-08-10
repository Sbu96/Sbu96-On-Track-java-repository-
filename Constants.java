public final class Constants {

    // Prevent objects from being created
    private Constants() {
    }

    // ==============================
    // Application Information
    // ==============================

    public static final String APP_NAME =
            "StudentSphere";

    public static final String APP_VERSION =
            "2.0";

    public static final String APP_TITLE =
            APP_NAME + " Version " + APP_VERSION;

    // ==============================
    // File Names
    // ==============================

    public static final String STUDENT_FILE =
            "students.txt";

    public static final String COURSE_FILE =
            "courses.txt";

    public static final String ENROLLMENT_FILE =
            "enrollments.txt";

    public static final String MARKS_FILE =
            "marks.txt";

    // ==============================
    // File Formatting
    // ==============================

    public static final String OUTPUT_DELIMITER =
            "|";

    public static final String INPUT_DELIMITER =
            "\\|";

    // ==============================
    // Mark Limits
    // ==============================

    public static final double MIN_MARK =
            0.0;

    public static final double MAX_MARK =
            100.0;

    public static final double PASS_MARK =
            50.0;

    // ==============================
    // Grade Boundaries
    // ==============================

    public static final double DISTINCTION_MARK =
            75.0;

    public static final double A_GRADE =
            75.0;

    public static final double B_GRADE =
            70.0;

    public static final double C_GRADE =
            60.0;

    public static final double D_GRADE =
            50.0;

    // ==============================
    // Student Limits
    // ==============================

    public static final int MIN_STUDENT_ID_LENGTH =
            3;

    public static final int MAX_STUDENT_ID_LENGTH =
            20;

    public static final int MIN_NAME_LENGTH =
            2;

    public static final int MAX_NAME_LENGTH =
            50;

    // ==============================
    // Course Limits
    // ==============================

    public static final int MIN_CREDITS =
            1;

    public static final int MAX_CREDITS =
            60;

    public static final int MIN_COURSE_CAPACITY =
            1;

    public static final int MAX_COURSE_CAPACITY =
            1000;

    public static final int MAX_DESCRIPTION_LENGTH =
            500;

    // ==============================
    // Academic Year Limits
    // ==============================

    public static final int MIN_ACADEMIC_YEAR =
            2000;

    public static final int MAX_ACADEMIC_YEAR =
            2100;

    // ==============================
    // Semester Values
    // ==============================

    public static final String SEMESTER_ONE =
            "Semester 1";

    public static final String SEMESTER_TWO =
            "Semester 2";

    public static final String SEMESTER_THREE =
            "Semester 3";

    public static final String FULL_YEAR =
            "Year";

    // ==============================
    // Enrollment ID Formatting
    // ==============================

    public static final String ENROLLMENT_PREFIX =
            "ENR";

    public static final int ENROLLMENT_ID_DIGITS =
            4;

    // ==============================
    // Result Text
    // ==============================

    public static final String RESULT_PASS =
            "PASS";

    public static final String RESULT_FAIL =
            "FAIL";

    public static final String RESULT_NOT_FINALISED =
            "NOT FINALISED";

    // ==============================
    // Grade Symbols
    // ==============================

    public static final String GRADE_A =
            "A";

    public static final String GRADE_B =
            "B";

    public static final String GRADE_C =
            "C";

    public static final String GRADE_D =
            "D";

    public static final String GRADE_F =
            "F";

    public static final String GRADE_NOT_AVAILABLE =
            "N/A";

    // ==============================
    // General Messages
    // ==============================

    public static final String MESSAGE_NOT_FOUND =
            "Record not found.";

    public static final String MESSAGE_INVALID_INPUT =
            "Invalid input.";

    public static final String MESSAGE_SAVE_SUCCESS =
            "All data saved successfully.";

    public static final String MESSAGE_SAVE_FAILURE =
            "Some data could not be saved.";

    public static final String MESSAGE_LOAD_COMPLETE =
            "Data loading completed.";
}