package com.xmage.core.entity.repositories.impl;

import com.xmage.core.entity.model.ServerStats;
import com.xmage.core.entity.repositories.XMageStatsRepository;
import mage.db.EntityManager;
import mage.db.model.Log;
import mage.server.services.LogKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Implementation for {@link com.xmage.core.entity.repositories.XMageStatsRepository}
 *
 * @author noxx
 */
public class XMageStatsRepositoryImpl implements XMageStatsRepository {

    private static final Logger logger = LoggerFactory.getLogger(XMageStatsRepositoryImpl.class);

    @Override
    public ServerStats getServerStats() {
        ServerStats serverStats = new ServerStats();

        List<Log> logs = EntityManager.instance.getAllLogs();
        logger.info("logs found count: " + logs.size());

        int numberOfGamesPlayed = 0;
        Set<String> playerNames = new HashSet<String>();

        // Get nicknames and games started count
        Map<String, Integer> nicknames = new HashMap<String, Integer>();
        for (Log log : logs) {
            if (log.getKey().equals(LogKeys.KEY_GAME_STARTED)) {
                if (log.getArguments() != null) {
                    int index = 0;
                    for (String argument : log.getArguments()) {
                        if (index > 0) {
                            inc(nicknames, argument);
                        }
                        index++;
                    }
                }
                numberOfGamesPlayed++;
            }
        }

        // Sort games
        Collection<Integer> values = nicknames.values();
        List<Integer> games = new ArrayList<Integer>();
        games.addAll(values);
        Collections.sort(games, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return i2.compareTo(i1);
            }
        });

        // Top-3
        List<Integer> numbersToFind = new ArrayList<Integer>();
        for (Integer numberOfGames : games) {
            numbersToFind.add(numberOfGames);
            if (numbersToFind.size() == 3) {
                break;
            }
        }

        Map<Integer, String> players = new LinkedHashMap<Integer, String>();
        for (Integer number : numbersToFind) {
            for (Map.Entry<String, Integer> entry : nicknames.entrySet()) {
                if (entry.getValue().equals(number)) {
                    players.put(entry.getValue(), entry.getKey());
                    break;
                }
            }
            if (players.size() == 3) {
                break;
            }
        }

        // Build top-3 string
        StringBuilder top3 = new StringBuilder();
        for (Map.Entry<Integer, String> entry : players.entrySet()) {
            top3.append("[").append(entry.getValue()).append(":").append(entry.getKey()).append("]");
        }

        // Played only once
        Integer oneGamePlayers = 0;
        for (Integer numberOfGames : games) {
            if (numberOfGames == 1) {
                oneGamePlayers++;
            }
        }

        serverStats.setNumberOfGamesPlayed(numberOfGamesPlayed);
        serverStats.setNumberOfUniquePlayers(nicknames.size());
        serverStats.setTop3Players(top3.toString());
        serverStats.setNumberOfPlayersPlayedOnce(oneGamePlayers);

        return serverStats;
    }

    private static void inc(Map<String, Integer> map, String player) {
        if (map.containsKey(player)) {
            Integer count = map.get(player);
            count++;
            map.put(player, count);
        } else {
            map.put(player, 1);
        }
    }

    private static boolean check(List<Integer> numbers, Integer value) {
        for (Integer number : numbers) {
            if (number.equals(value)) {
                return true;
            }
        }
        return false;
    }
}