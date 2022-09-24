
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public enum SweepNumber implements DynamicValue {
    // FOREST("Forest");
    // ISLAND("Island");
    PLAINS("Plains"),
    MOUNTAIN("Mountain"),
    SWAMP("Swamp");

    private final String message;

    SweepNumber(String landType) {
        this.message = landType + " returned this way";
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Integer sweepNumber = (Integer) game.getState().getValue("sweep"
                + sourceAbility.getSourceId() + game.getState().getZoneChangeCounter(sourceAbility.getSourceId()));
        return sweepNumber != null ? sweepNumber : 0;
    }

    @Override
    public SweepNumber copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
