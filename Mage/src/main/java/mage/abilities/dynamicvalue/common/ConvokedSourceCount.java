package mage.abilities.dynamicvalue.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.ConvokeAbility;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.HashSet;

/**
 * @author TheElk801
 */
public enum ConvokedSourceCount implements DynamicValue {
    instance;

    @Override
    @SuppressWarnings("unchecked")
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        HashSet<MageObjectReference> convokingCreatures =
                (HashSet<MageObjectReference>) CardUtil.getSourceCostTags(game, sourceAbility)
                .getOrDefault(ConvokeAbility.convokingCreaturesKey,new HashSet<>());
        return convokingCreatures.size();
    }

    @Override
    public ConvokedSourceCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creature that convoked it";
    }
}
