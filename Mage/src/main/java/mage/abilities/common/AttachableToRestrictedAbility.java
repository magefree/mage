package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 *
 * @author LevelX2
 */
public class AttachableToRestrictedAbility extends SimpleStaticAbility {

    public AttachableToRestrictedAbility(Target target) {
        super(Zone.BATTLEFIELD, new InfoEffect("{this} can be attached only to a " + target.getTargetName()));
        addTarget(target);
    }

    private AttachableToRestrictedAbility(AttachableToRestrictedAbility ability) {
        super(ability);
    }

    public boolean canEquip(Permanent toEquip, Ability source, Game game) {
        for (Target target : getTargets()) {
            if (source == null) {
                if (!target.canTarget(toEquip.getId(), game)) {
                    return false;
                }
            } else if (!target.canTarget(toEquip.getId(), source, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public AttachableToRestrictedAbility copy() {
        return new AttachableToRestrictedAbility(this);
    }
}
