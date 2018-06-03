

package mage.game.tournament.pairing;

import mage.game.tournament.Round;
import mage.game.tournament.TournamentPairing;
import mage.game.tournament.TournamentPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author Quercitron
 */
public class SwissPairingSimple {

    private final RoundPairings roundPairings;

    public SwissPairingSimple(List<TournamentPlayer> players, List<Round> rounds) {
        roundPairings = getPairingsSimple(players, rounds);
    }

    public RoundPairings getRoundPairings() {
        return roundPairings;
    }

    private RoundPairings getPairingsSimple(List<TournamentPlayer> players, List<Round> rounds) {
        List<TournamentPairing> pairings = new ArrayList<>();
        List<TournamentPlayer> playerByes = new ArrayList<>();

        // sort players by tournament points
        players.sort((p1, p2) -> p2.getPoints() - p1.getPoints());
        // create pairings
        while (!players.isEmpty()) {
            TournamentPlayer player1 = players.get(0);
            players.remove(0);
            TournamentPlayer playerForPossibleSecondPairing = null;
            for (TournamentPlayer player2 : players) {
                if (alreadyPaired(rounds, player1, player2)) {
                    // if already paired but equal points -> remember if second pairing is needed
                    if (playerForPossibleSecondPairing == null) {
                        playerForPossibleSecondPairing = player2;
                    }
                } else {
                    if (player2.getPoints() < player1.getPoints() && playerForPossibleSecondPairing != null) {
                        // pair again with a player
                        pairings.add(new TournamentPairing(player1, playerForPossibleSecondPairing));
                        players.remove(playerForPossibleSecondPairing);
                        player1 = null;
                        break;
                    } else {
                        // pair agains the next not paired before
                        pairings.add(new TournamentPairing(player1, player2));
                        players.remove(player2);
                        player1 = null;
                        break;
                    }
                }
            }
            if (player1 != null) {
                // no pairing done yet
                if (playerForPossibleSecondPairing != null) {
                    // pair again with a player
                    pairings.add(new TournamentPairing(player1, playerForPossibleSecondPairing));
                    players.remove(playerForPossibleSecondPairing);
                } else {
                    // player free round - add to bye players of this round
                    playerByes.add(player1);
                }
            }
        }

        return new RoundPairings(pairings, playerByes);
    }

    private boolean alreadyPaired(List<Round> rounds, TournamentPlayer player1, TournamentPlayer player2) {
        for (Round round : rounds) {
            for (TournamentPairing pairing : round.getPairs()) {
                if (pairing.getPlayer1().equals(player1) || pairing.getPlayer2().equals(player1)) {
                    if (pairing.getPlayer1().equals(player2) || pairing.getPlayer2().equals(player2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
