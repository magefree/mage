package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.ConvokeAbility;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.HashSet;

/**
 * @author notgreat
 */
public enum ConvokedSourceCount implements DynamicValue {
    instance;

    ConvokedSourceCount() {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.getSourceCostsTag(game, sourceAbility, ConvokeAbility.convokingCreaturesKey, new HashSet<>(0)).size();
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
