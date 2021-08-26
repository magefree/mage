package org.mage.test.serverside.tournament;

import mage.game.tournament.Round;
import mage.game.tournament.TournamentPairing;
import mage.game.tournament.TournamentPlayer;
import mage.game.tournament.pairing.RoundPairings;
import mage.game.tournament.pairing.SwissPairingMinimalWeightMatching;
import mage.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.stub.PlayerStub;
import org.mage.test.stub.TournamentStub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Quercitron
 */
public class SwissPairingMinimalWeightMatchingTest {

    @Test
    public void FourPlayersSecondRoundTest() {
        // 1 > 3
        // 2 > 4

        TournamentPlayer player1 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player2 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player3 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player4 = new TournamentPlayer(new PlayerStub(), null);
        List<TournamentPlayer> players = new ArrayList<>();
        players.add(player4);
        players.add(player2);
        players.add(player3);
        players.add(player1);

        player1.setPoints(3);
        player2.setPoints(3);
        player3.setPoints(0);
        player4.setPoints(0);

        List<Round> rounds = new ArrayList<>();
        Round round = new Round(1, new TournamentStub());
        TournamentPairing pair1 = new TournamentPairing(player1, player3);
        round.addPairing(pair1);
        TournamentPairing pair2 = new TournamentPairing(player4, player2);
        round.addPairing(pair2);
        rounds.add(round);

        SwissPairingMinimalWeightMatching swissPairing = new SwissPairingMinimalWeightMatching(players, rounds, false);
        RoundPairings roundPairings = swissPairing.getRoundPairings();

        Assert.assertEquals(2, roundPairings.getPairings().size());
        Assert.assertEquals(0, roundPairings.getPlayerByes().size());

        CheckPair(roundPairings.getPairings(), player1, player2);
        CheckPair(roundPairings.getPairings(), player3, player4);
        Assert.assertEquals(0, roundPairings.getPlayerByes().size());
    }

    @Test
    public void FourPlayersSecondThirdTest() {
        // 1 > 3
        // 2 > 4
        //
        // 1 > 2
        // 3 > 4

        TournamentPlayer player3 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player2 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player4 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player1 = new TournamentPlayer(new PlayerStub(), null);
        List<TournamentPlayer> players = new ArrayList<>();
        players.add(player4);
        players.add(player2);
        players.add(player3);
        players.add(player1);

        player1.setPoints(6);
        player2.setPoints(3);
        player3.setPoints(3);
        player4.setPoints(0);

        List<Round> rounds = new ArrayList<>();
        // round 1
        Round round = new Round(1, new TournamentStub());
        TournamentPairing pair1 = new TournamentPairing(player1, player3);
        round.addPairing(pair1);
        TournamentPairing pair2 = new TournamentPairing(player4, player2);
        round.addPairing(pair2);
        rounds.add(round);
        // round 2
        round = new Round(2, new TournamentStub());
        pair1 = new TournamentPairing(player2, player1);
        round.addPairing(pair1);
        pair2 = new TournamentPairing(player4, player3);
        round.addPairing(pair2);
        rounds.add(round);

        SwissPairingMinimalWeightMatching swissPairing = new SwissPairingMinimalWeightMatching(players, rounds, true);
        RoundPairings roundPairings = swissPairing.getRoundPairings();

        Assert.assertEquals(2, roundPairings.getPairings().size());
        Assert.assertEquals(0, roundPairings.getPlayerByes().size());

        CheckPair(roundPairings.getPairings(), player1, player4);
        CheckPair(roundPairings.getPairings(), player2, player3);
        Assert.assertEquals(0, roundPairings.getPlayerByes().size());
    }

    @Test
    public void PlayerLeftTournamentAfterFirstRound() {
        // 1 > 3
        // 2 > 4
        // 4 left the tournament

        TournamentPlayer player1 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player2 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player3 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player4 = new TournamentPlayer(new PlayerStub(), null);
        List<TournamentPlayer> players = new ArrayList<>();
        //players.add(player4); -- player 4 is not active
        players.add(player2);
        players.add(player3);
        players.add(player1);

        player1.setPoints(3);
        player2.setPoints(3);
        player3.setPoints(0);
        player4.setPoints(0);

        List<Round> rounds = new ArrayList<>();
        Round round = new Round(1, new TournamentStub());
        TournamentPairing pair1 = new TournamentPairing(player1, player3);
        round.addPairing(pair1);
        TournamentPairing pair2 = new TournamentPairing(player4, player2);
        round.addPairing(pair2);
        rounds.add(round);

        SwissPairingMinimalWeightMatching swissPairing = new SwissPairingMinimalWeightMatching(players, rounds, false);
        RoundPairings roundPairings = swissPairing.getRoundPairings();

        Assert.assertEquals(1, roundPairings.getPairings().size());
        Assert.assertEquals(1, roundPairings.getPlayerByes().size());

        CheckPair(roundPairings.getPairings(), player1, player2);
        Assert.assertTrue(roundPairings.getPlayerByes().contains(player3));
    }

    @Test
    public void FivePlayersThirdRoundTest() {
        // 1 > 2
        // 3 > 4
        // 5
        //
        // 1 > 5
        // 2 > 3
        // 4

        TournamentPlayer player1 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player2 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player3 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player4 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player5 = new TournamentPlayer(new PlayerStub(), null);
        List<TournamentPlayer> players = new ArrayList<>();
        players.add(player4);
        players.add(player2);
        players.add(player5);
        players.add(player3);
        players.add(player1);

        player1.setPoints(6);
        player2.setPoints(3);
        player3.setPoints(3);
        player4.setPoints(3);
        player5.setPoints(3);

        List<Round> rounds = new ArrayList<>();
        // first round
        Round round = new Round(1, new TournamentStub());
        TournamentPairing pair1 = new TournamentPairing(player1, player2);
        round.addPairing(pair1);
        TournamentPairing pair2 = new TournamentPairing(player3, player4);
        round.addPairing(pair2);
        round.getPlayerByes().add(player5);
        rounds.add(round);
        // second round
        round = new Round(1, new TournamentStub());
        pair1 = new TournamentPairing(player1, player5);
        round.addPairing(pair1);
        pair2 = new TournamentPairing(player2, player3);
        round.addPairing(pair2);
        round.getPlayerByes().add(player4);
        rounds.add(round);

        SwissPairingMinimalWeightMatching swissPairing = new SwissPairingMinimalWeightMatching(players, rounds, false);
        RoundPairings roundPairings = swissPairing.getRoundPairings();

        Assert.assertEquals(2, roundPairings.getPairings().size());
        Assert.assertEquals(1, roundPairings.getPlayerByes().size());

        CheckPair(roundPairings.getPairings(), player1, player4);
        CheckPair(roundPairings.getPairings(), player2, player5);
        Assert.assertTrue(roundPairings.getPlayerByes().contains(player3));
    }

    @Test
    public void PlayerWithByeLeftTournament() {
        // 1 > 2
        // 3 > 4
        // 5
        // 5 left the tournament


        TournamentPlayer player1 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player2 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player3 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player4 = new TournamentPlayer(new PlayerStub(), null);
        TournamentPlayer player5 = new TournamentPlayer(new PlayerStub(), null);
        List<TournamentPlayer> players = new ArrayList<>();
        //players.add(player5); -- player 5 is not active
        players.add(player4);
        players.add(player2);
        players.add(player3);
        players.add(player1);

        player1.setPoints(3);
        player2.setPoints(0);
        player3.setPoints(3);
        player4.setPoints(0);
        player5.setPoints(3);

        List<Round> rounds = new ArrayList<>();
        // first round
        Round round = new Round(1, new TournamentStub());
        TournamentPairing pair1 = new TournamentPairing(player1, player2);
        round.addPairing(pair1);
        TournamentPairing pair2 = new TournamentPairing(player3, player4);
        round.addPairing(pair2);
        round.getPlayerByes().add(player5);
        rounds.add(round);

        SwissPairingMinimalWeightMatching swissPairing = new SwissPairingMinimalWeightMatching(players, rounds, false);
        RoundPairings roundPairings = swissPairing.getRoundPairings();

        Assert.assertEquals(2, roundPairings.getPairings().size());
        Assert.assertEquals(0, roundPairings.getPlayerByes().size());

        CheckPair(roundPairings.getPairings(), player1, player3);
        CheckPair(roundPairings.getPairings(), player2, player4);
        Assert.assertEquals(0, roundPairings.getPlayerByes().size());
    }

    @Test
    public void SimulateDifferentTournaments() {
        int playersCount = 12;
        for (int i = 0; i < playersCount; i++) {
            int roundsCount = ((i + 1) / 2) * 2 - 1;
            for (int j = 1; j <= roundsCount; j++) {
                SimulateTournament(i, j);
            }
        }
    }

    private void SimulateTournament(int playersCount, int roundsCount) {

        List<TournamentPlayer> players = new ArrayList<>();
        for (int i = 0; i < playersCount; i++) {
            players.add(new TournamentPlayer(new PlayerStub(), null));
        }

        List<TournamentPairing> playedPairs = new ArrayList<>();
        Set<TournamentPlayer> playersByes = new HashSet<>();

        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < roundsCount; i++) {
            SwissPairingMinimalWeightMatching swissPairing =
                    new SwissPairingMinimalWeightMatching(new ArrayList<>(players), rounds, i + 1 == roundsCount);
            RoundPairings roundPairings = swissPairing.getRoundPairings();

            Assert.assertEquals(playersCount / 2, roundPairings.getPairings().size());
            Assert.assertEquals(playersCount % 2, roundPairings.getPlayerByes().size());

            Round round = new Round(1, new TournamentStub());
            rounds.add(round);
            for (TournamentPairing pairing : roundPairings.getPairings()) {
                if (ContainsPair(playedPairs, pairing.getPlayer1(), pairing.getPlayer2())) {
                    if (i < (playersCount + 1) / 2) {
                        throw new AssertionError("Match between players has been played already.");
                    }
                }
                playedPairs.add(pairing);

                round.addPairing(pairing);
                if (RandomUtil.nextBoolean()) {
                    pairing.getPlayer1().setPoints(pairing.getPlayer1().getPoints() + 3);
                } else {
                    pairing.getPlayer2().setPoints(pairing.getPlayer2().getPoints() + 3);
                }
            }
            for (TournamentPlayer playerBye : roundPairings.getPlayerByes()) {
                if (playersByes.contains(playerBye)) {
                    throw new AssertionError("Player already had bye.");
                }
                playersByes.add(playerBye);

                round.getPlayerByes().add(playerBye);
                playerBye.setPoints(playerBye.getPoints() + 3);
            }
        }
    }

    private void CheckPair(List<TournamentPairing> pairs, TournamentPlayer player1, TournamentPlayer player2) {
        if (!ContainsPair(pairs, player1, player2)) {
            throw new AssertionError("Pairing doesn't contain expected pair of players.");
        }
    }

    private boolean ContainsPair(List<TournamentPairing> pairs, TournamentPlayer player1, TournamentPlayer player2) {
        for (TournamentPairing pair : pairs) {
            if (pair.getPlayer1().equals(player1) && pair.getPlayer2().equals(player2)) {
                return true;
            }
            if (pair.getPlayer1().equals(player2) && pair.getPlayer2().equals(player1)) {
                return true;
            }
        }
        return false;
    }
}


