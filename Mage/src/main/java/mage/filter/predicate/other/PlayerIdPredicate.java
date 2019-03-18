
package mage.filter.predicate.other;

import java.util.UUID;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class PlayerIdPredicate implements Predicate<Player> {

    private final UUID playerId;

    public PlayerIdPredicate(UUID playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean apply(Player input, Game game) {
        return playerId.equals(input.getId());
    }

    @Override
    public String toString() {
        return "PlayerId(" + playerId + ')';
    }
}
