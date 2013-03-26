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

package mage.view;

import java.io.Serializable;
import java.util.UUID;
import mage.game.Game;
import mage.game.tournament.TournamentPairing;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */

public class TournamentGameView implements Serializable {
    private static final long serialVersionUID = 1L;

    private int roundNum;
    private UUID matchId;
    private UUID gameId;
    private String state;
    private String result;
    private String players;

    TournamentGameView(int roundNum, TournamentPairing pair, Game game) {
        this.roundNum = roundNum;
        this.matchId = pair.getMatch().getId();
        this.gameId = game.getId();
        this.players = pair.getPlayer1().getPlayer().getName() + " - " + pair.getPlayer2().getPlayer().getName();
        if (game.isGameOver()) {
            this.state = "Finished";
            this.result = game.getWinner();
        } 
        else {
            this.state = "Dueling";
            this.result = "";
        }
    }

    public int getRoundNum() {
        return roundNum;
    }

    public UUID getMatchId() {
        return this.matchId;
    }

    public UUID getGameId() {
        return this.gameId;
    }

    public String getState() {
        return this.state;
    }

    public String getResult() {
        return this.result;
    }

    public String getPlayers() {
        return this.players;
    }
}
