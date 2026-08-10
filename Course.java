import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {

    // ==============================
    // Attributes
    // ==============================

    private String courseCode;
    private String courseName;
    private String description;
    private int credits;
    private String lecturer;
    private int maxStudents;

    private ArrayList<Student> enrolledStudents;

    // ==============================
    // Constructors
    // ==============================

    public Course() {

        enrolledStudents = new ArrayList<>();

    }

    public Course(String courseCode,
                  String courseName,
                  String description,
                  int credits,
                  String lecturer,
                  int maxStudents) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.lecturer = lecturer;
        this.maxStudents = maxStudents;

        enrolledStudents = new ArrayList<>();

    }

    // ==============================
    // Getters
    // ==============================

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public int getCredits() {
        return credits;
    }

    public String getLecturer() {
        return lecturer;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    // ==============================
    // Setters
    // ==============================

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    // ==============================
    // Enrollment Methods
    // ==============================

    public boolean addStudent(Student student) {

        if (student == null) {
            return false;
        }

        if (enrolledStudents.contains(student)) {
            return false;
        }

        if (enrolledStudents.size() >= maxStudents) {
            return false;
        }

        enrolledStudents.add(student);

        return true;

    }

    public boolean removeStudent(Student student) {

        return enrolledStudents.remove(student);

    }

    public boolean isFull() {

        return enrolledStudents.size() >= maxStudents;

    }

    public int getEnrollmentCount() {

        return enrolledStudents.size();

    }

    // ==============================
    // Display Course
    // ==============================

    public void displayCourse() {

        System.out.println("-----------------------------------");
        System.out.println("Course Code : " + courseCode);
        System.out.println("Course Name : " + courseName);
        System.out.println("Description : " + description);
        System.out.println("Credits     : " + credits);
        System.out.println("Lecturer    : " + lecturer);

        System.out.println("Capacity    : "
                + enrolledStudents.size()
                + "/"
                + maxStudents);

        System.out.println();

        System.out.println("Enrolled Students");

        if (enrolledStudents.isEmpty()) {

            System.out.println("None");

        } else {

            for (Student student : enrolledStudents) {

                System.out.println(
                        student.getStudentID()
                        + " - "
                        + student.getFirstName()
                        + " "
                        + student.getLastName());

            }

        }

        System.out.println("-----------------------------------");

    }

    // ==============================
    // equals()
    // ==============================

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Course))
            return false;

        Course course = (Course) obj;

        return Objects.equals(courseCode,
                course.courseCode);

    }

    // ==============================
    // hashCode()
    // ==============================

    @Override
    public int hashCode() {

        return Objects.hash(courseCode);

    }

    // ==============================
    // toString()
    // ==============================

    @Override
    public String toString() {

        return courseCode
                + " | "
                + courseName
                + " | "
                + credits
                + " Credits | "
                + enrolledStudents.size()
                + "/"
                + maxStudents
                + " Students";

    }

}