
package mage.abilities.condition.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

public enum NotMyTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        UUID activePlayerId = game.getActivePlayerId();
        if (activePlayerId != null) {
            return !activePlayerId.equals(source.getControllerId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "if it's not your turn";
    }
}
