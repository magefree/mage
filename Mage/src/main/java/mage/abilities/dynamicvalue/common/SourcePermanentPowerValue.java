package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */
public enum SourcePermanentPowerValue implements DynamicValue {
    ALLOW_NEGATIVE(true), // 107.1b, only for setting power/toughness/life to a specific value or doubling
    NOT_NEGATIVE(false); // all other usages

    private final boolean allowNegativeValues;

    SourcePermanentPowerValue(boolean allowNegativeValues) {
        this.allowNegativeValues = allowNegativeValues;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return 0;
        }
        int value = sourcePermanent.getPower().getValue();
        if (allowNegativeValues || value > 0) {
            return value;
        }
        return 0;
    }

    @Override
    public SourcePermanentPowerValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "{this}'s power";
    }
}
