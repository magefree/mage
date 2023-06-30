package mage.abilities.dynamicvalue.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.ConvokeWatcher;

/**
 * @author TheElk801
 */
public enum ConvokedSourceCount implements DynamicValue {
    PERMANENT(-1),
    SPELL(0);
    private final int offset;

    ConvokedSourceCount(int offset) {
        this.offset = offset;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return ConvokeWatcher.getConvokingCreatures(new MageObjectReference(game.getObject(sourceAbility), game, offset), game).size();
    }

    @Override
    public ConvokedSourceCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creature that convoked it";
    }

    @Override
    public String toString() {
        return "1";
    }
}
