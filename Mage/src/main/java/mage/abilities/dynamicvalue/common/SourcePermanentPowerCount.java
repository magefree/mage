package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class SourcePermanentPowerCount implements DynamicValue {

    private final boolean allowNegativeValues;

    public SourcePermanentPowerCount() {
        this(true);
    }

    public SourcePermanentPowerCount(boolean allowNegativeValues) {
        super();
        this.allowNegativeValues = allowNegativeValues;
    }

    public SourcePermanentPowerCount(final SourcePermanentPowerCount dynamicValue) {
        super();
        this.allowNegativeValues = dynamicValue.allowNegativeValues;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return 0;
        }
        int power = sourcePermanent.getPower().getValue();
        return allowNegativeValues ? power : Integer.max(power, 0);
    }

    @Override
    public SourcePermanentPowerCount copy() {
        return new SourcePermanentPowerCount(this);
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
