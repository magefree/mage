package mage.players;

import mage.game.Game;
import mage.util.CircularList;

import java.util.UUID;

/**
 * Default players order: left (next player seated to the active player's left)
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
        UUID currentPlayerBefore = this.get();
        UUID nextPlayerId = super.getNext();
        do {
            if (basePlayer.getInRange().contains(nextPlayerId)) {
                return game.getPlayer(nextPlayerId);
            }
            nextPlayerId = super.getNext();
        } while (!nextPlayerId.equals(currentPlayerBefore));
        return null;
    }

    /**
     * Find next player. Default order: next player from the left
     *
     * @checkNextTurnReached - use it turns/priority code only to mark leaved player as "reached next turn end" (need for some continous effects)
     */
    public Player getNext(Game game, boolean checkNextTurnReached) {
        UUID start = this.get();
        if (start == null) {
            return null;
        }
        Player player;
        while (true) {
            player = game.getPlayer(super.getNext());
            if (player.isInGame()) {
                break;
            }

            if (checkNextTurnReached) {
                if (!player.hasReachedNextTurnAfterLeaving()) {
                    player.setReachedNextTurnAfterLeaving(true);
                }
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
        if (start == null) {
            return null;
        }
        while (true) {
            player = game.getPlayer(super.getPrevious());
            if (player.isInGame()) {
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