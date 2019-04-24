
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class TargetPermanentPowerCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getFirstTarget());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getFirstTarget(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null) {
            return sourcePermanent.getPower().getValue();
        }

        return 0;
    }

    @Override
    public TargetPermanentPowerCount copy() {
        return new TargetPermanentPowerCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "its power";
    }
}
