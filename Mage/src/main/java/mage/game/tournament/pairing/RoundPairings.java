

package mage.game.tournament.pairing;

import mage.game.tournament.TournamentPairing;
import mage.game.tournament.TournamentPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quercitron
 */
public class RoundPairings {

    public RoundPairings() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public RoundPairings(List<TournamentPairing> pairings, List<TournamentPlayer> playerByes) {
        this.pairings = pairings;
        this.playerByes = playerByes;
    }

    private final List<TournamentPairing> pairings;

    private final List<TournamentPlayer> playerByes;

    public List<TournamentPairing> getPairings() {
        return pairings;
    }

    public List<TournamentPlayer> getPlayerByes() {
        return playerByes;
    }

}
