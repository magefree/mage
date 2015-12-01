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

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
        if (sourcePermanent != null) {
            return sourcePermanent.getPower().getValue();
        }
        return 0;
    }

    @Override
    public SourcePermanentPowerCount copy() {
        return new SourcePermanentPowerCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "{source}'s power";
    }
}
