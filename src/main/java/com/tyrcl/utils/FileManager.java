package com.tyrcl.utils;

import java.io.IOException;

import com.tyrcl.mapeditor.model.Map;

/**
 * The {@code FileManager} interface defines methods for saving and loading maps.
 * Implementing classes should provide specific implementations for file operations.
 */
public interface FileManager {

    /**
     * Saves the given map to a specified file.
     *
     * @param p_map      the map to be saved
     * @param p_filename the name of the file where the map will be saved
     * @throws IOException if an I/O error occurs during saving
     */
    void saveMap(Map p_map, String p_filename) throws IOException;

    /**
     * Loads a map from a specified file.
     *
     * @param p_map      the map object to load data into
     * @param p_filename the name of the file from which to load the map
     * @throws IOException if an I/O error occurs during loading
     */
    void loadMap(Map p_map, String p_filename) throws IOException;
}
