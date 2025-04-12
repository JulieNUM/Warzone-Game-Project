package com.tyrcl.common.log;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code LogEntryBuffer} class maintains a log entry and notifies registered observers
 * whenever the log entry is updated.
 */
public class LogEntryBuffer {
    
    /**
     * The current log entry.
     */
    private String d_logEntry;
    
    /**
     * List of observers that are notified when the log entry changes.
     */
    private final List<LogObserver> d_observers = new ArrayList<>();
    
    /**
     * Adds an observer to the list of observers.
     *
     * @param p_observer the observer to be added
     */
    public void addObserver(LogObserver p_observer) {
        d_observers.add(p_observer);
    }
    
    /**
     * Removes an observer from the list of observers.
     *
     * @param p_observer the observer to be removed
     */
    public void removeObserver(LogObserver p_observer) {
        d_observers.remove(p_observer);
    }
    
    /**
     * Notifies all registered observers of the updated log entry.
     */
    public void notifyObservers() {
        for (LogObserver observer : d_observers) {
            observer.update(d_logEntry);
        }
    }
    
    /**
     * Sets the log entry and notifies all observers of the change.
     *
     * @param p_logEntry the new log entry
     */
    public void setLogEntry(String p_logEntry) {
        this.d_logEntry = p_logEntry;
        notifyObservers(); // Notify observers when the log entry is updated
    }
}
