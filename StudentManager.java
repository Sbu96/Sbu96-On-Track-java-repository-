import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentManager {

    // ==============================
    // Attributes
    // ==============================

    private final ArrayList<Student> students;

    // ==============================
    // Constructor
    // ==============================

    public StudentManager() {

        students = new ArrayList<>();

    }

    // ==============================
    // CRUD Operations
    // ==============================

    public boolean addStudent(Student student) {

        if (student == null)
            return false;

        if (findStudentByID(student.getStudentID()) != null)
            return false;

        students.add(student);

        return true;

    }

    public boolean updateStudent(Student updatedStudent) {

        Student existing =
                findStudentByID(updatedStudent.getStudentID());

        if (existing == null)
            return false;

        existing.setFirstName(updatedStudent.getFirstName());
        existing.setLastName(updatedStudent.getLastName());
        existing.setEmail(updatedStudent.getEmail());

        return true;

    }

    public boolean removeStudent(String studentID) {

        Student student =
                findStudentByID(studentID);

        if (student == null)
            return false;

        students.remove(student);

        return true;

    }

    // ==============================
    // Search Methods
    // ==============================

    public Student findStudentByID(String studentID) {

        for (Student student : students) {

            if (student.getStudentID()
                    .equalsIgnoreCase(studentID)) {

                return student;

            }

        }

        return null;

    }

    public List<Student> findStudentsByName(String keyword) {

        List<Student> results = new ArrayList<>();

        for (Student student : students) {

            String fullName =
                    student.getFirstName()
                    + " "
                    + student.getLastName();

            if (fullName.toLowerCase()
                    .contains(keyword.toLowerCase())) {

                results.add(student);

            }

        }

        return results;

    }

    // ==============================
    // Sorting
    // ==============================

    public void sortByStudentID() {

        students.sort(
                Comparator.comparing(
                        Student::getStudentID));

    }

    public void sortByName() {

        students.sort(
                Comparator.comparing(
                        Student::getLastName)
                        .thenComparing(
                                Student::getFirstName));

    }

    public void sortByAverage() {

        students.sort(
                Comparator.comparingDouble(
                        Student::calculateAverage)
                        .reversed());

    }

    // ==============================
    // Statistics
    // ==============================

    public int getStudentCount() {

        return students.size();

    }

    public Student getTopStudent() {

        if (students.isEmpty())
            return null;

        Student top = students.get(0);

        for (Student student : students) {

            if (student.calculateAverage()
                    > top.calculateAverage()) {

                top = student;

            }

        }

        return top;

    }

    public double calculateClassAverage() {

        if (students.isEmpty())
            return 0;

        double total = 0;

        for (Student student : students) {

            total += student.calculateAverage();

        }

        return total / students.size();

    }

    // ==============================
    // Display Methods
    // ==============================

    public void displayAllStudents() {

        if (students.isEmpty()) {

            System.out.println("No students found.");

            return;

        }

        System.out.println("\n========== STUDENTS ==========");

        for (Student student : students) {

            System.out.println(student);

        }

    }

    // ==============================
    // Utility Methods
    // ==============================

    public boolean isEmpty() {

        return students.isEmpty();

    }

    public void clear() {

        students.clear();

    }

    public List<Student> getStudents() {

        return students;

    }

}