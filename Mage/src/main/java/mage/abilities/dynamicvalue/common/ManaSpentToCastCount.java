package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.ManaPaidSourceWatcher;

/**
 * @author LevelX2
 */
public enum ManaSpentToCastCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return ManaPaidSourceWatcher.getTotalPaid(sourceAbility.getSourceId(), game);
    }

    @Override
    public ManaSpentToCastCount copy() {
        return ManaSpentToCastCount.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the amount of mana spent to cast it";
    }
}
