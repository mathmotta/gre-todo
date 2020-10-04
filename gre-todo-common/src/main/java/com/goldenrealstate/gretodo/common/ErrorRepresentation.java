package com.goldenrealstate.gretodo.common;

/**
 * Base DTO to represent Error data generically.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public class ErrorRepresentation extends AbstractResultRepresentation {
    private String error;
    private int statusCode;

    public ErrorRepresentation() {
    }

    public ErrorRepresentation(String error, int statusCode) {
        setError(error);
        setStatusCode(statusCode);
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
