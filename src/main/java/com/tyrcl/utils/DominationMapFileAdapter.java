package com.tyrcl.utils;

import java.io.IOException;

import com.tyrcl.mapeditor.model.Map;

/**
 * Adapter class that allows a {@link DominationMapFileManager} to be used as a {@link FileManager}.
 * This class delegates all file operations to the underlying {@code DominationMapFileManager}.
 */
public class DominationMapFileAdapter implements FileManager {

    private DominationMapFileManager d_mapManager;

    /**
     * Constructs a {@code DominationMapFileAdapter} with the specified {@code DominationMapFileManager}.
     *
     * @param p_mapManager the map file manager to delegate file operations to
     */
    public DominationMapFileAdapter(DominationMapFileManager p_mapManager) {
        d_mapManager = p_mapManager;
    }

    /**
     * Saves the given map to a file.
     *
     * @param p_map      the map to be saved
     * @param p_filename the name of the file to save the map to
     * @throws IOException if an I/O error occurs during saving
     */
    @Override
    public void saveMap(Map p_map, String p_filename) throws IOException {
        d_mapManager.saveMap(p_map, p_filename);
    }

    /**
     * Loads a map from a file.
     *
     * @param p_map      the map object to load data into
     * @param p_filename the name of the file to load the map from
     * @throws IOException if an I/O error occurs during loading
     */
    @Override
    public void loadMap(Map p_map, String p_filename) throws IOException {
        d_mapManager.loadMap(p_map, p_filename);
    }
}
