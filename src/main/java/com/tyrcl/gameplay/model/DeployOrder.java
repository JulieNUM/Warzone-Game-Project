package com.tyrcl.gameplay.model;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.mapeditor.model.Country;


/**
 * Represents a deployment order, which assigns a specified number of armies to
 * a target country.
 */
public class DeployOrder implements Order {
    private int d_numArmies;
    private Country d_country;
    private Player d_player;
    private LogEntryBuffer d_logEntryBuffer;

    /**
     * Constructs a DeployOrder with the specified player, target country, number of armies, and log entry buffer.
     *
     * @param p_player          The player issuing the order.
     * @param p_targetCountry   The target country object.
     * @param p_numArmies       The number of armies to be deployed.
     * @param p_logEntryBuffer  The log entry buffer for logging order execution details.
     */
    public DeployOrder(Player p_player, Country p_targetCountry, int p_numArmies, LogEntryBuffer p_logEntryBuffer) {
        this.d_player = p_player;
        this.d_numArmies = p_numArmies;
        this.d_country = p_targetCountry;
        this.d_logEntryBuffer = p_logEntryBuffer;
    }

    /**
     * Creates and validates a DeployOrder before instantiation.
     *
     * @param p_player          The player issuing the order.
     * @param p_targetCountryID The ID of the target country.
     * @param p_numArmies       The number of armies to be deployed.
     * @return A new DeployOrder object if valid, otherwise null.
     */
    //public static DeployOrder createDeployOrder(Player p_player, int p_targetCountryID, int p_numArmies) {
    //    GameEngine gameEngine = p_player.getd_gameEngine();
    //    Country country = gameEngine.getCountryByID(p_targetCountryID);
    //    return new DeployOrder(p_player, p_targetCountryID, p_numArmies);
    //}

    /**
     * Executes the deployment order, adding the specified number of armies to the
     * target country.
     */
    @Override
    public void execute() {
    }

    /**
     * Validates the deployment order before execution.
     *
     * @return True if the player has enough reinforcements and owns the target
     *         country, false otherwise.
     */
    @Override
    public boolean validate() {
        if (d_country == null) {
            System.out.println("Validation Failed: Target country does not exist.");
            return false;
        }

        if (!d_player.ownsCountry(d_country.getCountryID())) {
            System.out.println("Validation Failed: Player does not own the target country.");
            return false;
        }

        if (d_numArmies <= 0) {
            System.out.println("Validation Failed: Invalid number of armies.");
            return false;
        }

        return true;
    }

    /**
     * Prints the details of the deployment order.
     */
    @Override
    public void print() {
        String l_msg = d_player.getd_name() + " deployed " + d_numArmies + " armies to country " + d_country.getCountryID();
        System.out.println(l_msg);
        d_logEntryBuffer.setLogEntry(l_msg);
    }

    /**
     * Validates the deployment order before execution.
     * This method checks if the target country exists, if the player owns the target country,
     * if the player has enough reinforcements, and if the number of armies to be deployed is valid.
     *
     * @return true if the deployment order is valid, false otherwise.
     */
    @Override
    public boolean preValidate() {
        if (d_country == null) {
            System.out.println("Validation Failed: Target country does not exist.");
            return false;
        }

        if (!d_player.ownsCountry(d_country.getCountryID())) {
            System.out.println("Validation Failed: Player does not own the target country.");
            return false;
        }

        if (d_player.getd_reinforcementPool() < d_numArmies) {
            System.out.println("Validation Failed: Not enough armies in reinforcement pool.");
            return false;
        }

        if (d_numArmies <= 0) {
            System.out.println("Validation Failed: Invalid number of armies.");
            return false;
        }

        d_player.setd_reinforcementPool(d_player.getd_reinforcementPool() - d_numArmies);
        d_country.setNumOfArmies(d_country.getNumOfArmies() + d_numArmies);
        return true;
    }

    @Override
    public String getName() {
       return "deploy";
    }
}
