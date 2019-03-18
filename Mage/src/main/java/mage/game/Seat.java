
package mage.game;

import mage.players.Player;
import mage.players.PlayerType;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Seat implements Serializable {

//    private static final Logger logger = Logger.getLogger(Seat.class);
    private PlayerType playerType;
    private Player player;

    public Seat(PlayerType playerType) {
        this.playerType = playerType;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
