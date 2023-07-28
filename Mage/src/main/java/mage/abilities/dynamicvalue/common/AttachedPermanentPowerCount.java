package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */
public enum AttachedPermanentPowerCount implements DynamicValue {
    instance;


    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent attachment = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
        if (attachment == null) {
            return 0;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(attachment.getAttachedTo());
        if (permanent != null && (permanent.getPower().getValue() >= 0)) {
            return permanent.getPower().getValue();
        }
        return 0;
    }

    @Override
    public AttachedPermanentPowerCount copy() {
        return instance;
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
