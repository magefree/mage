
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
