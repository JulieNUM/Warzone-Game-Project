package com.tyrcl.utils;

/**
 * an expection class related to map errors
 */
public class InvalidMapException extends Exception {
    /**
     * Construction method by passing the error message
     * @param p_message the error message
     */
    public InvalidMapException(String p_message) {
        super(p_message);
    }
}
