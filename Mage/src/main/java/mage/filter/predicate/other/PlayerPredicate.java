
package mage.filter.predicate.other;

import java.util.UUID;
import mage.constants.TargetController;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class PlayerPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    private final TargetController targetPlayer;

    public PlayerPredicate(TargetController player) {
        this.targetPlayer = player;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player player = input.getObject();
        UUID playerId = input.getPlayerId();
        if (player == null || playerId == null) {
            return false;
        }

        switch (targetPlayer) {
            case YOU:
                if (player.getId().equals(playerId)) {
                    return true;
                }
                break;
            case OPPONENT:
                if (!player.getId().equals(playerId) &&
                        game.getPlayer(playerId).hasOpponent(player.getId(), game)) {
                    return true;
                }
                break;
            case NOT_YOU:
                if (!player.getId().equals(playerId)) {
                    return true;
                }
                break;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Player(" + targetPlayer + ')';
    }
}
