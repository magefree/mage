

package mage.abilities.condition.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */

public enum OpponentHasMoreLifeCondition implements Condition {

    instance;
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID uuid : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(uuid);
                if (opponent != null) {
                    if (opponent.getLife() > controller.getLife()) {
                         return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "an opponent has more life than you";
    }
}
