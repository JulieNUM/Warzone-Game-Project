package com.tyrcl.gameplay.model;

import java.util.*;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.model.cards.AirliftOrder;
import com.tyrcl.gameplay.model.cards.BlockadeOrder;
import com.tyrcl.gameplay.model.cards.BombOrder;
import com.tyrcl.gameplay.model.cards.DiplomacyOrder;
import com.tyrcl.gameplay.strategy.CheaterBehavior;
import com.tyrcl.gameplay.strategy.HumanBehavior;
import com.tyrcl.gameplay.strategy.PlayerBehavior;
import com.tyrcl.mapeditor.model.Continent;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.mapeditor.model.Map;
import com.tyrcl.utils.Helper;

/**
 * Represents a player in the game. Each player has a unique ID, a name,
 * a list of owned countries, an order queue, and a reinforcement pool.
 */
public class Player {
    private static int D_NEXTID = 1;
    private int d_ID;
    private String d_name;
    private List<Country> d_ownedCountries;
    private Queue<Order> d_dployOrders;
    private Queue<Order> d_orders;
    private int d_reinforcementPool;
    private String d_nextCommand = null;
    private boolean d_hasPlacedAllOrders = false;
    private Map d_map;
    private Scanner d_scanner;
    private LogEntryBuffer d_logEntryBuffer;
    private List<Card> d_cards; // Add a list to store the cards owned by the player
    private Card d_cardReceivedCurrentTurn;
    private List<Player> d_diplomacyPartners;
    private PlayerBehavior d_playerBehavior;

    /**
     * Constructs a new player with a given name and associated game engine.
     *
     * @param p_name The name of the player.
     */
    public Player(String p_name, LogEntryBuffer p_logEntryBuffer) {
        this.d_ID = D_NEXTID++;
        this.d_name = p_name;
        this.d_ownedCountries = new LinkedList<>();
        this.d_dployOrders = new LinkedList<>();
        this.d_orders = new LinkedList<>();
        this.d_reinforcementPool = 0;
        this.d_hasPlacedAllOrders = false;
        this.d_scanner = new Scanner(System.in);
        this.d_logEntryBuffer = p_logEntryBuffer;
        this.d_cards = new LinkedList<>(); // Initialize the card list
        this.d_cardReceivedCurrentTurn = null;
        this.d_diplomacyPartners = new ArrayList<Player>();
        this.d_playerBehavior = new HumanBehavior();
    }

    /**
     * Sets the order issuance strategy for this player
     */
    public void setOrderStrategy(PlayerBehavior p_strategy) {
        this.d_playerBehavior = p_strategy;
    }

    /**
     * Gets the current order issuance strategy
     */
    public PlayerBehavior getOrderStrategy() {
        return this.d_playerBehavior;
    }

    /**
     * Obtain a card to the player's card list.
     *
     * @return A message indicating the card obtained.
     */
    public String obtainCard() {
        if (d_cardReceivedCurrentTurn == null) {
            Card[] cards = Card.values();
            Random random = new Random();
            int randomIndex = random.nextInt(cards.length); // 3
            d_cardReceivedCurrentTurn = cards[randomIndex];
            d_cards.add(d_cardReceivedCurrentTurn);
            String msg = this.d_name + " has obtained a '" + d_cardReceivedCurrentTurn.name() + "' card";
            return msg;
        }
        return "";
    }

    /**
     * Check if the player has an Airlift card
     *
     * @return true if the player has an Airlift card, otherwise false
     */
    public boolean hasAirliftCard() {
        for (Card card : d_cards) {
            if (card.getType().equals("airlift")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove one Airlift card from the player
     */

    public void removeAirliftCard() {
        for (int i = 0; i < d_cards.size(); i++) {
            if (d_cards.get(i).getType().equals("airlift")) {
                d_cards.remove(i);
                return;
            }
        }
    }

    /**
     * Get the list of cards owned by the player
     *
     * @return the player's card list
     */
    public List<Card> getCards() {
        return d_cards;
    }

    /**
     * Check if the player has a Blockade card
     *
     * @return true if the player has a Blockade card, otherwise false
     */

    public boolean hasBlockadeCard() {
        for (Card card : d_cards) {
            if (card.getType().equals("blockade")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove one Blockade card from the player
     */
    public void removeBlockadeCard() {
        for (int i = 0; i < d_cards.size(); i++) {
            if (d_cards.get(i).getType().equals("blockade")) {
                d_cards.remove(i);
                return;
            }
        }
    }

    /**
     * Check if the player has a Diplomacy card.
     *
     * @return true if the player has a Diplomacy card, otherwise false.
     */
    public boolean hasDiplomacyCard() {
        for (Card card : d_cards) {
            if (card.getType().equals("diplomacy")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove one Diplomacy card from the player.
     */
    public void removeDiplomacyCard() {
        for (int i = 0; i < d_cards.size(); i++) {
            if (d_cards.get(i).getType().equals("diplomacy")) {
                d_cards.remove(i);
                return;
            }
        }
    }

    /**
     * Check if the player has a Bomb card.
     *
     * @return true if the player has a Bomb card, otherwise false.
     */
    public boolean hasBoomCard() {
        for (Card card : d_cards) {
            if (card.getType().equals("bomb")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove one Bomb card from the player.
     */
    public void removeBoomCard() {
        for (int i = 0; i < d_cards.size(); i++) {
            if (d_cards.get(i).getType().equals("bomb")) {
                d_cards.remove(i);
                return;
            }
        }
    }

    /**
     * Adds a diplomacy partner to the player's list of partners.
     *
     * @param p_partner The player to add as a diplomacy partner.
     */
    public void addDiplomacyPartner(Player p_partner) {
        if (!d_diplomacyPartners.contains(p_partner)) {
            d_diplomacyPartners.add(p_partner);
        }
    }

    /**
     * Verifies if a player is a diplomacy partner.
     *
     * @param p_partner The player to verify.
     * @return true if the player is a diplomacy partner, otherwise false.
     */
    public boolean verifyDiplomacyPartner(Player p_partner) {
        if (d_diplomacyPartners != null && p_partner != null) {
            for (Player l_Player : d_diplomacyPartners) {
                if (l_Player.getd_name().equals(p_partner.getd_name())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the player has a Reinforcement card
     *
     * @return true if the player has a Reinforcement card, otherwise false
     */

    public boolean hasReinforcementCard() {
        for (Card card : d_cards) {
            if (card.getType().equals("Reinforcement")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove one Reinforcement card from the player
     */
    public void removeReinforcementCard() {
        for (int i = 0; i < d_cards.size(); i++) {
            if (d_cards.get(i).getType().equals("Reinforcement")) {
                d_cards.remove(i);
                return;
            }
        }
    }

    /**
     * Prints the list of cards owned by the player.
     * If the player has no cards, it prints a message indicating that.
     */
    public void printCards() {
        if (d_cards.size() == 0) {
            System.out.println(this.d_name + " doesn't have any cards.");
        } else {
            String l_cards = "Cards: ";
            for (Card l_card : d_cards) {
                l_cards += "<" + l_card.name() + "> ";
            }
            System.out.println(l_cards);
        }
    }

    /**
     * Returns a string representation of the player's current status,
     * including owned countries and remaining reinforcements.
     *
     * @return A formatted string of player details.
     */
    @Override
    public String toString() {
        StringBuilder l_countries = new StringBuilder();
        for (Country l_country : d_ownedCountries) {
            l_countries.append(l_country.getCountryID() + " " + l_country.getCountryName() + "\n");
        }
        return "Player " + d_name + " remaining reinforcements: " + d_reinforcementPool + "\nCurrent countries:\n"
                + l_countries.toString();
    }

    /**
     * Add order to the player's order queue.
     */
    public void issue_order() {
        if (d_playerBehavior.getBehaviorName().equals("Cheater")) {
            if (d_playerBehavior instanceof CheaterBehavior) {
                ((CheaterBehavior) d_playerBehavior).issueOrder(this);
                System.out.println("[Cheater] Cheating logic executed for " + this.getd_name());
                this.d_hasPlacedAllOrders = true;
            }
            return;
        }

        Order l_order = d_playerBehavior.createOrder(this, this.getListOfPlayers());
        if (l_order != null) {
            boolean l_isDeploy = l_order.getName().equals("deploy");
            if (!d_playerBehavior.getBehaviorName().equals("Human")) {
                l_order.preValidate();
            }
            if (l_isDeploy) {
                d_dployOrders.add(l_order);
            } else {
                d_orders.add(l_order);
            }
        }
    }

    /**
     * Creates an order based on the provided command parts.
     *
     * @param p_orderParts The parts of the order command.
     * @return The created Order object, or null if the command is invalid.
     */
    public Order create_Order(String[] p_orderParts) {
        try {
            String l_orderType = p_orderParts[0];
            switch (l_orderType) {
                case "deploy":
                    if (verifyCommandFormat(p_orderParts, 3)) {
                        return new DeployOrder(this, getCountryByID(Integer.parseInt(p_orderParts[1])),
                                Integer.parseInt(p_orderParts[2]), d_logEntryBuffer);
                    } else {
                        return null;
                    }
                case "advance":
                    if (verifyCommandFormat(p_orderParts, 4)) {
                        return new AdvanceOrder(this, getCountryByID(Integer.parseInt(p_orderParts[1])),
                                getCountryByID(Integer.parseInt(p_orderParts[2])),
                                Integer.parseInt(p_orderParts[3]), d_logEntryBuffer);
                    } else {
                        return null;
                    }
                case "bomb":
                    if (verifyCommandFormat(p_orderParts, 2)) {
                        return new BombOrder(this, getCountryByID(Integer.parseInt(p_orderParts[1])), d_logEntryBuffer);
                    } else {
                        return null;
                    }
                case "blockade":
                    if (verifyCommandFormat(p_orderParts, 2)) {
                        return new BlockadeOrder(this, getCountryByID(Integer.parseInt(p_orderParts[1])),
                                d_logEntryBuffer);
                    } else {
                        return null;
                    }
                case "airlift":
                    if (verifyCommandFormat(p_orderParts, 4)) {
                        return new AirliftOrder(this, getCountryByID(Integer.parseInt(p_orderParts[1])),
                                getCountryByID(Integer.parseInt(p_orderParts[2])),
                                Integer.parseInt(p_orderParts[3]), d_logEntryBuffer);
                    } else {
                        return null;
                    }
                case "negotiate":
                    if (verifyCommandFormat(p_orderParts, 2)) {
                        return new DiplomacyOrder(this, getPlayerFromPlayerList(p_orderParts[1]),
                                d_logEntryBuffer);
                    } else {
                        return null;
                    }
                case "quit":
                    return new QuitOrder();
                default:
                    System.out.println("Unknown order type, please try again!");
                    return null;

            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format! Please enter valid integers"); // no need for Exception, just
                                                                                      // request another input
            return null;
        }
    }

    /**
     * This method is called by the game engine druing the excution phase
     * 
     * @return the head of the queue if the queue is not empty, null if the queue is
     *         empty.
     */
    public Order next_order() {
        if (!d_dployOrders.isEmpty()) {
            // System.out.println("Processing deploy order...");
            return d_dployOrders.poll();
        }

        if (!d_orders.isEmpty()) {
            return d_orders.poll();
        }

        return null;
    }

    /**
     * Calcualte Continent bonuses
     * 
     * @return number of armies as bonuses
     */
    public int calculateContinentBonuses() {
        int l_totalBonuses = 0;
        for (Continent l_continent : d_map.getContinents().values()) {
            int l_continentID = l_continent.getContinentID();
            if (this.ownsEntireContinent(l_continentID)) {
                l_totalBonuses += l_continent.getArmyValue();
            }
        }
        return l_totalBonuses;
    }

    /**
     * Verfiy if the player owns the continent
     * 
     * @param p_continentID the ID of the continent
     * @return ture if the player owns the continent, false if it doesn't
     */
    public boolean ownsEntireContinent(int p_continentID) {
        Set<Integer> ownedCountryIds = new HashSet<>();
        for (Country country : this.getd_ownedCountries()) {
            ownedCountryIds.add(country.getCountryID());
        }

        Set<Integer> continentCountryIds = new HashSet<>(d_map.getCountriesByContinent(p_continentID));
        return ownedCountryIds.containsAll(continentCountryIds);
    }

    /**
     * Checks if the player owns a specified country.
     *
     * @param p_country_id The ID of the country.
     * @return True if the player owns the country, false otherwise.
     */
    public boolean ownsCountry(int p_country_id) {
        for (Country l_country : d_ownedCountries) {
            if (l_country.getCountryID() == p_country_id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the country by ID.
     *
     * @return the country
     */
    public Country getCountryByID(int p_countryID) {
        return d_map.getCountries().get(p_countryID);
    }

    /**
     * Gets the country ID.
     *
     * @return the country ID (d_ID)
     */
    public int getd_ID() {
        return d_ID;
    }

    /**
     * Sets the country ID.
     *
     * @param p_ID the country ID to set
     * @throws IllegalArgumentException if the provided ID is less than 0
     */
    public void setd_ID(int p_ID) {
        if (p_ID < 0) {
            throw new IllegalArgumentException("Invalid country ID, must be a positive integer.");
        }
        this.d_ID = p_ID;
    }

    /**
     * Gets the name of the country.
     *
     * @return the country name (d_name)
     */
    public String getd_name() {
        return d_name;
    }

    /**
     * Sets the name of the country.
     *
     * @param p_name the country name to set
     * @throws IllegalArgumentException if the provided name is null or empty
     */
    public void setd_name(String p_name) {
        if (p_name == null || p_name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid name, it must be a non-empty string.");
        }
        this.d_name = p_name;
    }

    /**
     * Gets the list of countries owned by this country.
     *
     * @return a list of countries (d_ownedCountries)
     */
    public List<Country> getd_ownedCountries() {
        d_ownedCountries.clear();
        for (Country country : d_map.getCountries().values()) {
            if (country.getPlayer() != null && country.getPlayer().equals(this)) {
                d_ownedCountries.add(country);
            }
        }
        return d_ownedCountries;
    }

    /**
     * Sets the list of countries owned by this country.
     *
     * @param p_ownedCountries the list of owned countries to set
     * @throws IllegalArgumentException if the provided list is null
     */
    public void setd_ownedCountries(List<Country> p_ownedCountries) {
        if (p_ownedCountries == null) {
            throw new IllegalArgumentException("Invalid input, ownedCountries cannot be null.");
        }
        this.d_ownedCountries = p_ownedCountries;
    }

    /**
     * Gets the queue of orders for this country.
     *
     * @return the queue of orders (d_orders)
     */
    public Queue<Order> getd_orders() {
        return d_orders;
    }

    /**
     * Sets the queue of orders for this country.
     *
     * @param p_orders the queue of orders to set
     * @throws IllegalArgumentException if the provided queue is null
     */
    public void setd_orders(Queue<Order> p_orders) {
        if (p_orders == null) {
            throw new IllegalArgumentException("Invalid input, orders cannot be null.");
        }
        this.d_orders = p_orders;
    }

    /**
     * Gets the reinforcement pool value for this country.
     *
     * @return the reinforcement pool value (d_reinforcementPool)
     */
    public int getd_reinforcementPool() {
        return d_reinforcementPool;
    }

    /**
     * Sets the reinforcement pool value for this country.
     *
     * @param p_reinforcementPool the reinforcement pool value to set
     * @throws IllegalArgumentException if the provided reinforcement pool value is
     *                                  less than 0
     */
    public void setd_reinforcementPool(int p_reinforcementPool) {
        if (p_reinforcementPool < 0) {
            throw new IllegalArgumentException("Invalid input, must be a positive integer.");
        }
        this.d_reinforcementPool = p_reinforcementPool;
    }

    /**
     * Check if all orders have been placed
     * 
     * @return ture placed all order, false have not placed all orders.
     */
    public boolean hasPlacedAllOrders() {
        return d_hasPlacedAllOrders;
    }

    /**
     * Set its map by passing a map
     * 
     * @param p_map the game map data
     */
    public void setMap(Map p_map) {
        this.d_map = p_map;
    }

    /**
     * Get the Map of the player
     * 
     * @return the game map
     */
    public Map getMap() {
        return this.d_map;
    }

    /**
     * Resets the player's state for a new turn.
     * This includes removing the card received from the last turn,
     * clearing the list of diplomacy partners, and resetting the done command.
     */
    public void resetForNewTurn() {
        // remove card received from the last turn
        d_cardReceivedCurrentTurn = null;
        // remove diplomacy partners
        d_diplomacyPartners = new ArrayList<Player>();
        // reset the done command
        d_hasPlacedAllOrders = false;
        if (this.d_playerBehavior != null) {
            this.d_playerBehavior.turnReset();
        }

    }

    /**
     * Resets the player's state for a new game.
     */
    public void reset() {
        d_cardReceivedCurrentTurn = null;
        d_diplomacyPartners = new ArrayList<Player>();
        d_hasPlacedAllOrders = false;
        d_dployOrders.clear();
        d_orders.clear();
        d_ownedCountries.clear();
        d_reinforcementPool = 0;
        d_cards.clear();
    }

    /**
     * Sets the next command to be executed.
     *
     * @param p_nextCommand the next command to set
     */
    public void setNextCommand(String p_nextCommand) {
        d_nextCommand = p_nextCommand;
    }

    /**
     * Verifies the format of the command.
     *
     * @param p_orderParts the parts of the order command
     * @param requiredNum  the required number of parts for the command
     * @return true if the command format is valid, false otherwise
     */
    private boolean verifyCommandFormat(String[] p_orderParts, int requiredNum) {
        if (p_orderParts.length != requiredNum) {
            System.out.println("Invalid deploy order format!");
            return false;
        }
        return true;
    }

    /**
     * Mark player has done placing all orders.
     */
    public void setHasPlacedAllOrders() {
        d_hasPlacedAllOrders = true;
    }

    /**
     * get log buffer
     * 
     * @return log buffer
     */
    public LogEntryBuffer getLogBuffer() {
        return d_logEntryBuffer;
    }

    /**
     * Retrieves a list of all unique players in the game based on the countries
     * they own.
     * 
     * @return a list of {@code Player} objects representing all active players in
     *         the game.
     */
    public List<Player> getListOfPlayers() {
        List<Player> l_players = new ArrayList<>();
        for (Country l_country : this.d_map.getCountries().values()) {
            Player l_p = l_country.getPlayer();
            if (l_p != null && !l_players.contains(l_p)) {
                l_players.add(l_p);
            }
        }
        return l_players;
    }

    /**
     * Finds and returns a player from the list of active players by their name.
     * 
     * @param p_name the name of the player to search for.
     * @return the {@code Player} object if found, otherwise {@code null}.
     */
    public Player getPlayerFromPlayerList(String p_name) {
        List<Player> l_list = getListOfPlayers();
        for (Player l_player : l_list) {
            if (l_player.getd_name().equals(p_name)) {
                return l_player;
            }
        }
        return null;
    }

}
