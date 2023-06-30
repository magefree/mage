package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.KickerAbility;
import mage.game.Game;

/**
 * Find permanent/spell kicked stats, can be used in ETB effects.
 *
 * @author LevelX2
 */
public enum MultikickerCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return KickerAbility.getSourceObjectKickedCount(game, sourceAbility);
    }

    @Override
    public MultikickerCount copy() {
        return MultikickerCount.instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "time it was kicked";
    }
}
