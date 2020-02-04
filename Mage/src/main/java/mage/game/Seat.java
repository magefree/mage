
package mage.game;

import java.io.Serializable;

import mage.players.Player;
import mage.players.PlayerType;

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
