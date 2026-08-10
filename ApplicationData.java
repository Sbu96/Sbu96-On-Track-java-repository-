public final class ApplicationData {

    private static final ApplicationData INSTANCE =
            new ApplicationData();

    private final StudentManager studentManager;
    private final CourseManager courseManager;
    private final EnrollmentManager enrollmentManager;
    private final MarksManager marksManager;
    private final FileManager fileManager;

    private boolean dataLoaded;

    private ApplicationData() {

        studentManager =
                new StudentManager();

        courseManager =
                new CourseManager();

        enrollmentManager =
                new EnrollmentManager();

        marksManager =
                new MarksManager();

        fileManager =
                new FileManager();

        dataLoaded = false;
    }

    public static ApplicationData getInstance() {

        return INSTANCE;
    }

    public boolean loadAllData() {

        if (dataLoaded) {
            return true;
        }

        boolean loaded =
                fileManager.loadAllData(
                        studentManager,
                        courseManager,
                        enrollmentManager
                );

        dataLoaded = loaded;

        return loaded;
    }

    public boolean saveAllData() {

        return fileManager.saveAllData(
                studentManager,
                courseManager,
                enrollmentManager
        );
    }

    public StudentManager getStudentManager() {

        return studentManager;
    }

    public CourseManager getCourseManager() {

        return courseManager;
    }

    public EnrollmentManager getEnrollmentManager() {

        return enrollmentManager;
    }

    public MarksManager getMarksManager() {

        return marksManager;
    }

    public FileManager getFileManager() {

        return fileManager;
    }
}