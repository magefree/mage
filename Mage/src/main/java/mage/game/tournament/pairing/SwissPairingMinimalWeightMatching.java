

package mage.game.tournament.pairing;

import mage.game.tournament.Round;
import mage.game.tournament.TournamentPairing;
import mage.game.tournament.TournamentPlayer;

import java.util.*;

/**
 * @author Quercitron
 */

// SwissPairingMinimalWeightMatching creates round pairings for swiss tournament.
// It assigns weight to each possible pair and searches perfect matching with minimal weight
// for more details see https://www.leaguevine.com/blog/18/swiss-tournament-scheduling-leaguevines-new-algorithm/
// This implementation don't use fast minimum weight maximum matching algorithm,
// it uses brute-force search, so it works reasonably fast only up to 16 players.

public class SwissPairingMinimalWeightMatching {

    private final int playersCount;

    private List<PlayerInfo> swissPlayers;

    // number of vertexes in graph
    private final int n;

    // weight of pairings
    private final int[][] w;

    public SwissPairingMinimalWeightMatching(List<TournamentPlayer> players, List<Round> rounds, boolean isLastRound) {
        playersCount = players.size();

        swissPlayers = new ArrayList<>();
        for (TournamentPlayer tournamentPlayer : players) {
            PlayerInfo swissPlayer = new PlayerInfo();
            swissPlayer.setTournamentPlayer(tournamentPlayer);
            swissPlayer.setPoints(tournamentPlayer.getPoints());
            swissPlayers.add(swissPlayer);
        }

        // shuffle players first to add some randomness
        Collections.shuffle(swissPlayers);
        Map<TournamentPlayer, Integer> map = new HashMap<>();
        for (int i = 0; i < playersCount; i++) {
            swissPlayers.get(i).setId(i);
            map.put(swissPlayers.get(i).getTournamentPlayer(), i);
        }

        // calculate Tie Breaker points -- Sum of Opponents' Scores (SOS)
        // see http://senseis.xmp.net/?SOS
        for (Round round : rounds) {
            for (TournamentPairing pairing : round.getPairs()) {
                TournamentPlayer player1 = pairing.getPlayer1();
                TournamentPlayer player2 = pairing.getPlayer2();

                Integer id1 = map.get(player1);
                Integer id2 = map.get(player2);

                // a player could have left the tournament, so we should check if id is not null
                if (id1 != null) {
                    swissPlayers.get(id1).setSosPoints(swissPlayers.get(id1).getSosPoints() + player2.getPoints());
                }
                if (id2 != null) {
                    swissPlayers.get(id2).setSosPoints(swissPlayers.get(id2).getSosPoints() + player1.getPoints());
                }
                // todo: sos points for byes? maybe add player points?
            }
        }

        // sort by points and then by sos points
        swissPlayers.sort((p1, p2) -> {
            int result = p2.getPoints() - p1.getPoints();
            if (result != 0) {
                return result;
            }
            return p2.getSosPoints() - p1.getSosPoints();
        });

        // order could be changed, update ids and mapping
        map.clear();
        for (int i = 0; i < playersCount; i++) {
            swissPlayers.get(i).setId(i);
            map.put(swissPlayers.get(i).getTournamentPlayer(), i);
        }

        // count ties and matches between players
        int[][] duels = new int[playersCount][playersCount];
        int[] byes = new int[playersCount];
        for (Round round : rounds) {
            for (TournamentPairing pairing : round.getPairs()) {
                TournamentPlayer player1 = pairing.getPlayer1();
                TournamentPlayer player2 = pairing.getPlayer2();

                Integer id1 = map.get(player1);
                Integer id2 = map.get(player2);

                if (id1 != null && id2 != null) {
                    duels[id1][id2]++;
                    duels[id2][id1]++;
                }
            }
            for (TournamentPlayer playerBye : round.getPlayerByes()) {
                Integer id = map.get(playerBye);
                if (id != null) {
                    byes[id]++;
                }
            }
        }

        // set vertex count
        // add vertex for bye if we have odd number of players
        n = (playersCount % 2 == 1 ? playersCount + 1 : playersCount);

        // calculate weight
        // try to pair players with equal scores
        w = new int[n][n];
        int pointsDiffMultiplier = 10;
        if (isLastRound) {
            // for the last round, for each unpaired player starting with the first place, pair
            // against the highest ranked player they haven't played against
            for (int i = 0; i < playersCount; i++) {
                for (int j = 0; j < i; j++) {
                    w[i][j] = Math.abs(i - j) +
                            pointsDiffMultiplier * Math.abs(swissPlayers.get(i).getPoints() - swissPlayers.get(j).getPoints());
                    w[j][i] = w[i][j];
                }
            }
        } else {
            for (int i = 0; i < playersCount; i++) {
                PlayerInfo player = swissPlayers.get(i);
                for (int p = player.getPoints(); p >= 0; p--) {
                    int first = -1;
                    int last = -1;
                    for (int j = 0; j < playersCount; j++) {
                        if (swissPlayers.get(j).getPoints() == p) {
                            if (first < 0) {
                                first = j;
                            }
                            last = j;
                        }
                    }
                    if (first < 0) {
                        continue;
                    }
                    int self = (p == player.getPoints() ? i : first - 1);
                    int diff = pointsDiffMultiplier * (player.getPoints() - p);
                    for (int j = Math.max(first, i); j <= last; j++) {
                        w[i][j] = Math.abs(j - (last + first - self)) + diff;
                        w[j][i] = w[i][j];
                    }
                }
            }
        }

        // avoid pairing players that have played each other already
        for (int i = 0; i < playersCount; i++) {
            for (int j = 0; j < i; j++) {
                w[i][j] += duels[i][j] * 500;
                w[j][i] = w[i][j];
            }
        }

        // try to give bye to a player with a low score
        // try to avoid giving the same person multiple byes
        if (n > playersCount) {
            for (int i = 0; i < playersCount; i++) {
                w[i][n - 1] = 10 * (swissPlayers.get(i).getPoints() - swissPlayers.get(playersCount - 1).getPoints()) + (playersCount - i - 1);
                w[i][n - 1] += byes[i] * 2000;
                w[n - 1][i] = w[i][n - 1];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                w[i][j] *= w[i][j];
            }
        }

        // initialize variables for backtrack
        used = new boolean[n];
        pairs = new int[n];
        Arrays.fill(pairs, -1);
        result = new int[n];
        weight = 0;
        minCost = -1;
        makePairings(0);
    }

    public RoundPairings getRoundPairings() {
        // return round pairings with minimal weight
        List<TournamentPairing> pairings = new ArrayList<>();
        List<TournamentPlayer> playerByes = new ArrayList<>();

        Map<Integer, TournamentPlayer> map = new HashMap<>();
        for (PlayerInfo player : swissPlayers) {
            map.put(player.getId(), player.getTournamentPlayer());
        }

        if (n > playersCount) {
            // last vertex -- bye
            playerByes.add(map.get(result[n - 1]));
            result[result[n - 1]] = -1;
            result[n - 1] = -1;
        }

        for (int i = 0; i < playersCount; i++) {
            if (result[i] >= 0) {
                pairings.add(new TournamentPairing(map.get(i), map.get(result[i])));
                result[result[i]] = -1;
                result[i] = -1;
            }
        }

        return new RoundPairings(pairings, playerByes);
    }

    private boolean[] used;

    // current pairs
    private int[] pairs;
    // current weight
    private int weight;

    private int[] result;
    private int minCost;

    // backtrack all possible pairings and choose one with minimal weight
    private void makePairings(int t) {
        if (t >= n) {
            if (minCost < 0 || minCost > weight) {
                minCost = weight;
                System.arraycopy(pairs, 0, result, 0, n);
            }
            return;
        }

        if (!used[t]) {
            for (int i = t + 1; i < n; i++) {
                if (!used[i]) {
                    pairs[t] = i;
                    pairs[i] = t;
                    used[t] = true;
                    used[i] = true;
                    weight += w[t][i];

                    makePairings(t + 1);

                    pairs[t] = -1;
                    pairs[i] = -1;
                    used[t] = false;
                    used[i] = false;
                    weight -= w[t][i];
                }
            }
        } else {
            makePairings(t + 1);
        }
    }

    static class PlayerInfo {
        private int id;

        private TournamentPlayer tournamentPlayer;

        private int points;

        private int sosPoints;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TournamentPlayer getTournamentPlayer() {
            return tournamentPlayer;
        }

        public void setTournamentPlayer(TournamentPlayer tournamentPlayer) {
            this.tournamentPlayer = tournamentPlayer;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getSosPoints() {
            return sosPoints;
        }

        public void setSosPoints(int sosPoints) {
            this.sosPoints = sosPoints;
        }
    }
}

