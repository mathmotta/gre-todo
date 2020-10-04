package com.goldenrealstate.gretodo.common;

public enum ProjectStatus {
    NEW("New"), IN_PROGRESS("In Progress"), COMPLETED("Completed");

    private String enumAsString;
    ProjectStatus(String enumAsString) {
        this.enumAsString = enumAsString;
    }

    @Override
    public String toString(){
        return enumAsString;
    }
}
