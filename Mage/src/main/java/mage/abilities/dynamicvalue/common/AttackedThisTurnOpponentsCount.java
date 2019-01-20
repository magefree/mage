
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

/**
 * @author JayDi85
 */
public enum AttackedThisTurnOpponentsCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PlayersAttackedThisTurnWatcher watcher = game.getState().getWatcher(PlayersAttackedThisTurnWatcher.class);
        if (watcher != null) {
            return watcher.getAttackedOpponentsCount(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public AttackedThisTurnOpponentsCount copy() {
        return AttackedThisTurnOpponentsCount.instance;
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
