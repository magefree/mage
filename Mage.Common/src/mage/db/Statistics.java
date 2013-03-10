package mage.db;

import mage.db.model.Log;

import java.util.*;

/**
 * @author noxx
 */
public class Statistics {

    public static void main(String[] args) throws Exception {
        List<Log> logs = EntityManager.instance.getAllLogs();
        System.out.println("logs found: " + logs.size());

        Map<String, Integer> nicknames = displayCommonNumbers(logs);
        List<Integer> games = displayTop3(nicknames);
        displayPlayedOnlyOnce(games);

        System.out.println("Done");
    }

    private static void displayPlayedOnlyOnce(List<Integer> games) {
        Integer oneGame = 0;
        for (Integer numberOfGames : games) {
            if (numberOfGames == 1) {
                oneGame++;
            }
        }

        System.out.println("Number of players played only one game: " + oneGame);
    }

    private static List<Integer> displayTop3(Map<String, Integer> nicknames) {
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
        for (Map.Entry<String, Integer> entry : nicknames.entrySet()) {
            if (check(numbersToFind, entry.getValue())) {
                players.put(entry.getValue(), entry.getKey());
            }
            if (players.size() == 3) {
               break;
            }
        }

        System.out.println("Top-3");
        for (Map.Entry<Integer, String> entry : players.entrySet()) {
            System.out.println("   " + entry.getValue() + ": " + entry.getKey());
        }
        return games;
    }

    private static Map<String, Integer> displayCommonNumbers(List<Log> logs) {
        int count = 0;
        Map<String, Integer> nicknames = new HashMap<String, Integer>();
        for (Log log : logs) {
            if (log.getKey().equals("gameStarted")) {
                if (log.getArguments() != null) {
                    int index = 0;
                    for (String argument : log.getArguments()) {
                        if (index > 0) {
                            inc(nicknames, argument);
                        }
                        index++;
                    }
                }
                count++;
            }
        }

        System.out.println("********************************");
        System.out.println("Games played: " + count);
        System.out.println("Number of players: " + nicknames.size());
        return nicknames;
    }

    public static void inc(Map<String, Integer> map, String player) {
        if (map.containsKey(player)) {
            Integer count = map.get(player);
            count++;
            map.put(player, count);
        } else {
            map.put(player, 1);
        }
    }
    
    public static boolean check(List<Integer> numbers, Integer value) {
        for (Integer number : numbers) {
            if (number.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
