package mage.abilities.dynamicvalue.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Map;


/**
 * Kicker {X}
 *
 * @author JayDi85
 */
public enum GetKickerXValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        // Currently identical logic to the Manacost X value
        // which should be fine since you can only have one X at a time
        int zcc = CardUtil.getActualSourceObjectZoneChangeCounter(game, sourceAbility);
        MageObjectReference mor = new MageObjectReference(sourceAbility.getSourceId(),zcc,game);
        Map<String, Integer> map = game.getPermanentCostsTags().get(mor);
        if (map != null) {
            return map.getOrDefault("X",0);
        }
        return 0;
    }

    @Override
    public GetKickerXValue copy() {
        return GetKickerXValue.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
