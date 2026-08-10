import java.util.regex.Pattern;

public class InputValidator {

    // ==============================
    // Regular Expression Patterns
    // ==============================

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
            );

    private static final Pattern STUDENT_ID_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9_-]{3,20}$"
            );

    private static final Pattern COURSE_CODE_PATTERN =
            Pattern.compile(
                    "^[A-Za-z]{2,10}[0-9]{2,6}$"
            );

    // ==============================
    // Student Validation
    // ==============================

    public static boolean isValidStudentID(
            String studentID) {

        if (studentID == null) {
            return false;
        }

        return STUDENT_ID_PATTERN
                .matcher(studentID.trim())
                .matches();
    }

    public static boolean isValidName(
            String name) {

        if (name == null) {
            return false;
        }

        String cleanedName = name.trim();

        if (cleanedName.length() < 2
                || cleanedName.length() > 50) {

            return false;
        }

        for (int index = 0;
             index < cleanedName.length();
             index++) {

            char character =
                    cleanedName.charAt(index);

            if (!Character.isLetter(character)
                    && character != ' '
                    && character != '-'
                    && character != '\'') {

                return false;
            }
        }

        return true;
    }

    public static boolean isValidEmail(
            String email) {

        if (email == null) {
            return false;
        }

        return EMAIL_PATTERN
                .matcher(email.trim())
                .matches();
    }

    // ==============================
    // Course Validation
    // ==============================

    public static boolean isValidCourseCode(
            String courseCode) {

        if (courseCode == null) {
            return false;
        }

        return COURSE_CODE_PATTERN
                .matcher(courseCode.trim())
                .matches();
    }

    public static boolean isValidCourseName(
            String courseName) {

        if (courseName == null) {
            return false;
        }

        String cleanedName =
                courseName.trim();

        return cleanedName.length() >= 3
                && cleanedName.length() <= 100;
    }

    public static boolean isValidDescription(
            String description) {

        if (description == null) {
            return false;
        }

        return description.trim().length()
                <= 500;
    }

    public static boolean isValidCredits(
            int credits) {

        return credits > 0
                && credits <= 60;
    }

    public static boolean isValidCapacity(
            int maxStudents) {

        return maxStudents > 0
                && maxStudents <= 1000;
    }

    public static boolean isValidLecturerName(
        String lecturer) {

    if (lecturer == null) {
        return false;
    }

    String cleanedName =
            lecturer.trim();

    if (cleanedName.length() < 2
            || cleanedName.length() > 100) {

        return false;
    }

    for (int index = 0;
         index < cleanedName.length();
         index++) {

        char character =
                cleanedName.charAt(index);

        if (!Character.isLetter(character)
                && character != ' '
                && character != '-'
                && character != '\''
                && character != '.') {

            return false;
        }
    }

    return true;
}

    // ==============================
    // Enrollment Validation
    // ==============================

    public static boolean isValidAcademicYear(
            String academicYear) {

        if (academicYear == null) {
            return false;
        }

        String cleanedYear =
                academicYear.trim();

        if (!cleanedYear.matches("\\d{4}")) {
            return false;
        }

        int year;

        try {

            year = Integer.parseInt(
                    cleanedYear);

        } catch (NumberFormatException exception) {

            return false;
        }

        return year >= 2000
                && year <= 2100;
    }

    public static boolean isValidSemester(
            String semester) {

        if (semester == null) {
            return false;
        }

        String cleanedSemester =
                semester.trim();

        return cleanedSemester
                .equalsIgnoreCase("Semester 1")
                || cleanedSemester
                .equalsIgnoreCase("Semester 2")
                || cleanedSemester
                .equalsIgnoreCase("Semester 3")
                || cleanedSemester
                .equalsIgnoreCase("Year");
    }

    public static boolean isValidEnrollmentID(
            String enrollmentID) {

        if (enrollmentID == null) {
            return false;
        }

        return enrollmentID
                .trim()
                .matches("^ENR\\d{4,}$");
    }

    // ==============================
    // Marks Validation
    // ==============================

    public static boolean isValidMark(
            double mark) {

        return mark >= 0
                && mark <= 100;
    }

    public static boolean isValidAssessmentNumber(
            int assessmentNumber) {

        return assessmentNumber > 0;
    }

    // ==============================
    // General Text Validation
    // ==============================

    public static boolean isNotEmpty(
            String value) {

        return value != null
                && !value.trim().isEmpty();
    }

    public static boolean isWithinLength(
            String value,
            int minimumLength,
            int maximumLength) {

        if (value == null) {
            return false;
        }

        int length =
                value.trim().length();

        return length >= minimumLength
                && length <= maximumLength;
    }

    // ==============================
    // Validation Messages
    // ==============================

    public static String getStudentIDMessage() {

        return "Student ID must contain 3 to 20 "
                + "letters, numbers, underscores, "
                + "or hyphens.";
    }

    public static String getNameMessage() {

        return "Name must contain 2 to 50 characters "
                + "and may only use letters, spaces, "
                + "hyphens, or apostrophes.";
    }

    public static String getEmailMessage() {

        return "Enter a valid email address, "
                + "for example student@email.com.";
    }

    public static String getCourseCodeMessage() {

        return "Course code must begin with letters "
                + "and end with numbers, for example "
                + "JAVA101.";
    }

    public static String getAcademicYearMessage() {

        return "Academic year must be a four-digit "
                + "year between 2000 and 2100.";
    }

    public static String getSemesterMessage() {

        return "Semester must be Semester 1, "
                + "Semester 2, Semester 3, or Year.";
    }

    public static String getMarkMessage() {

        return "Mark must be between 0 and 100.";
    }
}