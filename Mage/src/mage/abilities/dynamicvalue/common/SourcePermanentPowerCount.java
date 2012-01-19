package mage.abilities.dynamicvalue.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class SourcePermanentPowerCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Constants.Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null)
            return sourcePermanent.getPower().getValue();

        return 0;
    }

    @Override
    public DynamicValue clone() {
        return new SourcePermanentPowerCount();
    }

    @Override
    public String toString() {
        return "X";
    }


    @Override
    public String getMessage() {
        return "point of power that {source} had";
    }
}
