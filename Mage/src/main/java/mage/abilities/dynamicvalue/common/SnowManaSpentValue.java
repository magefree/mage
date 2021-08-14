package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.watchers.common.ManaPaidSourceWatcher;

/**
 * @author TheElk801
 */
public enum SnowManaSpentValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility.getAbilityType() == AbilityType.SPELL) {
            return ManaPaidSourceWatcher.getSnowPaid(sourceAbility.getId(), game);
        }
        return ManaPaidSourceWatcher.getSnowPaid(sourceAbility.getSourceId(), game);
    }

    @Override
    public SnowManaSpentValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "{S} spent to cast this spell";
    }
}
