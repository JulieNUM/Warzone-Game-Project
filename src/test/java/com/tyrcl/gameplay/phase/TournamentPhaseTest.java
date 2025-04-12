package com.tyrcl.gameplay.phase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.gameplay.strategy.AggressiveBehavior;
import com.tyrcl.gameplay.strategy.BenevolentBehavior;
import com.tyrcl.gameplay.strategy.CheaterBehavior;
import com.tyrcl.gameplay.strategy.RandomBehavior;

public class TournamentPhaseTest {

    @Test
    public void TestTournamentPlay() throws IOException {
        List<String> d_mapFileNames;
        List<Player> d_players;
        int d_numGames;
        int d_maxTurns;
        List<String> d_gameResults;
        d_mapFileNames = new ArrayList<>();
        d_players = new ArrayList<>();
        d_numGames = 0;
        d_maxTurns = 0;
        d_gameResults = new ArrayList<>();
        GameEngine d_ge = new GameEngine();

        String l_input = "tournament -M map1.txt map2.txt map3.txt -P Random Benevolent Aggressive -G 2 -D 20";
        String[] parts = l_input.trim().split(" ");

        String l_setupSwitch = null;
        String l_newPlayerName = null;
        for (int i = 1; i < parts.length; i++) {
            switch (parts[i]) {
                case "-M":
                    l_setupSwitch = "MAP";
                    break;
                case "-P":
                    l_setupSwitch = "PLAYER";
                    break;
                case "-G":
                    d_numGames = Integer.parseInt(parts[i + 1]);
                    i++;
                    break;
                case "-D":
                    d_maxTurns = Integer.parseInt(parts[i + 1]);
                    i++;
                    break;
                default:
                    if (l_setupSwitch.equals("MAP")) {
                        d_mapFileNames.add(parts[i]);
                    } else if (l_setupSwitch.equals("PLAYER")) {
                        switch (parts[i]) {
                            case "Aggressive":
                                l_newPlayerName = "P" + d_players.size() + "-Aggressive";
                                d_players.add(new Player(l_newPlayerName, d_ge.d_logEntryBuffer));
                                d_players.get(d_players.size() - 1)
                                        .setOrderStrategy(new AggressiveBehavior());
                                break;
                            case "Benevolent":
                                l_newPlayerName = "P" + d_players.size() + "-Benevolent";
                                d_players.add(new Player(l_newPlayerName, d_ge.d_logEntryBuffer));
                                d_players.get(d_players.size() - 1)
                                        .setOrderStrategy(new BenevolentBehavior());
                                break;
                            case "Random":
                                l_newPlayerName = "P" + d_players.size() + "-Random";
                                d_players.add(new Player(l_newPlayerName, d_ge.d_logEntryBuffer));
                                d_players.get(d_players.size() - 1)
                                        .setOrderStrategy(new RandomBehavior());
                                break;
                            case "Cheater":
                                l_newPlayerName = "P" + d_players.size() + "-Cheater";
                                d_players.add(new Player(l_newPlayerName, d_ge.d_logEntryBuffer));
                                d_players.get(d_players.size() - 1)
                                        .setOrderStrategy(new CheaterBehavior());
                                break;
                            default:
                                System.out.println("Invalid player strategy: " + parts[i]);
                        }
                    }
                    break;
            }
        }
        System.out.println(this.toString());

        for (int i = 0; i < d_mapFileNames.size(); i++) {
            System.out.println("Map " + (i + 1) + ": " + d_mapFileNames.get(i));

            int l_gameCounter = 0;
            while (l_gameCounter < d_numGames) {
                d_ge.reset();
                d_ge.loadMap(d_mapFileNames.get(i));
                for (Player player : d_players) {
                    player.reset();
                    d_ge.addPlayer(player);
                }
                d_ge.getGameMap().assignAdjacentCountriesToPlayers(d_ge.getPlayers(), 2);

                d_ge.getGameMap().initMapReiforcement();
                d_ge.startAutoPlay(d_maxTurns, d_gameResults);
                l_gameCounter++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Tournament Results:\n");

        int l_playerWidth = 6;
        for (int i = 0; i < d_players.size(); i++) {
            l_playerWidth = Math.max(l_playerWidth, d_players.get(i).getd_name().length()) + 1;
        }
        int l_lineWidth = 9 + (l_playerWidth + 2) * d_numGames;
        String l_inner = "-".repeat(l_lineWidth) + "\n";
        String l_outer = "=".repeat(l_lineWidth) + "\n";
        sb.append(l_outer);
        sb.append(String.format("‖ %-6s", "Map"));
        for (int i = 1; i <= d_numGames; i++) {
            sb.append(String.format("| %-" + l_playerWidth + "s", "Game" + i));
        }
        sb.append("‖\n");
        sb.append(l_inner);
        for (int i = 0; i < d_mapFileNames.size(); i++) {
            sb.append(String.format("‖ %-6s", "Map" + (i + 1)));
            for (int j = 0; j < d_numGames; j++) {
                sb.append(String.format("| %-" + l_playerWidth + "s", d_gameResults.get(i * d_numGames + j)));
            }
            sb.append("‖\n");
        }
        sb.append(l_outer);
        System.out.println(sb.toString());

        Assert.assertNotNull(sb);
        
    }
}
