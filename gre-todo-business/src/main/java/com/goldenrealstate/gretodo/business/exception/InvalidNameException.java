package com.goldenrealstate.gretodo.business.exception;

/**
 * Thrown when the provided name for mutations/changes is not valid, such as null or empty.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public class InvalidNameException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidNameException() {
        super(); // TODO: resolve message with properties
    }
}
