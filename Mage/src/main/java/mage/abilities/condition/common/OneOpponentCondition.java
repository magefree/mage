
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public enum OneOpponentCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int opponentCount = 0;
        if (controller != null) {
            for (UUID uuid : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(uuid);
                if (opponent != null) {
                    opponentCount++;
                    if (opponentCount > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "you have one opponent";
    }
}
