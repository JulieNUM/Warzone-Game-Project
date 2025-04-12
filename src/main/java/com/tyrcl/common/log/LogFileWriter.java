package com.tyrcl.common.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.tyrcl.utils.Helper;

/**
 * The {@code LogFileWriter} class implements {@code LogObserver} to write log entries
 * to a file whenever they are updated.
 */
public class LogFileWriter implements LogObserver {
    /**
     * Log Constructor
     */
    public LogFileWriter() {

    }
    
    /**
     * Writes the updated log entry to the specified log file.
     *
     * @param p_logEntry the updated log entry
     */
    @Override
    public void update(String p_logEntry) {
       String logFileName = Helper.LOG_DIRECTORY + "log.txt";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        File file = new File(logFileName);
        File parentDirectory = file.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            // If the directory does not exist, create it
            boolean isDirectoryCreated = parentDirectory.mkdirs();
            if (isDirectoryCreated) {
                System.out.println("Directory created successfully: " + parentDirectory.getAbsolutePath());
            } else {
                System.out.println("Failed to create directory: " + parentDirectory.getAbsolutePath());
                return;
            }
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Log file created: " + file.getName());
        }
        try (FileWriter writer = new FileWriter(logFileName, true)) {
            String logEntry = String.format("[%s] %s%n", timestamp, p_logEntry);
            writer.write(logEntry);
        } catch (Exception e) {
            System.err.println("Failed to log action for : " + p_logEntry);
            e.printStackTrace();
        }
    }
}

