package com.tyrcl.gameplay.model.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tyrcl.common.model.MapType;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.mapeditor.model.Map;


/**
 * Represents the game data, including continents, countries, adjacency list, 
 * map type, and players participating in the game.
 */
public class GameData {

    /**
     * A list of continents in the game.
     */
    public List<ContinentData> d_continents = new ArrayList<>();

    /**
     * A list of countries in the game.
     */
    public List<CountryData> d_countries = new ArrayList<>();

    /**
     * An adjacency list representing the connectivity between countries.
     * The key is a country ID, and the value is a list of neighboring country IDs.
     */
    public HashMap<Integer, List<Integer>> d_adjacencyList = new HashMap<>();

    /**
     * The type of the map being used in the game.
     */
    public MapType d_mapType;

    /**
     * A list of players participating in the game.
     */
    public List<PlayerData> d_players = new ArrayList<>();


    /**
     * Converts the current game state into game data by extracting relevant 
     * information from a list of players.
     *
     * @param p_players The list of players whose data will be used to populate 
     *                  the game data structure.
     */
    public void ConvertGameStateToGameData(List<Player> p_players) {
        if (p_players.size() > 0) {
            Map map = p_players.get(0).getMap();

            // Extract continent data
            map.d_continents.forEach((a, b) -> {
                d_continents.add(new ContinentData(a, b.getContinentName(), b.getArmyValue()));
            });

            // Extract country data
            map.d_countries.forEach((a, b) -> {
                d_countries.add(new CountryData(a, b.getContinentID(), b.getCountryName(), b.getNumOfArmies(),
                        b.getPlayerName()));
            });

            // Extract adjacency list
            d_adjacencyList = (HashMap<Integer, List<Integer>>) map.d_adjacencyList;

            // Extract map type
            d_mapType = map.d_mapType;

            // Extract player data
            for (Player l_player : p_players) {
                PlayerData l_data = new PlayerData();
                l_data.d_name = l_player.getd_name();
                l_data.d_behavior = l_player.getOrderStrategy().getBehaviorName();

                // Extract player cards
                l_player.getCards().forEach(c -> {
                    l_data.d_cards.add(c.name());
                });

                d_players.add(l_data);
            }
        }
    }
}
