

package mage.game.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Round {

    private final int roundNum;
    private final Tournament tournament;
    private final List<TournamentPairing> pairs = new ArrayList<>();
    private final List<TournamentPlayer> playerByes = new ArrayList<>();

    public Round(int roundNum, Tournament tournament) {
        this.roundNum = roundNum;
        this.tournament = tournament;
    }

    public void addPairing(TournamentPairing match) {
        this.pairs.add(match);
    }

    public TournamentPairing getPairing(UUID pairId) {
        for (TournamentPairing pair: pairs) {
            if (pair.getId().equals(pairId)) {
                return pair;
            }
        }
        return null;
    }

    public List<TournamentPairing> getPairs() {
        return pairs;
    }

    public int getRoundNumber() {
        return this.roundNum;
    }

    public boolean isRoundOver() {
        boolean roundIsOver = true;
        for (TournamentPairing pair: pairs) {
            if (pair.getMatch() != null) {
                if (!pair.getMatch().hasEnded()) {
                    roundIsOver = false;
                } else {
                    if (!pair.isAlreadyPublished()) {
                        tournament.updateResults();
                        pair.setAlreadyPublished(true);
                        if (tournament instanceof TournamentSingleElimination) {
                            pair.eliminatePlayers();
                        }
                        // if it's the last round, finish all players for the tournament if their match is finished
                        if (getRoundNumber() == tournament.getNumberRounds()) {
                            pair.finishPlayersThatPlayedLastRound();
                        }
                    }
                }
            }
        }
        return roundIsOver;
    }

    public List<TournamentPlayer> getPlayerByes() {
        return playerByes;
    }
    
}
