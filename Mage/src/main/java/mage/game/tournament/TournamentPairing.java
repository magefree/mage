

package mage.game.tournament;

import java.util.UUID;
import mage.constants.TournamentPlayerState;
import mage.game.match.Match;
import mage.game.match.MatchPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentPairing {

    private final UUID id = UUID.randomUUID();
    private UUID tableId;
    private Match match;
    private final TournamentPlayer player1;
    private final TournamentPlayer player2;
    private boolean alreadyPublished;
    
    public TournamentPairing(TournamentPlayer player1, TournamentPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.alreadyPublished = false;
    }

    public UUID getId() {
        return id;
    }

    public TournamentPlayer getPlayer1() {
        return this.player1;
    }

    public TournamentPlayer getPlayer2() {
        return this.player2;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * Called by eliminate tournaments after each match
     */
    public void eliminatePlayers() {
        if (match != null && match.hasEnded()) {
            MatchPlayer mPlayer1 = match.getPlayer(player1.getPlayer().getId());
            MatchPlayer mPlayer2 = match.getPlayer(player2.getPlayer().getId());
            if (mPlayer1.hasQuit() || !mPlayer1.isMatchWinner()) {
                player1.setEliminated();
            }
            if (mPlayer2.hasQuit() || !mPlayer2.isMatchWinner()) {
                player2.setEliminated();
            }
        }
    }
    public void finishPlayersThatPlayedLastRound() {
        if (match != null && match.hasEnded()) {
            if (!player1.isEliminated()) {
                player1.setEliminated();
                player1.setState(TournamentPlayerState.FINISHED);
            }
            if (!player2.isEliminated()) {
                player2.setEliminated();
                player2.setState(TournamentPlayerState.FINISHED);
            }            
        }        
    }

    public void eliminateComputer() {
        if (!player1.getPlayer().isHuman()) {
            player1.setEliminated();
            return;
        }
        if (!player2.getPlayer().isHuman()) {
            player2.setEliminated();
        }
    }

    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    public boolean isAlreadyPublished() {
        return alreadyPublished;
    }

    public void setAlreadyPublished(boolean alreadyPublished) {
        this.alreadyPublished = alreadyPublished;
    }

}
