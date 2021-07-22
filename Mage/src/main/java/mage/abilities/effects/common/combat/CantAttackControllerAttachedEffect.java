package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;


/**
 * @author LevelX2
 */

public class CantAttackControllerAttachedEffect extends RestrictionEffect {

    public CantAttackControllerAttachedEffect(AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield);
        this.staticText = attachmentType.verb() + " creature can't attack you or planeswalkers you control";
    }

    public CantAttackControllerAttachedEffect(final CantAttackControllerAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }

        if (defenderId.equals(source.getControllerId())) {
            return false;
        }
        Permanent planeswalker = game.getPermanent(defenderId);
        return planeswalker == null || !planeswalker.isControlledBy(source.getControllerId());
    }


    @Override
    public CantAttackControllerAttachedEffect copy() {
        return new CantAttackControllerAttachedEffect(this);
    }

}
