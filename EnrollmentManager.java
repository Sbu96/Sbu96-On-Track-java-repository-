

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EnrollmentManager {

    // ==============================
    // Attributes
    // ==============================

    private final ArrayList<Enrollment> enrollments;
    private int nextEnrollmentNumber;

    // ==============================
    // Constructor
    // ==============================

    public EnrollmentManager() {

        enrollments = new ArrayList<>();
        nextEnrollmentNumber = 1;
    }

    // ==============================
    // Create Enrollment
    // ==============================

    public Enrollment enrollStudent(
            Student student,
            Course course,
            String academicYear,
            String semester) {

        if (student == null || course == null) {
            return null;
        }

        if (academicYear == null
                || academicYear.trim().isEmpty()) {

            return null;
        }

        if (semester == null
                || semester.trim().isEmpty()) {

            return null;
        }

        /*
         * Prevent the student from having another
         * active enrollment for the same course,
         * academic year, and semester.
         */
        if (hasActiveEnrollment(
                student.getStudentID(),
                course.getCourseCode(),
                academicYear,
                semester)) {

            return null;
        }

        /*
         * Prevent enrollment when the course
         * has reached maximum capacity.
         */
        if (course.isFull()) {
            return null;
        }

        /*
         * Add the student to the active course list.
         * Course.addStudent() also prevents duplicates.
         */
        boolean studentAdded =
                course.addStudent(student);

        if (!studentAdded) {
            return null;
        }

        String enrollmentID =
                generateEnrollmentID();

        Enrollment enrollment =
                new Enrollment(
                        enrollmentID,
                        student,
                        course,
                        academicYear.trim(),
                        semester.trim()
                );

        enrollments.add(enrollment);

        return enrollment;
    }

    // ==============================
    // Add Existing Enrollment
    // ==============================

    /*
     * Used by FileManager when loading previously
     * saved enrollment records.
     */
    public boolean addExistingEnrollment(
            Enrollment enrollment) {

        if (enrollment == null) {
            return false;
        }

        if (enrollment.getEnrollmentID() == null
                || enrollment.getEnrollmentID()
                .trim()
                .isEmpty()) {

            return false;
        }

        if (enrollment.getStudent() == null
                || enrollment.getCourse() == null) {

            return false;
        }

        if (findEnrollmentByID(
                enrollment.getEnrollmentID()) != null) {

            return false;
        }

        /*
         * Active enrollments occupy a place
         * in the course.
         */
        if (enrollment.getStatus()
                == EnrollmentStatus.ACTIVE) {

            Course course = enrollment.getCourse();
            Student student = enrollment.getStudent();

            if (!course.getEnrolledStudents()
                    .contains(student)) {

                boolean added =
                        course.addStudent(student);

                if (!added) {
                    return false;
                }
            }
        }

        enrollments.add(enrollment);

        updateNextEnrollmentNumber(
                enrollment.getEnrollmentID());

        return true;
    }

    // ==============================
    // Generate Enrollment ID
    // ==============================

    private String generateEnrollmentID() {

        String enrollmentID =
                String.format(
                        "ENR%04d",
                        nextEnrollmentNumber
                );

        nextEnrollmentNumber++;

        return enrollmentID;
    }

    private void updateNextEnrollmentNumber(
            String enrollmentID) {

        if (enrollmentID == null
                || !enrollmentID.matches("ENR\\d+")) {

            return;
        }

        try {

            int existingNumber =
                    Integer.parseInt(
                            enrollmentID.substring(3)
                    );

            if (existingNumber
                    >= nextEnrollmentNumber) {

                nextEnrollmentNumber =
                        existingNumber + 1;
            }

        } catch (NumberFormatException exception) {

            System.out.println(
                    "Invalid enrollment ID: "
                            + enrollmentID
            );
        }
    }

    // ==============================
    // Find Enrollment by ID
    // ==============================

    public Enrollment findEnrollmentByID(
            String enrollmentID) {

        if (enrollmentID == null) {
            return null;
        }

        for (Enrollment enrollment : enrollments) {

            if (enrollment.getEnrollmentID()
                    .equalsIgnoreCase(
                            enrollmentID.trim())) {

                return enrollment;
            }
        }

        return null;
    }

    // ==============================
    // Find Active Enrollment
    // ==============================

    public Enrollment findActiveEnrollment(
            String studentID,
            String courseCode) {

        if (studentID == null
                || courseCode == null) {

            return null;
        }

        for (Enrollment enrollment : enrollments) {

            boolean sameStudent =
                    enrollment.getStudent()
                            .getStudentID()
                            .equalsIgnoreCase(
                                    studentID.trim());

            boolean sameCourse =
                    enrollment.getCourse()
                            .getCourseCode()
                            .equalsIgnoreCase(
                                    courseCode.trim());

            boolean active =
                    enrollment.getStatus()
                            == EnrollmentStatus.ACTIVE;

            if (sameStudent
                    && sameCourse
                    && active) {

                return enrollment;
            }
        }

        return null;
    }

    // ==============================
    // Check Duplicate Enrollment
    // ==============================

    public boolean hasActiveEnrollment(
            String studentID,
            String courseCode,
            String academicYear,
            String semester) {

        if (studentID == null
                || courseCode == null
                || academicYear == null
                || semester == null) {

            return false;
        }

        for (Enrollment enrollment : enrollments) {

            boolean sameStudent =
                    enrollment.getStudent()
                            .getStudentID()
                            .equalsIgnoreCase(
                                    studentID.trim());

            boolean sameCourse =
                    enrollment.getCourse()
                            .getCourseCode()
                            .equalsIgnoreCase(
                                    courseCode.trim());

            boolean sameAcademicYear =
                    enrollment.getAcademicYear()
                            .equalsIgnoreCase(
                                    academicYear.trim());

            boolean sameSemester =
                    enrollment.getSemester()
                            .equalsIgnoreCase(
                                    semester.trim());

            boolean active =
                    enrollment.getStatus()
                            == EnrollmentStatus.ACTIVE;

            if (sameStudent
                    && sameCourse
                    && sameAcademicYear
                    && sameSemester
                    && active) {

                return true;
            }
        }

        return false;
    }

    // ==============================
    // Find Enrollments by Student
    // ==============================

    public List<Enrollment> findEnrollmentsByStudent(
            String studentID) {

        List<Enrollment> results =
                new ArrayList<>();

        if (studentID == null) {
            return results;
        }

        for (Enrollment enrollment : enrollments) {

            if (enrollment.getStudent()
                    .getStudentID()
                    .equalsIgnoreCase(
                            studentID.trim())) {

                results.add(enrollment);
            }
        }

        return results;
    }

    // ==============================
    // Find Enrollments by Course
    // ==============================

    public List<Enrollment> findEnrollmentsByCourse(
            String courseCode) {

        List<Enrollment> results =
                new ArrayList<>();

        if (courseCode == null) {
            return results;
        }

        for (Enrollment enrollment : enrollments) {

            if (enrollment.getCourse()
                    .getCourseCode()
                    .equalsIgnoreCase(
                            courseCode.trim())) {

                results.add(enrollment);
            }
        }

        return results;
    }

    // ==============================
    // Find Enrollments by Status
    // ==============================

    public List<Enrollment> findEnrollmentsByStatus(
            EnrollmentStatus status) {

        List<Enrollment> results =
                new ArrayList<>();

        if (status == null) {
            return results;
        }

        for (Enrollment enrollment : enrollments) {

            if (enrollment.getStatus() == status) {
                results.add(enrollment);
            }
        }

        return results;
    }

    // ==============================
    // Find Enrollments by Period
    // ==============================

    public List<Enrollment>
    findEnrollmentsByAcademicPeriod(
            String academicYear,
            String semester) {

        List<Enrollment> results =
                new ArrayList<>();

        if (academicYear == null
                || semester == null) {

            return results;
        }

        for (Enrollment enrollment : enrollments) {

            boolean sameAcademicYear =
                    enrollment.getAcademicYear()
                            .equalsIgnoreCase(
                                    academicYear.trim());

            boolean sameSemester =
                    enrollment.getSemester()
                            .equalsIgnoreCase(
                                    semester.trim());

            if (sameAcademicYear
                    && sameSemester) {

                results.add(enrollment);
            }
        }

        return results;
    }

    // ==============================
    // Drop Enrollment
    // ==============================

    public boolean dropEnrollment(
            String enrollmentID,
            String remarks) {

        Enrollment enrollment =
                findEnrollmentByID(enrollmentID);

        if (enrollment == null) {
            return false;
        }

        if (enrollment.getStatus()
                != EnrollmentStatus.ACTIVE) {

            return false;
        }

        enrollment.dropEnrollment();

        enrollment.setRemarks(
                remarks == null
                        ? ""
                        : remarks.trim()
        );

        /*
         * Dropped students no longer occupy
         * active course capacity.
         */
        enrollment.getCourse()
                .removeStudent(
                        enrollment.getStudent());

        return true;
    }

    // ==============================
    // Complete Enrollment
    // ==============================

    public boolean completeEnrollment(
            String enrollmentID,
            double finalGrade,
            String remarks) {

        Enrollment enrollment =
                findEnrollmentByID(enrollmentID);

        if (enrollment == null) {
            return false;
        }

        if (enrollment.getStatus()
                != EnrollmentStatus.ACTIVE) {

            return false;
        }

        if (finalGrade < 0
                || finalGrade > 100) {

            return false;
        }

        /*
         * The final grade is stored in the
         * Enrollment object only.
         *
         * It is not added to Student marks because
         * it would incorrectly become an additional
         * assessment mark.
         */
        enrollment.setFinalGrade(finalGrade);

        enrollment.setRemarks(
                remarks == null
                        ? ""
                        : remarks.trim()
        );

        enrollment.completeEnrollment();

        /*
         * Completed students no longer occupy
         * active course capacity.
         */
        enrollment.getCourse()
                .removeStudent(
                        enrollment.getStudent());

        return true;
    }

    // ==============================
    // Complete Using Recorded Marks
    // ==============================

    public boolean completeEnrollmentFromMarks(
            String enrollmentID,
            String remarks) {

        Enrollment enrollment =
                findEnrollmentByID(enrollmentID);

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

        double calculatedAverage =
                student.calculateCourseAverage(course);

        return completeEnrollment(
                enrollmentID,
                calculatedAverage,
                remarks
        );
    }

    // ==============================
    // Update Final Grade
    // ==============================

    public boolean updateFinalGrade(
            String enrollmentID,
            double finalGrade) {

        Enrollment enrollment =
                findEnrollmentByID(enrollmentID);

        if (enrollment == null) {
            return false;
        }

        if (finalGrade < 0
                || finalGrade > 100) {

            return false;
        }

        enrollment.setFinalGrade(finalGrade);

        return true;
    }

    // ==============================
    // Update Final Grade from Marks
    // ==============================

    public boolean updateFinalGradeFromMarks(
            String enrollmentID) {

        Enrollment enrollment =
                findEnrollmentByID(enrollmentID);

        if (enrollment == null) {
            return false;
        }

        Student student =
                enrollment.getStudent();

        Course course =
                enrollment.getCourse();

        if (!student.hasMarksForCourse(course)) {
            return false;
        }

        double calculatedAverage =
                student.calculateCourseAverage(course);

        enrollment.setFinalGrade(
                calculatedAverage);

        return true;
    }

    // ==============================
    // Update Remarks
    // ==============================

    public boolean updateRemarks(
            String enrollmentID,
            String remarks) {

        Enrollment enrollment =
                findEnrollmentByID(enrollmentID);

        if (enrollment == null) {
            return false;
        }

        enrollment.setRemarks(
                remarks == null
                        ? ""
                        : remarks.trim()
        );

        return true;
    }

    // ==============================
    // Remove Enrollment Record
    // ==============================

    public boolean removeEnrollmentRecord(
            String enrollmentID) {

        Enrollment enrollment =
                findEnrollmentByID(enrollmentID);

        if (enrollment == null) {
            return false;
        }

        /*
         * If it is active, remove the student
         * from the course before deleting it.
         */
        if (enrollment.getStatus()
                == EnrollmentStatus.ACTIVE) {

            enrollment.getCourse()
                    .removeStudent(
                            enrollment.getStudent());
        }

        return enrollments.remove(enrollment);
    }

    // ==============================
    // Sorting
    // ==============================

    public void sortByEnrollmentID() {

        enrollments.sort(
                Comparator.comparing(
                        Enrollment::getEnrollmentID,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }

    public void sortByEnrollmentDate() {

        enrollments.sort(
                Comparator.comparing(
                        Enrollment::getEnrollmentDate
                )
        );
    }

    public void sortByStudentName() {

    enrollments.sort(new Comparator<Enrollment>() {

        @Override
        public int compare(
                Enrollment firstEnrollment,
                Enrollment secondEnrollment) {

            int lastNameComparison =
                    firstEnrollment
                            .getStudent()
                            .getLastName()
                            .compareToIgnoreCase(
                                    secondEnrollment
                                            .getStudent()
                                            .getLastName()
                            );

            if (lastNameComparison != 0) {
                return lastNameComparison;
            }

            return firstEnrollment
                    .getStudent()
                    .getFirstName()
                    .compareToIgnoreCase(
                            secondEnrollment
                                    .getStudent()
                                    .getFirstName()
                    );
        }
    });
}

    public void sortByCourseCode() {

        enrollments.sort(
                Comparator.comparing(
                        enrollment ->
                                enrollment
                                        .getCourse()
                                        .getCourseCode(),
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }

    public void sortByFinalGrade() {

        enrollments.sort(
                Comparator.comparingDouble(
                        Enrollment::getFinalGrade
                ).reversed()
        );
    }

    // ==============================
    // Statistics
    // ==============================

    public int getEnrollmentCount() {
        return enrollments.size();
    }

    public int getActiveEnrollmentCount() {

        return countByStatus(
                EnrollmentStatus.ACTIVE);
    }

    public int getCompletedEnrollmentCount() {

        return countByStatus(
                EnrollmentStatus.COMPLETED);
    }

    public int getDroppedEnrollmentCount() {

        return countByStatus(
                EnrollmentStatus.DROPPED);
    }

    private int countByStatus(
            EnrollmentStatus status) {

        int count = 0;

        for (Enrollment enrollment : enrollments) {

            if (enrollment.getStatus() == status) {
                count++;
            }
        }

        return count;
    }

    public double calculateCompletionRate() {

        if (enrollments.isEmpty()) {
            return 0;
        }

        return ((double)
                getCompletedEnrollmentCount()
                / enrollments.size()) * 100;
    }

    public double calculateDropoutRate() {

        if (enrollments.isEmpty()) {
            return 0;
        }

        return ((double)
                getDroppedEnrollmentCount()
                / enrollments.size()) * 100;
    }

    public double calculateCompletedAverage() {

        double total = 0;
        int completedCount = 0;

        for (Enrollment enrollment : enrollments) {

            if (enrollment.getStatus()
                    == EnrollmentStatus.COMPLETED) {

                total += enrollment.getFinalGrade();
                completedCount++;
            }
        }

        if (completedCount == 0) {
            return 0;
        }

        return total / completedCount;
    }

    // ==============================
    // Display All Enrollments
    // ==============================

    public void displayAllEnrollments() {

        if (enrollments.isEmpty()) {

            System.out.println(
                    "No enrollments found.");

            return;
        }

        System.out.println(
                "\n========== ENROLLMENTS ==========");

        for (Enrollment enrollment
                : enrollments) {

            System.out.println(enrollment);
        }
    }

    // ==============================
    // Display Student Enrollments
    // ==============================

    public void displayStudentEnrollments(
            String studentID) {

        List<Enrollment> results =
                findEnrollmentsByStudent(studentID);

        if (results.isEmpty()) {

            System.out.println(
                    "No enrollments found "
                            + "for this student.");

            return;
        }

        for (Enrollment enrollment : results) {
            enrollment.displayEnrollment();
        }
    }

    // ==============================
    // Display Course Enrollments
    // ==============================

    public void displayCourseEnrollments(
            String courseCode) {

        List<Enrollment> results =
                findEnrollmentsByCourse(courseCode);

        if (results.isEmpty()) {

            System.out.println(
                    "No enrollments found "
                            + "for this course.");

            return;
        }

        for (Enrollment enrollment : results) {
            enrollment.displayEnrollment();
        }
    }

    // ==============================
    // Display One Enrollment
    // ==============================

    public void displayEnrollment(
            String enrollmentID) {

        Enrollment enrollment =
                findEnrollmentByID(enrollmentID);

        if (enrollment == null) {

            System.out.println(
                    "Enrollment not found.");

            return;
        }

        enrollment.displayEnrollment();
    }

    // ==============================
    // Utility Methods
    // ==============================

    public boolean isEmpty() {
        return enrollments.isEmpty();
    }

    public void clear() {

        /*
         * Remove all actively enrolled students
         * from their courses first.
         */
        for (Enrollment enrollment : enrollments) {

            if (enrollment.getStatus()
                    == EnrollmentStatus.ACTIVE) {

                enrollment.getCourse()
                        .removeStudent(
                                enrollment.getStudent());
            }
        }

        enrollments.clear();
        nextEnrollmentNumber = 1;
    }

    public List<Enrollment> getEnrollments() {

        /*
         * Return a copy to prevent external code
         * from directly modifying the original list.
         */
        return new ArrayList<>(enrollments);
    }
}