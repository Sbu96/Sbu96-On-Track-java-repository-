import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CourseManager {

    // ==============================
    // Attributes
    // ==============================

    private final ArrayList<Course> courses;

    // ==============================
    // Constructor
    // ==============================

    public CourseManager() {
        courses = new ArrayList<>();
    }

    // ==============================
    // Create
    // ==============================

    public boolean addCourse(Course course) {

        if (course == null) {
            return false;
        }

        if (course.getCourseCode() == null
                || course.getCourseCode().trim().isEmpty()) {
            return false;
        }

        if (findCourseByCode(course.getCourseCode()) != null) {
            return false;
        }

        courses.add(course);

        return true;
    }

    // ==============================
    // Read / Search
    // ==============================

    public Course findCourseByCode(String courseCode) {

        if (courseCode == null) {
            return null;
        }

        for (Course course : courses) {

            if (course.getCourseCode()
                    .equalsIgnoreCase(courseCode.trim())) {

                return course;
            }
        }

        return null;
    }

    public List<Course> findCoursesByName(String keyword) {

        List<Course> results = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }

        String searchValue = keyword.trim().toLowerCase();

        for (Course course : courses) {

            if (course.getCourseName()
                    .toLowerCase()
                    .contains(searchValue)) {

                results.add(course);
            }
        }

        return results;
    }

    public List<Course> findCoursesByLecturer(String lecturerName) {

        List<Course> results = new ArrayList<>();

        if (lecturerName == null
                || lecturerName.trim().isEmpty()) {

            return results;
        }

        String searchValue =
                lecturerName.trim().toLowerCase();

        for (Course course : courses) {

            if (course.getLecturer() != null
                    && course.getLecturer()
                    .toLowerCase()
                    .contains(searchValue)) {

                results.add(course);
            }
        }

        return results;
    }

    // ==============================
    // Update
    // ==============================

    public boolean updateCourse(
            String courseCode,
            String courseName,
            String description,
            int credits,
            String lecturer,
            int maxStudents) {

        Course course = findCourseByCode(courseCode);

        if (course == null) {
            return false;
        }

        if (courseName == null
                || courseName.trim().isEmpty()) {

            return false;
        }

        if (credits <= 0) {
            return false;
        }

        if (maxStudents <= 0) {
            return false;
        }

        if (maxStudents < course.getEnrollmentCount()) {
            return false;
        }

        course.setCourseName(courseName.trim());
        course.setDescription(
                description == null
                        ? ""
                        : description.trim());

        course.setCredits(credits);

        course.setLecturer(
                lecturer == null
                        ? ""
                        : lecturer.trim());

        course.setMaxStudents(maxStudents);

        return true;
    }

    public boolean updateCourse(Course updatedCourse) {

        if (updatedCourse == null) {
            return false;
        }

        return updateCourse(
                updatedCourse.getCourseCode(),
                updatedCourse.getCourseName(),
                updatedCourse.getDescription(),
                updatedCourse.getCredits(),
                updatedCourse.getLecturer(),
                updatedCourse.getMaxStudents()
        );
    }

    // ==============================
    // Delete
    // ==============================

    public boolean removeCourse(String courseCode) {

        Course course = findCourseByCode(courseCode);

        if (course == null) {
            return false;
        }

        /*
         * Prevent deleting a course while students
         * are still enrolled in it.
         */
        if (course.getEnrollmentCount() > 0) {
            return false;
        }

        return courses.remove(course);
    }

    // ==============================
    // Course Availability
    // ==============================

    public List<Course> getAvailableCourses() {

        List<Course> availableCourses =
                new ArrayList<>();

        for (Course course : courses) {

            if (!course.isFull()) {
                availableCourses.add(course);
            }
        }

        return availableCourses;
    }

    public List<Course> getFullCourses() {

        List<Course> fullCourses =
                new ArrayList<>();

        for (Course course : courses) {

            if (course.isFull()) {
                fullCourses.add(course);
            }
        }

        return fullCourses;
    }

    public int getAvailableSpace(String courseCode) {

        Course course = findCourseByCode(courseCode);

        if (course == null) {
            return -1;
        }

        return course.getMaxStudents()
                - course.getEnrollmentCount();
    }

    // ==============================
    // Sorting
    // ==============================

    public void sortByCourseCode() {

        courses.sort(
                Comparator.comparing(
                        Course::getCourseCode,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }

    public void sortByCourseName() {

        courses.sort(
                Comparator.comparing(
                        Course::getCourseName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }

    public void sortByLecturer() {

        courses.sort(
                Comparator.comparing(
                        Course::getLecturer,
                        Comparator.nullsLast(
                                String.CASE_INSENSITIVE_ORDER
                        )
                )
        );
    }

    public void sortByEnrollmentCount() {

        courses.sort(
                Comparator.comparingInt(
                        Course::getEnrollmentCount
                ).reversed()
        );
    }

    // ==============================
    // Statistics
    // ==============================

    public int getCourseCount() {
        return courses.size();
    }

    public int getTotalEnrollmentCount() {

        int total = 0;

        for (Course course : courses) {
            total += course.getEnrollmentCount();
        }

        return total;
    }

    public Course getMostPopularCourse() {

        if (courses.isEmpty()) {
            return null;
        }

        Course mostPopular = courses.get(0);

        for (Course course : courses) {

            if (course.getEnrollmentCount()
                    > mostPopular.getEnrollmentCount()) {

                mostPopular = course;
            }
        }

        return mostPopular;
    }

    public double calculateAverageClassSize() {

        if (courses.isEmpty()) {
            return 0;
        }

        return (double) getTotalEnrollmentCount()
                / courses.size();
    }

    public double calculateOverallCapacityUsage() {

        int totalCapacity = 0;
        int totalEnrollments = 0;

        for (Course course : courses) {

            totalCapacity += course.getMaxStudents();
            totalEnrollments +=
                    course.getEnrollmentCount();
        }

        if (totalCapacity == 0) {
            return 0;
        }

        return ((double) totalEnrollments
                / totalCapacity) * 100;
    }

    // ==============================
    // Display Methods
    // ==============================

    public void displayAllCourses() {

        if (courses.isEmpty()) {

            System.out.println("No courses found.");
            return;
        }

        System.out.println(
                "\n========== COURSES ==========");

        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public void displayCourse(String courseCode) {

        Course course = findCourseByCode(courseCode);

        if (course == null) {

            System.out.println("Course not found.");
            return;
        }

        course.displayCourse();
    }

    // ==============================
    // Utility Methods
    // ==============================

    public boolean isEmpty() {
        return courses.isEmpty();
    }

    public void clear() {
        courses.clear();
    }

    public List<Course> getCourses() {
        return courses;
    }

}