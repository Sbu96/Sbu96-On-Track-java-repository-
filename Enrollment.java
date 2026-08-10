 

import java.time.LocalDate;
import java.util.Objects;

public class Enrollment {

    // ==============================
    // Attributes
    // ==============================

    private String enrollmentID;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private String academicYear;
    private String semester;
    private EnrollmentStatus status;
    private double finalGrade;
    private String remarks;

    // ==============================
    // Default Constructor
    // ==============================

    public Enrollment() {

        enrollmentDate = LocalDate.now();
        status = EnrollmentStatus.ACTIVE;
        finalGrade = 0;
        remarks = "";
    }

    // ==============================
    // Parameterized Constructor
    // ==============================

    public Enrollment(
            String enrollmentID,
            Student student,
            Course course,
            String academicYear,
            String semester) {

        this.enrollmentID = enrollmentID;
        this.student = student;
        this.course = course;
        this.academicYear = academicYear;
        this.semester = semester;

        enrollmentDate = LocalDate.now();
        status = EnrollmentStatus.ACTIVE;
        finalGrade = 0;
        remarks = "";
    }

    // ==============================
    // Full Constructor
    // ==============================

    /*
     * This constructor will be useful when
     * loading enrollment records from a file.
     */
    public Enrollment(
            String enrollmentID,
            Student student,
            Course course,
            LocalDate enrollmentDate,
            String academicYear,
            String semester,
            EnrollmentStatus status,
            double finalGrade,
            String remarks) {

        this.enrollmentID = enrollmentID;
        this.student = student;
        this.course = course;

        this.enrollmentDate =
                enrollmentDate == null
                        ? LocalDate.now()
                        : enrollmentDate;

        this.academicYear =
                academicYear == null
                        ? ""
                        : academicYear.trim();

        this.semester =
                semester == null
                        ? ""
                        : semester.trim();

        this.status =
                status == null
                        ? EnrollmentStatus.ACTIVE
                        : status;

        setFinalGrade(finalGrade);

        this.remarks =
                remarks == null
                        ? ""
                        : remarks.trim();
    }

    // ==============================
    // Getters
    // ==============================

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public String getRemarks() {
        return remarks;
    }

    // ==============================
    // Setters
    // ==============================

    public void setEnrollmentID(
            String enrollmentID) {

        if (enrollmentID != null
                && !enrollmentID.trim().isEmpty()) {

            this.enrollmentID =
                    enrollmentID.trim();
        }
    }

    public void setStudent(
            Student student) {

        if (student != null) {
            this.student = student;
        }
    }

    public void setCourse(
            Course course) {

        if (course != null) {
            this.course = course;
        }
    }

    public void setEnrollmentDate(
            LocalDate enrollmentDate) {

        if (enrollmentDate != null) {
            this.enrollmentDate =
                    enrollmentDate;
        }
    }

    public void setAcademicYear(
            String academicYear) {

        if (academicYear != null
                && !academicYear.trim().isEmpty()) {

            this.academicYear =
                    academicYear.trim();
        }
    }

    public void setSemester(
            String semester) {

        if (semester != null
                && !semester.trim().isEmpty()) {

            this.semester =
                    semester.trim();
        }
    }

    public void setStatus(
            EnrollmentStatus status) {

        if (status != null) {
            this.status = status;
        }
    }

    public void setFinalGrade(
            double finalGrade) {

        if (finalGrade >= 0
                && finalGrade <= 100) {

            this.finalGrade = finalGrade;
        }
    }

    public void setRemarks(
            String remarks) {

        this.remarks =
                remarks == null
                        ? ""
                        : remarks.trim();
    }

    // ==============================
    // Status Methods
    // ==============================

    public boolean isActive() {

        return status
                == EnrollmentStatus.ACTIVE;
    }

    public boolean isCompleted() {

        return status
                == EnrollmentStatus.COMPLETED;
    }

    public boolean isDropped() {

        return status
                == EnrollmentStatus.DROPPED;
    }

    public void completeEnrollment() {

        status = EnrollmentStatus.COMPLETED;
    }

    public void dropEnrollment() {

        status = EnrollmentStatus.DROPPED;
    }

    public void reactivateEnrollment() {

        status = EnrollmentStatus.ACTIVE;
    }

    // ==============================
    // Academic Result Methods
    // ==============================

    public boolean hasPassed() {

        return isCompleted()
                && finalGrade >= 50;
    }

    public boolean hasFailed() {

        return isCompleted()
                && finalGrade < 50;
    }

    public String getResult() {

        if (!isCompleted()) {
            return "NOT FINALISED";
        }

        return hasPassed()
                ? "PASS"
                : "FAIL";
    }

    public String getGradeSymbol() {

        if (!isCompleted()) {
            return "N/A";
        }

        if (finalGrade >= 75) {
            return "A";
        }

        if (finalGrade >= 70) {
            return "B";
        }

        if (finalGrade >= 60) {
            return "C";
        }

        if (finalGrade >= 50) {
            return "D";
        }

        return "F";
    }

    // ==============================
    // Validation
    // ==============================

    public boolean isValid() {

        return enrollmentID != null
                && !enrollmentID.trim().isEmpty()
                && student != null
                && course != null
                && enrollmentDate != null
                && academicYear != null
                && !academicYear.trim().isEmpty()
                && semester != null
                && !semester.trim().isEmpty()
                && status != null;
    }

    // ==============================
    // Display Enrollment
    // ==============================

    public void displayEnrollment() {

        System.out.println(
                "======================================");

        System.out.println(
                "Enrollment ID : "
                        + enrollmentID);

        if (student != null) {

            System.out.println(
                    "Student ID    : "
                            + student.getStudentID());

            System.out.println(
                    "Student Name  : "
                            + student.getFirstName()
                            + " "
                            + student.getLastName());

        } else {

            System.out.println(
                    "Student       : Not available");
        }

        if (course != null) {

            System.out.println(
                    "Course Code   : "
                            + course.getCourseCode());

            System.out.println(
                    "Course Name   : "
                            + course.getCourseName());

        } else {

            System.out.println(
                    "Course        : Not available");
        }

        System.out.println(
                "Academic Year  : "
                        + academicYear);

        System.out.println(
                "Semester       : "
                        + semester);

        System.out.println(
                "Enrollment Date: "
                        + enrollmentDate);

        System.out.println(
                "Status         : "
                        + status);

        if (isCompleted()) {

            System.out.printf(
                    "Final Grade    : %.2f%%%n",
                    finalGrade);

            System.out.println(
                    "Grade Symbol   : "
                            + getGradeSymbol());

            System.out.println(
                    "Result         : "
                            + getResult());

        } else {

            System.out.println(
                    "Final Grade    : Not finalised");
        }

        System.out.println(
                "Remarks        : "
                        + (remarks.isEmpty()
                        ? "None"
                        : remarks));

        System.out.println(
                "======================================");
    }

    // ==============================
    // equals()
    // ==============================

    @Override
    public boolean equals(
            Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof Enrollment)) {
            return false;
        }

        Enrollment enrollment =
                (Enrollment) object;

        return Objects.equals(
                enrollmentID,
                enrollment.enrollmentID
        );
    }

    // ==============================
    // hashCode()
    // ==============================

    @Override
    public int hashCode() {

        return Objects.hash(enrollmentID);
    }

    // ==============================
    // toString()
    // ==============================

    @Override
    public String toString() {

        String studentID =
                student == null
                        ? "Unknown Student"
                        : student.getStudentID();

        String courseCode =
                course == null
                        ? "Unknown Course"
                        : course.getCourseCode();

        return enrollmentID
                + " | "
                + studentID
                + " | "
                + courseCode
                + " | "
                + academicYear
                + " "
                + semester
                + " | "
                + status
                + (isCompleted()
                ? " | Final Grade: "
                + String.format(
                        "%.2f%%",
                        finalGrade)
                : "");
    }
}