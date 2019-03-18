
package mage.players;

import java.util.UUID;
import mage.game.Game;
import mage.util.CircularList;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerList extends CircularList<UUID> {

    public PlayerList() {
    }

    public PlayerList(final PlayerList list) {
        super(list);
    }

    public Player getCurrent(Game game) {
        return game.getPlayer(this.get());
    }

    public Player getNextInRange(Player basePlayer, Game game) {
        UUID currentPlayerBefore = get();
        UUID nextPlayerId = super.getNext();
        do {
            if (basePlayer.getInRange().contains(nextPlayerId)) {
                return game.getPlayer(nextPlayerId);
            }
            nextPlayerId = super.getNext();
        } while (!nextPlayerId.equals(currentPlayerBefore));
        return null;
    }

    public Player getNext(Game game) {
        UUID start = this.get();
        if (start == null) {
            return null;
        }
        Player player;
        while (true) {
            player = game.getPlayer(super.getNext());
            if (!player.hasLeft() && !player.hasLost()) {
                break;
            }
            if (!player.hasReachedNextTurnAfterLeaving()) {
                player.setReachedNextTurnAfterLeaving(true);
            }
            if (player.getId().equals(start)) {
                return null;
            }
        }
        return player;
    }

    public Player getPrevious(Game game) {
        Player player;
        UUID start = this.get();
        while (true) {
            player = game.getPlayer(super.getPrevious());
            if (!player.hasLeft() && !player.hasLost()) {
                break;
            }
            if (player.getId().equals(start)) {
                return null;
            }
        }
        return player;
    }

    @Override
    public PlayerList copy() {
        return new PlayerList(this);
    }

}
