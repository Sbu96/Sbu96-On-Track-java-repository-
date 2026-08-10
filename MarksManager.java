import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MarksManager {

    // ==============================
    // Record Marks
    // ==============================

    public boolean recordMark(
            Student student,
            Course course,
            double mark) {

        if (student == null || course == null) {
            return false;
        }

        if (!InputValidator.isValidMark(mark)) {
            return false;
        }

        return student.addMark(course, mark);
    }

    public boolean recordMark(
            Enrollment enrollment,
            double mark) {

        if (enrollment == null) {
            return false;
        }

        if (enrollment.getStatus()
                != EnrollmentStatus.ACTIVE) {

            return false;
        }

        return recordMark(
                enrollment.getStudent(),
                enrollment.getCourse(),
                mark
        );
    }

    // ==============================
    // Update Mark
    // ==============================

    public boolean updateMark(
            Student student,
            Course course,
            int assessmentNumber,
            double newMark) {

        if (student == null || course == null) {
            return false;
        }

        if (!InputValidator
                .isValidAssessmentNumber(
                        assessmentNumber)) {

            return false;
        }

        if (!InputValidator.isValidMark(newMark)) {
            return false;
        }

        int assessmentIndex =
                assessmentNumber - 1;

        return student.updateMark(
                course,
                assessmentIndex,
                newMark
        );
    }

    // ==============================
    // Remove Mark
    // ==============================

    public boolean removeMark(
            Student student,
            Course course,
            int assessmentNumber) {

        if (student == null || course == null) {
            return false;
        }

        if (!InputValidator
                .isValidAssessmentNumber(
                        assessmentNumber)) {

            return false;
        }

        int assessmentIndex =
                assessmentNumber - 1;

        return student.removeMark(
                course,
                assessmentIndex
        );
    }

    // ==============================
    // Course Average
    // ==============================

    public double calculateCourseAverage(
            Student student,
            Course course) {

        if (student == null || course == null) {
            return 0;
        }

        return student.calculateCourseAverage(course);
    }

    // ==============================
    // Highest Course Mark
    // ==============================

    public double getHighestCourseMark(
            Student student,
            Course course) {

        if (student == null || course == null) {
            return 0;
        }

        List<Double> marks =
                student.getMarksForCourse(course);

        if (marks.isEmpty()) {
            return 0;
        }

        double highest = marks.get(0);

        for (double mark : marks) {

            if (mark > highest) {
                highest = mark;
            }
        }

        return highest;
    }

    // ==============================
    // Lowest Course Mark
    // ==============================

    public double getLowestCourseMark(
            Student student,
            Course course) {

        if (student == null || course == null) {
            return 0;
        }

        List<Double> marks =
                student.getMarksForCourse(course);

        if (marks.isEmpty()) {
            return 0;
        }

        double lowest = marks.get(0);

        for (double mark : marks) {

            if (mark < lowest) {
                lowest = mark;
            }
        }

        return lowest;
    }

    // ==============================
    // Grade Symbol
    // ==============================

    public String getGradeSymbol(double average) {

        if (average >= Constants.A_GRADE) {
            return Constants.GRADE_A;
        }

        if (average >= Constants.B_GRADE) {
            return Constants.GRADE_B;
        }

        if (average >= Constants.C_GRADE) {
            return Constants.GRADE_C;
        }

        if (average >= Constants.D_GRADE) {
            return Constants.GRADE_D;
        }

        return Constants.GRADE_F;
    }

    // ==============================
    // Result
    // ==============================

    public boolean hasPassed(double average) {

        return average >= Constants.PASS_MARK;
    }

    public String getResult(double average) {

        return hasPassed(average)
                ? Constants.RESULT_PASS
                : Constants.RESULT_FAIL;
    }

    // ==============================
    // Performance Level
    // ==============================

    public String getPerformanceLevel(
            double average) {

        if (average >= Constants.DISTINCTION_MARK) {
            return "Distinction";
        }

        if (average >= Constants.C_GRADE) {
            return "Good";
        }

        if (average >= Constants.PASS_MARK) {
            return "Satisfactory";
        }

        return "Needs Improvement";
    }

    // ==============================
    // Complete Enrollment from Marks
    // ==============================

    public boolean finaliseCourseResult(
            Enrollment enrollment) {

        if (enrollment == null) {
            return false;
        }

        if (enrollment.getStatus()
                != EnrollmentStatus.ACTIVE) {

            return false;
        }

        Student student =
                enrollment.getStudent();

        Course course =
                enrollment.getCourse();

        if (!student.hasMarksForCourse(course)) {
            return false;
        }

        double average =
                student.calculateCourseAverage(course);

        enrollment.setFinalGrade(average);

        enrollment.setRemarks(
                getResult(average)
                        + " - "
                        + getPerformanceLevel(average)
        );

        enrollment.completeEnrollment();

        course.removeStudent(student);

        return true;
    }

    // ==============================
    // Course Class Average
    // ==============================

    public double calculateCourseClassAverage(
            Course course) {

        if (course == null) {
            return 0;
        }

        double total = 0;
        int studentCount = 0;

        for (Student student
                : course.getEnrolledStudents()) {

            if (student.hasMarksForCourse(course)) {

                total +=
                        student.calculateCourseAverage(
                                course);

                studentCount++;
            }
        }

        if (studentCount == 0) {
            return 0;
        }

        return total / studentCount;
    }

    // ==============================
    // Top Student for Course
    // ==============================

    public Student getTopStudentForCourse(
            Course course) {

        if (course == null) {
            return null;
        }

        Student topStudent = null;
        double highestAverage = -1;

        for (Student student
                : course.getEnrolledStudents()) {

            if (!student.hasMarksForCourse(course)) {
                continue;
            }

            double average =
                    student.calculateCourseAverage(
                            course);

            if (average > highestAverage) {

                highestAverage = average;
                topStudent = student;
            }
        }

        return topStudent;
    }

    // ==============================
    // Course Pass Count
    // ==============================

    public int countCoursePasses(
            Course course) {

        if (course == null) {
            return 0;
        }

        int passCount = 0;

        for (Student student
                : course.getEnrolledStudents()) {

            if (student.hasMarksForCourse(course)
                    && hasPassed(
                    student.calculateCourseAverage(
                            course))) {

                passCount++;
            }
        }

        return passCount;
    }

    // ==============================
    // Course Failure Count
    // ==============================

    public int countCourseFailures(
            Course course) {

        if (course == null) {
            return 0;
        }

        int failureCount = 0;

        for (Student student
                : course.getEnrolledStudents()) {

            if (student.hasMarksForCourse(course)
                    && !hasPassed(
                    student.calculateCourseAverage(
                            course))) {

                failureCount++;
            }
        }

        return failureCount;
    }

    // ==============================
    // Course Pass Rate
    // ==============================

    public double calculateCoursePassRate(
            Course course) {

        if (course == null) {
            return 0;
        }

        int studentsWithMarks = 0;

        for (Student student
                : course.getEnrolledStudents()) {

            if (student.hasMarksForCourse(course)) {
                studentsWithMarks++;
            }
        }

        if (studentsWithMarks == 0) {
            return 0;
        }

        return ((double) countCoursePasses(course)
                / studentsWithMarks) * 100;
    }

    // ==============================
    // Student Passed Courses
    // ==============================

    public int countPassedCourses(
            Student student) {

        if (student == null) {
            return 0;
        }

        int count = 0;

        for (Course course
                : student.getGrades().keySet()) {

            double average =
                    student.calculateCourseAverage(
                            course);

            if (hasPassed(average)) {
                count++;
            }
        }

        return count;
    }

    // ==============================
    // Student Failed Courses
    // ==============================

    public int countFailedCourses(
            Student student) {

        if (student == null) {
            return 0;
        }

        int count = 0;

        for (Course course
                : student.getGrades().keySet()) {

            double average =
                    student.calculateCourseAverage(
                            course);

            if (!hasPassed(average)) {
                count++;
            }
        }

        return count;
    }

    // ==============================
    // Display Student Results
    // ==============================

    public void displayStudentResults(
            Student student) {

        if (student == null) {

            System.out.println(
                    "Student not found.");

            return;
        }

        System.out.println();
        System.out.println(
                "======================================");
        System.out.println(
                "          ACADEMIC REPORT");
        System.out.println(
                "======================================");

        System.out.println(
                "Student ID : "
                        + student.getStudentID());

        System.out.println(
                "Student    : "
                        + student.getFirstName()
                        + " "
                        + student.getLastName());

        if (student.getGrades().isEmpty()) {

            System.out.println(
                    "No marks have been captured.");

            System.out.println(
                    "======================================");

            return;
        }

        for (Map.Entry<Course,
                ArrayList<Double>> entry
                : student.getGrades().entrySet()) {

            Course course = entry.getKey();
            ArrayList<Double> marks =
                    entry.getValue();

            System.out.println();
            System.out.println(
                    course.getCourseCode()
                            + " - "
                            + course.getCourseName());

            for (int index = 0;
                 index < marks.size();
                 index++) {

                System.out.printf(
                        "Assessment %d: %.2f%%%n",
                        index + 1,
                        marks.get(index)
                );
            }

            double courseAverage =
                    student.calculateCourseAverage(
                            course);

            System.out.printf(
                    "Course Average: %.2f%%%n",
                    courseAverage);

            System.out.println(
                    "Grade         : "
                            + getGradeSymbol(
                            courseAverage));

            System.out.println(
                    "Result        : "
                            + getResult(
                            courseAverage));
        }

        System.out.println();
        System.out.println(
                "--------------------------------------");

        System.out.printf(
                "Overall Average : %.2f%%%n",
                student.calculateAverage());

        System.out.printf(
                "Highest Mark    : %.2f%%%n",
                student.getHighestMark());

        System.out.printf(
                "Lowest Mark     : %.2f%%%n",
                student.getLowestMark());

        System.out.println(
                "Courses Passed  : "
                        + countPassedCourses(student));

        System.out.println(
                "Courses Failed  : "
                        + countFailedCourses(student));

        System.out.println(
                "Overall Result  : "
                        + getResult(
                        student.calculateAverage()));

        System.out.println(
                "======================================");
    }

    // ==============================
    // Display Course Results
    // ==============================

    public void displayCourseResults(
            Course course) {

        if (course == null) {

            System.out.println(
                    "Course not found.");

            return;
        }

        System.out.println();
        System.out.println(
                "======================================");

        System.out.println(
                course.getCourseCode()
                        + " - "
                        + course.getCourseName());

        System.out.println(
                "======================================");

        boolean resultsFound = false;

        for (Student student
                : course.getEnrolledStudents()) {

            if (!student.hasMarksForCourse(course)) {
                continue;
            }

            double average =
                    student.calculateCourseAverage(
                            course);

            System.out.printf(
                    "%s | %s %s | %.2f%% | %s%n",
                    student.getStudentID(),
                    student.getFirstName(),
                    student.getLastName(),
                    average,
                    getResult(average)
            );

            resultsFound = true;
        }

        if (!resultsFound) {

            System.out.println(
                    "No course marks available.");

            return;
        }

        System.out.println();

        System.out.printf(
                "Class Average : %.2f%%%n",
                calculateCourseClassAverage(
                        course));

        System.out.printf(
                "Pass Rate     : %.2f%%%n",
                calculateCoursePassRate(course));

        System.out.println(
                "Passes        : "
                        + countCoursePasses(course));

        System.out.println(
                "Failures      : "
                        + countCourseFailures(course));

        Student topStudent =
                getTopStudentForCourse(course);

        if (topStudent != null) {

            System.out.println(
                    "Top Student   : "
                            + topStudent.getStudentID()
                            + " - "
                            + topStudent.getFirstName()
                            + " "
                            + topStudent.getLastName());
        }

        System.out.println(
                "======================================");
    }
}