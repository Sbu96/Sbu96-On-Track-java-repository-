public class ReportGenerator {

    // ==============================
    // Overall System Summary
    // ==============================

    public static void generateSummary(
            StudentManager studentManager,
            CourseManager courseManager,
            EnrollmentManager enrollmentManager) {

        System.out.println();
        System.out.println(
                "========================================");
        System.out.println(
                "          STUDENTSPHERE REPORT");
        System.out.println(
                "========================================");

        System.out.println(
                "Total Students    : "
                        + studentManager.getStudentCount());

        System.out.println(
                "Total Courses     : "
                        + courseManager.getCourseCount());

        System.out.println(
                "Total Enrollments : "
                        + enrollmentManager.getEnrollmentCount());

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
                studentManager.calculateClassAverage());

        System.out.printf(
                "Completion Rate   : %.2f%%%n",
                enrollmentManager.calculateCompletionRate());

        System.out.printf(
                "Dropout Rate      : %.2f%%%n",
                enrollmentManager.calculateDropoutRate());

        displayTopStudent(studentManager);
        displayMostPopularCourse(courseManager);

        System.out.println(
                "========================================");
    }

    // ==============================
    // Student Report
    // ==============================

    public static void generateStudentReport(
            Student student,
            MarksManager marksManager) {

        if (student == null) {

            System.out.println("Student not found.");
            return;
        }

        marksManager.displayStudentResults(student);
    }

    // ==============================
    // Course Report
    // ==============================

    public static void generateCourseReport(
            Course course,
            MarksManager marksManager) {

        if (course == null) {

            System.out.println("Course not found.");
            return;
        }

        marksManager.displayCourseResults(course);
    }

    // ==============================
    // Enrollment Report
    // ==============================

    public static void generateEnrollmentReport(
            EnrollmentManager enrollmentManager) {

        System.out.println();
        System.out.println(
                "========================================");
        System.out.println(
                "          ENROLLMENT REPORT");
        System.out.println(
                "========================================");

        enrollmentManager.displayAllEnrollments();

        System.out.println();
        System.out.println(
                "Total Enrollments : "
                        + enrollmentManager.getEnrollmentCount());

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
                "Completion Rate   : %.2f%%%n",
                enrollmentManager.calculateCompletionRate());

        System.out.printf(
                "Dropout Rate      : %.2f%%%n",
                enrollmentManager.calculateDropoutRate());

        System.out.println(
                "========================================");
    }

    // ==============================
    // Top Student
    // ==============================

    private static void displayTopStudent(
            StudentManager studentManager) {

        Student topStudent =
                studentManager.getTopStudent();

        if (topStudent == null) {

            System.out.println(
                    "Top Student       : None");

            return;
        }

        System.out.println(
                "Top Student       : "
                        + topStudent.getStudentID()
                        + " - "
                        + topStudent.getFirstName()
                        + " "
                        + topStudent.getLastName());

        System.out.printf(
                "Top Average       : %.2f%%%n",
                topStudent.calculateAverage());
    }

    // ==============================
    // Most Popular Course
    // ==============================

    private static void displayMostPopularCourse(
            CourseManager courseManager) {

        Course popularCourse =
                courseManager.getMostPopularCourse();

        if (popularCourse == null) {

            System.out.println(
                    "Popular Course    : None");

            return;
        }

        System.out.println(
                "Popular Course    : "
                        + popularCourse.getCourseCode()
                        + " - "
                        + popularCourse.getCourseName());

        System.out.println(
                "Enrollment Count  : "
                        + popularCourse.getEnrollmentCount());
    }
}