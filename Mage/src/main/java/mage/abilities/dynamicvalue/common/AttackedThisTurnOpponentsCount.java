
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class AttackedThisTurnOpponentsCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return this.calculate(game, sourceAbility.getControllerId());
    }

    public int calculate(Game game, UUID controllerId) {
        PlayersAttackedThisTurnWatcher watcher = game.getState().getWatcher(PlayersAttackedThisTurnWatcher.class);
        if (watcher != null) {
            return watcher.getAttackedOpponentsCount(controllerId);
        }
        return 0;
    }

    @Override
    public AttackedThisTurnOpponentsCount copy() {
        return new AttackedThisTurnOpponentsCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the number of opponents you attacked this turn";
    }
}
