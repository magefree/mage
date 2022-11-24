
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.SquadAbility;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Susucre
 */
public enum SquadDynamicValue implements DynamicValue {
    instance;

    private static final ValueHint hint = new ValueHint("Time(s) squad was paid", instance);

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        return SquadAbility.getSourceObjectSquadCount(game, source);
    }

    @Override
    public SquadDynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {

        return "time(s) squad was paid";
    }

    public static ValueHint getHint() {
        return hint;
    }
}
