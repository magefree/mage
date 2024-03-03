
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public enum TargetPermanentPowerCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent targetPermanent = game.getPermanent(effect.getTargetPointer().getFirst(game, sourceAbility));
        if (targetPermanent == null) {
            targetPermanent = (Permanent) game.getLastKnownInformation(effect.getTargetPointer().getFirst(game, sourceAbility), Zone.BATTLEFIELD);
        }
        if (targetPermanent != null) {
            return targetPermanent.getPower().getValue();
        }

        return 0;
    }

    @Override
    public TargetPermanentPowerCount copy() {
        return TargetPermanentPowerCount.instance;
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
