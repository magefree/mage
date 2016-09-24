/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

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
