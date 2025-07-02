package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.Target;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class AttachableToRestrictedAbility extends SimpleStaticAbility {
    private final Target attachable;
    public AttachableToRestrictedAbility(Target target) {
        super(Zone.BATTLEFIELD, new InfoEffect("{this} can be attached only to a " + target.getTargetName()));
        this.attachable = target.copy();
    }

    private AttachableToRestrictedAbility(AttachableToRestrictedAbility ability) {
        super(ability);
        this.attachable = ability.attachable; // Since we never modify the target, we don't need to re-copy it
    }

    public boolean canEquip(UUID toEquip, Ability source, Game game) {
        if (source == null) {
            return attachable.canTarget(toEquip, game);
        } else return attachable.canTarget(toEquip, source, game);
    }

    @Override
    public AttachableToRestrictedAbility copy() {
        return new AttachableToRestrictedAbility(this);
    }
}
