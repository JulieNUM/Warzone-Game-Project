package com.tyrcl.common.log;

/**
 * The {@code LogObserver} interface defines a method for receiving updates
 * when a log entry changes.
 */
interface LogObserver {
    /**
     * Called when the log entry is updated.
     *
     * @param logEntry the updated log entry
     */
    void update(String p_logEntry);
}
