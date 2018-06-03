

package mage.game.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.game.match.Match;

/**
 *
 * @author spjspj
 */
public class MultiplayerRound {

    private final int roundNum;
    private final Tournament tournament;
    private final int numSeats;
    private final List<TournamentPlayer> allPlayers = new ArrayList<>();
    private Match match;
    private UUID tableId;


    public MultiplayerRound(int roundNum, Tournament tournament, int numSeats) {
        this.roundNum = roundNum;
        this.tournament = tournament;
        this.numSeats = numSeats;
    }
    
    public List<TournamentPlayer> getAllPlayers () {
        return allPlayers;
    }
    
    public TournamentPlayer getPlayer (int i) {
        if (i >= 0 && i < numSeats && i < allPlayers.size()) {
            return allPlayers.get(i);
        }
        return null;
    }

    public void addPairing(TournamentPairing match) {
        this.allPlayers.add(match.getPlayer1());
        this.allPlayers.add(match.getPlayer2());
    }
    
    public void addPlayer(TournamentPlayer player) {
        this.allPlayers.add(player);        
    }
    
    public int getRoundNumber() {
        return this.roundNum;
    }
    
    public void setMatch (Match match) {
        this.match = match;
    }
    
    public void setTableId (UUID tableId) {
        this.tableId = tableId;
    }

    public boolean isRoundOver() {
        boolean roundIsOver = true;
        if (this.match != null) {
            if (!this.match.hasEnded()) {
                roundIsOver = false;
            }
        }
        return roundIsOver;
    }
}
