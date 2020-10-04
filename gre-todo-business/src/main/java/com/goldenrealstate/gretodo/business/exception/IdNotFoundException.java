package com.goldenrealstate.gretodo.business.exception;

/**
 * Thrown when the requested ID for mutations/changes is not provided.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public class IdNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public IdNotFoundException(long id) {
        super(); // TODO: resolve message with properties
    }
}