 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {

    // ==============================
    // Attributes
    // ==============================

    private String studentID;
    private String firstName;
    private String lastName;
    private String email;

    /*
     * Stores multiple marks for each course.
     *
     * Example:
     * Java Programming -> [75, 82, 68]
     * Database Systems -> [80, 74, 91]
     */
    private HashMap<Course, ArrayList<Double>> grades;

    // ==============================
    // Constructors
    // ==============================

    public Student() {

        grades = new HashMap<>();

    }

    public Student(
            String studentID,
            String firstName,
            String lastName,
            String email) {

        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        grades = new HashMap<>();

    }

    // ==============================
    // Getters
    // ==============================

    public String getStudentID() {

        return studentID;

    }

    public String getFirstName() {

        return firstName;

    }

    public String getLastName() {

        return lastName;

    }

    public String getEmail() {

        return email;

    }

    public Map<Course, ArrayList<Double>> getGrades() {

        return grades;

    }

    // ==============================
    // Setters
    // ==============================

    public void setStudentID(String studentID) {

        this.studentID = studentID;

    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;

    }

    public void setLastName(String lastName) {

        this.lastName = lastName;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    // ==============================
    // Mark Management
    // ==============================

    public boolean addMark(
            Course course,
            double mark) {

        if (course == null
                || mark < 0
                || mark > 100) {

            return false;

        }

        /*
         * Create a mark list for the course
         * if one does not already exist.
         */
        grades.putIfAbsent(
                course,
                new ArrayList<>()
        );

        grades.get(course).add(mark);

        return true;

    }

    public boolean updateMark(
            Course course,
            int assessmentIndex,
            double newMark) {

        if (course == null
                || newMark < 0
                || newMark > 100) {

            return false;

        }

        ArrayList<Double> courseMarks =
                grades.get(course);

        if (courseMarks == null
                || assessmentIndex < 0
                || assessmentIndex
                >= courseMarks.size()) {

            return false;

        }

        courseMarks.set(
                assessmentIndex,
                newMark
        );

        return true;

    }

    public boolean removeMark(
            Course course,
            int assessmentIndex) {

        if (course == null) {

            return false;

        }

        ArrayList<Double> courseMarks =
                grades.get(course);

        if (courseMarks == null
                || assessmentIndex < 0
                || assessmentIndex
                >= courseMarks.size()) {

            return false;

        }

        courseMarks.remove(assessmentIndex);

        /*
         * Remove the course from the map if
         * it no longer contains any marks.
         */
        if (courseMarks.isEmpty()) {

            grades.remove(course);

        }

        return true;

    }

    public boolean removeAllMarksForCourse(
            Course course) {

        if (course == null
                || !grades.containsKey(course)) {

            return false;

        }

        grades.remove(course);

        return true;

    }

    public ArrayList<Double> getMarksForCourse(
            Course course) {

        ArrayList<Double> courseMarks =
                grades.get(course);

        if (courseMarks == null) {

            return new ArrayList<>();

        }

        /*
         * Return a copy so external code cannot
         * directly modify the original list.
         */
        return new ArrayList<>(courseMarks);

    }

    public int getAssessmentCount(
            Course course) {

        ArrayList<Double> courseMarks =
                grades.get(course);

        if (courseMarks == null) {

            return 0;

        }

        return courseMarks.size();

    }

    public boolean hasMarksForCourse(
            Course course) {

        ArrayList<Double> courseMarks =
                grades.get(course);

        return courseMarks != null
                && !courseMarks.isEmpty();

    }

    // ==============================
    // Course Average
    // ==============================

    public double calculateCourseAverage(
            Course course) {

        ArrayList<Double> courseMarks =
                grades.get(course);

        if (courseMarks == null
                || courseMarks.isEmpty()) {

            return 0;

        }

        double total = 0;

        for (double mark : courseMarks) {

            total += mark;

        }

        return total / courseMarks.size();

    }

    // ==============================
    // Overall Average
    // ==============================

    public double calculateAverage() {

        if (grades.isEmpty()) {

            return 0;

        }

        double totalCourseAverages = 0;
        int coursesWithMarks = 0;

        for (Course course : grades.keySet()) {

            if (hasMarksForCourse(course)) {

                totalCourseAverages +=
                        calculateCourseAverage(course);

                coursesWithMarks++;

            }

        }

        if (coursesWithMarks == 0) {

            return 0;

        }

        return totalCourseAverages
                / coursesWithMarks;

    }

    // ==============================
    // Highest Mark
    // ==============================

    public double getHighestMark() {

        double highest = 0;
        boolean markFound = false;

        for (ArrayList<Double> courseMarks
                : grades.values()) {

            for (double mark : courseMarks) {

                if (!markFound
                        || mark > highest) {

                    highest = mark;
                    markFound = true;

                }

            }

        }

        return markFound ? highest : 0;

    }

    // ==============================
    // Lowest Mark
    // ==============================

    public double getLowestMark() {

        double lowest = 0;
        boolean markFound = false;

        for (ArrayList<Double> courseMarks
                : grades.values()) {

            for (double mark : courseMarks) {

                if (!markFound
                        || mark < lowest) {

                    lowest = mark;
                    markFound = true;

                }

            }

        }

        return markFound ? lowest : 0;

    }

    // ==============================
    // Total Marks
    // ==============================

    public int getTotalMarkCount() {

        int total = 0;

        for (ArrayList<Double> courseMarks
                : grades.values()) {

            total += courseMarks.size();

        }

        return total;

    }

    // ==============================
    // Display Student
    // ==============================

    public void displayStudent() {

        System.out.println(
                "======================================");

        System.out.println(
                "Student ID : " + studentID);

        System.out.println(
                "Name       : "
                        + firstName
                        + " "
                        + lastName);

        System.out.println(
                "Email      : " + email);

        System.out.println(
                "--------------------------------------");

        System.out.println("Academic Results");

        if (grades.isEmpty()) {

            System.out.println(
                    "No marks available.");

        } else {

            for (Map.Entry<Course,
                    ArrayList<Double>> entry
                    : grades.entrySet()) {

                Course course =
                        entry.getKey();

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
                            "Assessment %d: %.2f%n",
                            index + 1,
                            marks.get(index)
                    );

                }

                System.out.printf(
                        "Course Average: %.2f%%%n",
                        calculateCourseAverage(
                                course)
                );

            }

        }

        System.out.println(
                "--------------------------------------");

        System.out.printf(
                "Overall Average : %.2f%%%n",
                calculateAverage());

        System.out.printf(
                "Highest Mark    : %.2f%%%n",
                getHighestMark());

        System.out.printf(
                "Lowest Mark     : %.2f%%%n",
                getLowestMark());

        System.out.println(
                "======================================");

    }

    // ==============================
    // equals()
    // ==============================

    @Override
    public boolean equals(Object object) {

        if (this == object) {

            return true;

        }

        if (!(object instanceof Student)) {

            return false;

        }

        Student student =
                (Student) object;

        return Objects.equals(
                studentID,
                student.studentID
        );

    }

    // ==============================
    // hashCode()
    // ==============================

    @Override
    public int hashCode() {

        return Objects.hash(studentID);

    }

    // ==============================
    // toString()
    // ==============================

    @Override
    public String toString() {

        return studentID
                + " | "
                + firstName
                + " "
                + lastName
                + " | "
                + email
                + " | Average: "
                + String.format(
                        "%.2f%%",
                        calculateAverage()
                );

    }

}