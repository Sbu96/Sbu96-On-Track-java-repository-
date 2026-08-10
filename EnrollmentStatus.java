public enum EnrollmentStatus {

    ACTIVE,
    COMPLETED,
    DROPPED;

    /**
     * Returns a user-friendly version of the status.
     */
    @Override
    public String toString() {

        switch (this) {

            case ACTIVE:
                return "Active";

            case COMPLETED:
                return "Completed";

            case DROPPED:
                return "Dropped";

            default:
                return super.toString();
        }
    }

    /**
     * Converts text into an EnrollmentStatus.
     * Returns null if the text is invalid.
     */
    public static EnrollmentStatus fromString(String value) {

        if (value == null) {
            return null;
        }

        switch (value.trim().toUpperCase()) {

            case "ACTIVE":
                return ACTIVE;

            case "COMPLETED":
                return COMPLETED;

            case "DROPPED":
                return DROPPED;

            default:
                return null;
        }
    }

    /**
     * Checks if the enrollment is still active.
     */
    public boolean isActive() {
        return this == ACTIVE;
    }

    /**
     * Checks if the enrollment has been completed.
     */
    public boolean isCompleted() {
        return this == COMPLETED;
    }

    /**
     * Checks if the enrollment has been dropped.
     */
    public boolean isDropped() {
        return this == DROPPED;
    }
}