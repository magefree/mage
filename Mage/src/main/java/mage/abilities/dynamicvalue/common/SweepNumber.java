
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class SweepNumber implements DynamicValue {

    private final String sweepSubtype;

    public SweepNumber(String sweepSubtype) {
        this.sweepSubtype = sweepSubtype;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Integer sweepNumber = (Integer) game.getState().getValue("sweep"
                + sourceAbility.getSourceId() + game.getState().getZoneChangeCounter(sourceAbility.getSourceId()));
        return sweepNumber != null ? sweepNumber : 0;
    }

    @Override
    public SweepNumber copy() {
        return new SweepNumber(sweepSubtype);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of " + sweepSubtype + (sweepSubtype.endsWith("s") ? "" : "s") + " returned this way";
    }
}
