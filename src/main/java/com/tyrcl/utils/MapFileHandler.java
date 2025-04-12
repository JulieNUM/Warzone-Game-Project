package com.tyrcl.utils;

import java.io.*;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.gameplay.model.data.GameData;
import com.tyrcl.mapeditor.model.Map;

/**
 * Utility class for handling map file operations such as saving and loading
 * maps.
 */
public class MapFileHandler {

    /**
     * Loads map data from a file and populates the map object.
     *
     * @param p_map      The map object to populate with the loaded data.
     * @param p_filename The name of the file to read the map data from.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static void loadMap(Map p_map, String p_filename) throws IOException {
        try {
            FileManager l_manager = FileReaderFactory.getFileReader(p_filename);
            l_manager.loadMap(p_map, Helper.getMapFileFolderLoction(p_filename));
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Saves the map data to a file.
     *
     * @param p_map      The map object containing continents, countries, and
     *                   borders.
     * @param p_filename The name of the file to save the map data.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public static void saveMap(Map p_map, String p_filename) throws IOException {
        try {
            FileManager l_manager = FileReaderFactory.getFileReader(p_filename);
            l_manager.saveMap(p_map, Helper.getMapFileFolderLoction(p_filename));
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * generate FileManager according to different file format
     */
    public class FileReaderFactory {
        public static FileManager getFileReader(String filePath) {
            if (filePath.endsWith(".txt")) {
                return new DominationMapFileAdapter(new DominationMapFileManager());
            } else if (filePath.endsWith(".map")) {
                return new ConquestMapFileAdapter(new ConquestMapFileManager());
            } else {
                throw new IllegalArgumentException("Unsupported file type: " + filePath);
            }
        }
    }

    /**
     * Method to save the game state to a file with a specific path
     * @param p_filename the name of the file
     * @param p_players players that play the game
     * @throws IOException if the file doesn't exist
     */
    public static void saveGame(String p_filename, List<Player> p_players) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL);
        // Specify the directory and filename for saving
        String directoryPath = "risk/data/"; // Directory to save the file
        File directory = new File(directoryPath);

        // Combine directory and file name to get the full path
        File file = new File(directory, p_filename);

        // Serialize the GameEngine object to a JSON file
        System.out.println("Saving game...");

        // Serialize the GameEngine object to a JSON file
        try {
            GameData l_data = new GameData();
            l_data.ConvertGameStateToGameData(p_players);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, l_data);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Game successfully saved to " + file.getAbsolutePath());
    }

    /**
     * Method to load the game state from a file with a specific path
     * @param p_filename the name of the file
     * @return the data of the game
     * @throws IOException if the file doesn't exist
     */
    public static GameData loadGame(String p_filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Specify the directory and filename for loading
        String directoryPath = "risk/data/"; 
        File directory = new File(directoryPath);

        // Combine directory and file name to get the full path
        File file = new File(directory, p_filename);

        // Load the JSON file into a GameEngine object
        GameData l_data = mapper.readValue(file, GameData.class);
  
        System.out.println("Game successfully loaded from " + file.getAbsolutePath());
        return l_data;
    }

}
