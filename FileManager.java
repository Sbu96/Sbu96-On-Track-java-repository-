import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileManager {

    // ==============================
    // Save All Data
    // ==============================

    public boolean saveAllData(
            StudentManager studentManager,
            CourseManager courseManager,
            EnrollmentManager enrollmentManager) {

        if (studentManager == null
                || courseManager == null
                || enrollmentManager == null) {

            return false;
        }

        boolean studentsSaved =
                saveStudents(
                        studentManager.getStudents());

        boolean coursesSaved =
                saveCourses(
                        courseManager.getCourses());

        boolean enrollmentsSaved =
                saveEnrollments(
                        enrollmentManager.getEnrollments());

        boolean marksSaved =
                saveMarks(
                        studentManager.getStudents());

        return studentsSaved
                && coursesSaved
                && enrollmentsSaved
                && marksSaved;
    }

    // ==============================
    // Load All Data
    // ==============================

    public boolean loadAllData(
            StudentManager studentManager,
            CourseManager courseManager,
            EnrollmentManager enrollmentManager) {

        if (studentManager == null
                || courseManager == null
                || enrollmentManager == null) {

            return false;
        }

        /*
         * Clear enrollments first because active
         * enrollments are connected to courses.
         */
        enrollmentManager.clear();
        studentManager.clear();
        courseManager.clear();

        boolean studentsLoaded =
                loadStudents(studentManager);

        boolean coursesLoaded =
                loadCourses(courseManager);

        boolean enrollmentsLoaded =
                loadEnrollments(
                        enrollmentManager,
                        studentManager,
                        courseManager
                );

        boolean marksLoaded =
                loadMarks(
                        studentManager,
                        courseManager
                );

        return studentsLoaded
                && coursesLoaded
                && enrollmentsLoaded
                && marksLoaded;
    }

    // ==============================
    // Save Students
    // ==============================

    public boolean saveStudents(
            List<Student> students) {

        if (students == null) {
            return false;
        }

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     Constants.STUDENT_FILE))) {

            for (Student student : students) {

                if (student == null) {
                    continue;
                }

                writer.write(
                        clean(student.getStudentID())
                                + Constants.OUTPUT_DELIMITER
                                + clean(student.getFirstName())
                                + Constants.OUTPUT_DELIMITER
                                + clean(student.getLastName())
                                + Constants.OUTPUT_DELIMITER
                                + clean(student.getEmail())
                );

                writer.newLine();
            }

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error saving students: "
                            + exception.getMessage());

            return false;
        }
    }

    // ==============================
    // Load Students
    // ==============================

    public boolean loadStudents(
            StudentManager studentManager) {

        if (studentManager == null) {
            return false;
        }

        File file =
                new File(Constants.STUDENT_FILE);

        if (!file.exists()) {
            return true;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split(
                                Constants.INPUT_DELIMITER,
                                -1
                        );

                if (data.length != 4) {

                    System.out.println(
                            "Skipped invalid student record: "
                                    + line);

                    continue;
                }

                String studentID =
                        data[0].trim();

                String firstName =
                        data[1].trim();

                String lastName =
                        data[2].trim();

                String email =
                        data[3].trim();

                if (!InputValidator
                        .isValidStudentID(studentID)
                        || !InputValidator
                        .isValidName(firstName)
                        || !InputValidator
                        .isValidName(lastName)
                        || !InputValidator
                        .isValidEmail(email)) {

                    System.out.println(
                            "Skipped student with invalid data: "
                                    + line);

                    continue;
                }

                Student student =
                        new Student(
                                studentID,
                                firstName,
                                lastName,
                                email
                        );

                if (!studentManager
                        .addStudent(student)) {

                    System.out.println(
                            "Skipped duplicate student: "
                                    + studentID);
                }
            }

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error loading students: "
                            + exception.getMessage());

            return false;
        }
    }

    // ==============================
    // Save Courses
    // ==============================

    public boolean saveCourses(
            List<Course> courses) {

        if (courses == null) {
            return false;
        }

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     Constants.COURSE_FILE))) {

            for (Course course : courses) {

                if (course == null) {
                    continue;
                }

                writer.write(
                        clean(course.getCourseCode())
                                + Constants.OUTPUT_DELIMITER
                                + clean(course.getCourseName())
                                + Constants.OUTPUT_DELIMITER
                                + clean(course.getDescription())
                                + Constants.OUTPUT_DELIMITER
                                + course.getCredits()
                                + Constants.OUTPUT_DELIMITER
                                + clean(course.getLecturer())
                                + Constants.OUTPUT_DELIMITER
                                + course.getMaxStudents()
                );

                writer.newLine();
            }

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error saving courses: "
                            + exception.getMessage());

            return false;
        }
    }

    // ==============================
    // Load Courses
    // ==============================

    public boolean loadCourses(
            CourseManager courseManager) {

        if (courseManager == null) {
            return false;
        }

        File file =
                new File(Constants.COURSE_FILE);

        if (!file.exists()) {
            return true;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split(
                                Constants.INPUT_DELIMITER,
                                -1
                        );

                if (data.length != 6) {

                    System.out.println(
                            "Skipped invalid course record: "
                                    + line);

                    continue;
                }

                try {

                    String courseCode =
                            data[0].trim();

                    String courseName =
                            data[1].trim();

                    String description =
                            data[2].trim();

                    int credits =
                            Integer.parseInt(
                                    data[3].trim());

                    String lecturer =
                            data[4].trim();

                    int maxStudents =
                            Integer.parseInt(
                                    data[5].trim());

                    if (!InputValidator
                            .isValidCourseCode(courseCode)
                            || !InputValidator
                            .isValidCourseName(courseName)
                            || !InputValidator
                            .isValidDescription(description)
                            || !InputValidator
                            .isValidCredits(credits)
                            || !InputValidator
                            .isValidLecturerName(lecturer)
                            || !InputValidator
                            .isValidCapacity(maxStudents)) {

                        System.out.println(
                                "Skipped course with invalid data: "
                                        + line);

                        continue;
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

                    if (!courseManager
                            .addCourse(course)) {

                        System.out.println(
                                "Skipped duplicate course: "
                                        + courseCode);
                    }

                } catch (NumberFormatException exception) {

                    System.out.println(
                            "Skipped course with invalid number: "
                                    + line);
                }
            }

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error loading courses: "
                            + exception.getMessage());

            return false;
        }
    }

    // ==============================
    // Save Enrollments
    // ==============================

    public boolean saveEnrollments(
            List<Enrollment> enrollments) {

        if (enrollments == null) {
            return false;
        }

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     Constants.ENROLLMENT_FILE))) {

            for (Enrollment enrollment : enrollments) {

                if (enrollment == null
                        || !enrollment.isValid()) {

                    continue;
                }

                writer.write(
                        clean(enrollment.getEnrollmentID())
                                + Constants.OUTPUT_DELIMITER
                                + clean(
                                enrollment
                                        .getStudent()
                                        .getStudentID())
                                + Constants.OUTPUT_DELIMITER
                                + clean(
                                enrollment
                                        .getCourse()
                                        .getCourseCode())
                                + Constants.OUTPUT_DELIMITER
                                + enrollment.getEnrollmentDate()
                                + Constants.OUTPUT_DELIMITER
                                + clean(
                                enrollment.getAcademicYear())
                                + Constants.OUTPUT_DELIMITER
                                + clean(
                                enrollment.getSemester())
                                + Constants.OUTPUT_DELIMITER
                                + enrollment
                                .getStatus()
                                .name()
                                + Constants.OUTPUT_DELIMITER
                                + enrollment.getFinalGrade()
                                + Constants.OUTPUT_DELIMITER
                                + clean(
                                enrollment.getRemarks())
                );

                writer.newLine();
            }

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error saving enrollments: "
                            + exception.getMessage());

            return false;
        }
    }

    // ==============================
    // Load Enrollments
    // ==============================

    public boolean loadEnrollments(
            EnrollmentManager enrollmentManager,
            StudentManager studentManager,
            CourseManager courseManager) {

        if (enrollmentManager == null
                || studentManager == null
                || courseManager == null) {

            return false;
        }

        File file =
                new File(Constants.ENROLLMENT_FILE);

        if (!file.exists()) {
            return true;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split(
                                Constants.INPUT_DELIMITER,
                                -1
                        );

                if (data.length != 9) {

                    System.out.println(
                            "Skipped invalid enrollment record: "
                                    + line);

                    continue;
                }

                try {

                    String enrollmentID =
                            data[0].trim();

                    Student student =
                            studentManager
                                    .findStudentByID(
                                            data[1].trim());

                    Course course =
                            courseManager
                                    .findCourseByCode(
                                            data[2].trim());

                    LocalDate enrollmentDate =
                            LocalDate.parse(
                                    data[3].trim());

                    String academicYear =
                            data[4].trim();

                    String semester =
                            data[5].trim();

                    EnrollmentStatus status =
                            EnrollmentStatus.valueOf(
                                    data[6]
                                            .trim()
                                            .toUpperCase()
                            );

                    double finalGrade =
                            Double.parseDouble(
                                    data[7].trim());

                    String remarks =
                            data[8].trim();

                    if (student == null
                            || course == null
                            || !InputValidator
                            .isValidEnrollmentID(
                                    enrollmentID)
                            || !InputValidator
                            .isValidAcademicYear(
                                    academicYear)
                            || !InputValidator
                            .isValidSemester(
                                    semester)
                            || !InputValidator
                            .isValidMark(
                                    finalGrade)) {

                        System.out.println(
                                "Skipped enrollment with invalid data: "
                                        + line);

                        continue;
                    }

                    Enrollment enrollment =
                            new Enrollment(
                                    enrollmentID,
                                    student,
                                    course,
                                    enrollmentDate,
                                    academicYear,
                                    semester,
                                    status,
                                    finalGrade,
                                    remarks
                            );

                    if (!enrollmentManager
                            .addExistingEnrollment(
                                    enrollment)) {

                        System.out.println(
                                "Skipped duplicate or invalid enrollment: "
                                        + enrollmentID);
                    }

                } catch (Exception exception) {

                    System.out.println(
                            "Skipped invalid enrollment data: "
                                    + line);
                }
            }

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error loading enrollments: "
                            + exception.getMessage());

            return false;
        }
    }

    // ==============================
    // Save Marks
    // ==============================

    public boolean saveMarks(
            List<Student> students) {

        if (students == null) {
            return false;
        }

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     Constants.MARKS_FILE))) {

            for (Student student : students) {

                if (student == null) {
                    continue;
                }

                for (Map.Entry<Course,
                        ArrayList<Double>> entry
                        : student.getGrades().entrySet()) {

                    Course course =
                            entry.getKey();

                    ArrayList<Double> marks =
                            entry.getValue();

                    if (course == null
                            || marks == null) {

                        continue;
                    }

                    for (int index = 0;
                         index < marks.size();
                         index++) {

                        double mark =
                                marks.get(index);

                        writer.write(
                                clean(student.getStudentID())
                                        + Constants.OUTPUT_DELIMITER
                                        + clean(course.getCourseCode())
                                        + Constants.OUTPUT_DELIMITER
                                        + (index + 1)
                                        + Constants.OUTPUT_DELIMITER
                                        + mark
                        );

                        writer.newLine();
                    }
                }
            }

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error saving marks: "
                            + exception.getMessage());

            return false;
        }
    }

    // ==============================
    // Load Marks
    // ==============================

    public boolean loadMarks(
            StudentManager studentManager,
            CourseManager courseManager) {

        if (studentManager == null
                || courseManager == null) {

            return false;
        }

        File file =
                new File(Constants.MARKS_FILE);

        if (!file.exists()) {
            return true;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split(
                                Constants.INPUT_DELIMITER,
                                -1
                        );

                if (data.length != 4) {

                    System.out.println(
                            "Skipped invalid mark record: "
                                    + line);

                    continue;
                }

                try {

                    Student student =
                            studentManager
                                    .findStudentByID(
                                            data[0].trim());

                    Course course =
                            courseManager
                                    .findCourseByCode(
                                            data[1].trim());

                    int assessmentNumber =
                            Integer.parseInt(
                                    data[2].trim());

                    double mark =
                            Double.parseDouble(
                                    data[3].trim());

                    if (student == null
                            || course == null
                            || !InputValidator
                            .isValidAssessmentNumber(
                                    assessmentNumber)
                            || !InputValidator
                            .isValidMark(mark)) {

                        System.out.println(
                                "Skipped mark with invalid data: "
                                        + line);

                        continue;
                    }

                    /*
                     * Marks are read in saved assessment
                     * order and added to the course list.
                     */
                    student.addMark(course, mark);

                } catch (NumberFormatException exception) {

                    System.out.println(
                            "Skipped invalid mark value: "
                                    + line);
                }
            }

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error loading marks: "
                            + exception.getMessage());

            return false;
        }
    }

    // ==============================
    // Clean Saved Text
    // ==============================

    private String clean(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace(
                        Constants.OUTPUT_DELIMITER,
                        "/")
                .replace("\n", " ")
                .replace("\r", " ")
                .trim();
    }
}