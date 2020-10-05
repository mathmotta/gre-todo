package com.goldenrealstate.gretodo.common;

/**
 * Represents the current Status for objects that needs to control Begin, Progress and End
 *
 * @author Mathews Motta
 * @since 1.0
 */
public enum ProjectStatus {
    NEW("New"), IN_PROGRESS("In Progress"), COMPLETED("Completed");

    private String enumAsString;

    /**
     * Creates an enum with an equivalent string text that represents it. This is useful to be able to control toString() formats.
     *
     * @param enumAsString the string value
     */
    ProjectStatus(String enumAsString) {
        this.enumAsString = enumAsString;
    }

    public static ProjectStatus fromString(String text) {
        for (ProjectStatus b : ProjectStatus.values()) {
            if (b.enumAsString.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Gets the correspondent string value of the enum back
     *
     * @return the string value
     */
    @Override
    public String toString() {
        return enumAsString;
    }
}
