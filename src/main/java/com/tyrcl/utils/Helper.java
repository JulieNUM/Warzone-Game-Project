package com.tyrcl.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;

import com.tyrcl.mapeditor.model.Continent;
import com.tyrcl.mapeditor.model.Country;

/**
 * A utility class providing helper methods for manipulation.
 * This class contains static methods and cannot be instantiated.
 */
public class Helper {

    public final static String CONTINENT_COMMAND_USAGE = "[editcontinent -add continentID contienetvalue -remove continentID]";
    public final static String COUNTRY_COMMAND_USAGE = "[editcountry -add countryID continentID -remove countryID]";
    public final static String NEIGHBOR_COMMAND_USAGE = "[editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID]";
    public final static String INVALID_COMMAND_USAGE = "Invalid command. Available commands: editmap, editcontinent, editcountry, editneighbor, showmap, validatemap and savemap.";
    public final static String LOG_DIRECTORY = "risk/logs/";
    public final static Integer INITIAL_ARMY = 2;

    /**
     * Generate the text of the command instructions using a specific format
     * 
     * @param p_text the text of the command
     * @return the text of the command with specified format
     */
    public static String generateCommandString(String p_text) {
        String l_returnStr = String.format("->%s", p_text);
        return l_returnStr;
    }

    /**
     * Generate the text of the gameplay title using a specific format
     * 
     * @param p_text the text of the command
     * @return the text of the command with specified format
     */
    public static String generateGameplayTitle(String p_text) {
        String l_returnStr = String.format("\n=====<%s>=====", p_text);
        return l_returnStr;
    }

    /**
     * Generate an ID based on the max key value in the map
     * 
     * @param p_map the map object
     * @return a new ID
     */
    public static int generateID(Map<Integer, ? extends Object> p_map) {
        int l_index = 0;
        for (Integer l_key : p_map.keySet()) {
            if (l_index < l_key) {
                l_index = l_key;
            }
        }
        l_index++;
        return l_index;
    }

    /**
     * Search for the ID of the continent in the given continent list
     * 
     * @param p_continentName the name of the continent
     * @param p_continents    the continent list
     * @return the ID of the continent
     */
    public static int getContinentIDByName(String p_continentName, Map<Integer, Continent> p_continents) {
        int l_continentID = 0;
        for (Integer l_key : p_continents.keySet()) {

            if (p_continentName.toLowerCase().equals(p_continents.get(l_key).getContinentName().toLowerCase())) {
                l_continentID = l_key;
            }
        }
        return l_continentID;
    }

    /**
     * Search for the ID of the country in the given country list
     * 
     * @param p_countryName the name of the country
     * @param p_countries   the country list
     * @return the ID of the country
     */
    public static int getCountryIDByName(String p_countryName, Map<Integer, Country> p_countries) {
        int l_countryID = 0;
        for (Integer l_key : p_countries.keySet()) {

            if (p_countryName.toLowerCase().equals(p_countries.get(l_key).getCountryName().toLowerCase())) {
                l_countryID = l_key;
            }
        }
        return l_countryID;
    }

    /**
     * Helper method to split a string into lines of a specified maximum length
     * 
     * @param p_text      the text needs to be displayed on the console
     * @param p_maxLength the maximun length of the column
     * @return a string with lines if the length of the text exceeds the maximum
     *         length
     */
    public static String[] splitIntoLines(String p_text, int p_maxLength) {

        if (p_text.length() <= p_maxLength) {
            return new String[] { p_text };
        }

        // Split the text into words
        String[] l_words = p_text.split(" ");
        StringBuilder l_line = new StringBuilder();
        java.util.List<String> l_lines = new java.util.ArrayList<>();

        for (String l_word : l_words) {
            if (l_line.length() + l_word.length() + 1 > p_maxLength) {
                l_lines.add(l_line.toString());
                l_line = new StringBuilder(l_word);
            } else {
                if (l_line.length() > 0) {
                    l_line.append(" ");
                }
                l_line.append(l_word);
            }
        }

        // Add the last line
        if (l_line.length() > 0) {
            l_lines.add(l_line.toString());
        }

        return l_lines.toArray(new String[0]);
    }

    /**
     * Generate a random number from the provided number
     * 
     * @param max the max number
     * @return a random number that's in the range (0,Max]
     */
    public static int generateRandomIntExclusiveInclusive(int p_max) {
        if (p_max <= 0) {
            return 0;
        }

        Random l_random = new Random();
        // nextInt(max) gives (0, max]
        int l_randomValue;
        do {
            l_randomValue = l_random.nextInt(p_max + 1); // Now range is [0, max]
        } while (l_randomValue == 0); // Exclude 0

        l_randomValue = p_max;
        return l_randomValue;
    }

    /**
     * Retrieves the absolute path of the current working directory.
     *
     * @return The absolute path of the current folder as a String.
     */
    private static String getCurrentFolderLocation() {
        return Paths.get("").toAbsolutePath().toString();
    }

    /**
     * Constructs the file path for a map file within the "risk/map" directory.
     *
     * @param p_fileName The name of the map file.
     * @return The full path of the map file as a String.
     */
    public static String getMapFileFolderLoction(String p_fileName) {
        return getCurrentFolderLocation() + "/risk/map/" + p_fileName;
    }

    /**
     * Constructs the file path for a data file within the "risk/data" directory.
     *
     * @param p_fileName The name of the data file.
     * @return The full path of the data file as a String.
     */
    public static String getMapDataFolderLocation(String p_fileName) {
        return getCurrentFolderLocation() + "/risk/data/" + p_fileName;
    }

    /**
     * List all files in the current folder.
     * 
     * @param p_currentDir current directory
     * @return all file names
     */
    public static String ListFilesInDirectory(String p_currentDir) {
        File l_folder = new File(p_currentDir);
        String l_fileNames = "";

        // List files in the directory
        File[] l_files = l_folder.listFiles();

        // Check if directory is valid
        if (l_files != null) {
            for (File l_file : l_files) {
                if (l_file.isFile()) {
                    l_fileNames += l_file.getName() + " ";
                }
            }
        } else {
            System.out.println("The folder path is invalid or empty.");
        }
     
        return l_fileNames;
        
    }

}
