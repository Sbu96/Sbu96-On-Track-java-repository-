public class TestStudentSphere {

    public static void main(String[] args) {

        StudentManager studentManager =
                new StudentManager();

        CourseManager courseManager =
                new CourseManager();

        EnrollmentManager enrollmentManager =
                new EnrollmentManager();

        MarksManager marksManager =
                new MarksManager();

        // ==============================
        // Create Students
        // ==============================

        Student student1 =
                new Student(
                        "ST001",
                        "John",
                        "Smith",
                        "johnsmith@email.com"
                );

        Student student2 =
                new Student(
                        "ST002",
                        "Mary",
                        "Dlamini",
                        "marydlamini@email.com"
                );

        studentManager.addStudent(student1);
        studentManager.addStudent(student2);

        // ==============================
        // Create Courses
        // ==============================

        Course javaCourse =
                new Course(
                        "JAVA101",
                        "Java Programming",
                        "Introduction to Java and OOP",
                        16,
                        "Mr Dube",
                        30
                );

        Course databaseCourse =
                new Course(
                        "DBS101",
                        "Database Systems",
                        "Introduction to relational databases",
                        16,
                        "Ms Nkosi",
                        25
                );

        courseManager.addCourse(javaCourse);
        courseManager.addCourse(databaseCourse);

        // ==============================
        // Create Enrollments
        // ==============================

        Enrollment enrollment1 =
                enrollmentManager.enrollStudent(
                        student1,
                        javaCourse,
                        "2026",
                        "Semester 1"
                );

        Enrollment enrollment2 =
                enrollmentManager.enrollStudent(
                        student2,
                        javaCourse,
                        "2026",
                        "Semester 1"
                );

        Enrollment enrollment3 =
                enrollmentManager.enrollStudent(
                        student1,
                        databaseCourse,
                        "2026",
                        "Semester 1"
                );

        // ==============================
        // Record Java Marks
        // ==============================

        marksManager.recordMark(
                student1,
                javaCourse,
                75
        );

        marksManager.recordMark(
                student1,
                javaCourse,
                82
        );

        marksManager.recordMark(
                student1,
                javaCourse,
                68
        );

        marksManager.recordMark(
                student2,
                javaCourse,
                55
        );

        marksManager.recordMark(
                student2,
                javaCourse,
                64
        );

        marksManager.recordMark(
                student2,
                javaCourse,
                60
        );

        // ==============================
        // Record Database Marks
        // ==============================

        marksManager.recordMark(
                student1,
                databaseCourse,
                80
        );

        marksManager.recordMark(
                student1,
                databaseCourse,
                74
        );

        marksManager.recordMark(
                student1,
                databaseCourse,
                91
        );

        // ==============================
        // Display Students
        // ==============================

        System.out.println();
        System.out.println(
                "===== ALL STUDENTS =====");

        studentManager.displayAllStudents();

        // ==============================
        // Display Courses
        // ==============================

        System.out.println();
        System.out.println(
                "===== ALL COURSES =====");

        courseManager.displayAllCourses();

        // ==============================
        // Display Enrollments
        // ==============================

        System.out.println();
        System.out.println(
                "===== ALL ENROLLMENTS =====");

        enrollmentManager.displayAllEnrollments();

        // ==============================
        // Display Student Reports
        // ==============================

        marksManager.displayStudentResults(
                student1);

        marksManager.displayStudentResults(
                student2);

        // ==============================
        // Display Course Results
        // ==============================

        marksManager.displayCourseResults(
                javaCourse);

        // ==============================
        // Complete Enrollments
        // ==============================

        if (enrollment1 != null) {

            enrollmentManager
                    .completeEnrollmentFromMarks(
                            enrollment1
                                    .getEnrollmentID(),
                            "Java course completed"
                    );
        }

        if (enrollment2 != null) {

            enrollmentManager
                    .completeEnrollmentFromMarks(
                            enrollment2
                                    .getEnrollmentID(),
                            "Java course completed"
                    );
        }

        if (enrollment3 != null) {

            enrollmentManager
                    .completeEnrollmentFromMarks(
                            enrollment3
                                    .getEnrollmentID(),
                            "Database course completed"
                    );
        }

        // ==============================
        // Display Completed Enrollments
        // ==============================

        System.out.println();
        System.out.println(
                "===== COMPLETED ENROLLMENTS =====");

        enrollmentManager.displayAllEnrollments();

        // ==============================
        // Display Statistics
        // ==============================

        System.out.println();
        System.out.println(
                "===== SYSTEM STATISTICS =====");

        System.out.println(
                "Students: "
                        + studentManager
                        .getStudentCount());

        System.out.println(
                "Courses: "
                        + courseManager
                        .getCourseCount());

        System.out.println(
                "Enrollments: "
                        + enrollmentManager
                        .getEnrollmentCount());

        System.out.println(
                "Active enrollments: "
                        + enrollmentManager
                        .getActiveEnrollmentCount());

        System.out.println(
                "Completed enrollments: "
                        + enrollmentManager
                        .getCompletedEnrollmentCount());

        System.out.printf(
                "Class average: %.2f%%%n",
                studentManager
                        .calculateClassAverage());

        System.out.printf(
                "Completion rate: %.2f%%%n",
                enrollmentManager
                        .calculateCompletionRate());

        // ==============================
        // Test File Saving
        // ==============================

        FileManager fileManager =
                new FileManager();

        boolean saved =
                fileManager.saveAllData(
                        studentManager,
                        courseManager,
                        enrollmentManager
                );

        if (saved) {

            System.out.println();
            System.out.println(
                    "All test data saved successfully.");

        } else {

            System.out.println();
            System.out.println(
                    "Some test data could not be saved.");
        }
    }
}