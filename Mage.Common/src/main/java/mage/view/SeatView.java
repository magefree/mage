/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.view;

import mage.game.Seat;
import mage.players.PlayerType;
import mage.players.net.UserData;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SeatView implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String flagName;
    private UUID playerId;
    private final String playerName;
    private final PlayerType playerType;
    private final String history;
    private final int generalRating;
    private final int constructedRating;
    private final int limitedRating;

    public SeatView(Seat seat) {
        if (seat.getPlayer() != null) {
            this.playerId = seat.getPlayer().getId();
            this.playerName = seat.getPlayer().getName();
            if (seat.getPlayer().getUserData() == null) {
                this.flagName = UserData.getDefaultFlagName();
                this.history = "";
                this.generalRating = 0;
                this.constructedRating = 0;
                this.limitedRating = 0;
            } else {
                this.flagName = seat.getPlayer().getUserData().getFlagName();
                this.history = seat.getPlayer().getUserData().getHistory();
                this.generalRating = seat.getPlayer().getUserData().getGeneralRating();
                this.constructedRating = seat.getPlayer().getUserData().getConstructedRating();
                this.limitedRating = seat.getPlayer().getUserData().getLimitedRating();
            }
        } else {
            // Empty seat
            this.playerName = "";
            this.flagName = "";
            this.history = "";
            this.generalRating = 0;
            this.constructedRating = 0;
            this.limitedRating = 0;
        }
        this.playerType = seat.getPlayerType();
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public String getFlagName() {
        return flagName;
    }

    public String getHistory() {
        return history;
    }

    public int getGeneralRating() {
        return generalRating;
    }

    public int getConstructedRating() {
        return constructedRating;
    }

    public int getLimitedRating() {
        return limitedRating;
    }
}
