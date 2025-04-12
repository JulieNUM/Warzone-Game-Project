package com.tyrcl.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Utility class for retrieving a random item from a given list.
 */
public class RandomItemFromList {

    /**
     * Returns a random item from the specified list.
     *
     * @param <T>  the type of elements in the list
     * @param list the list from which to retrieve a random item
     * @return a randomly selected item from the list
     * @throws IllegalArgumentException if the list is null or empty
     */
    public static <T> T getRandomItem(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
