

package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */

public class AttacksIfAbleAttachedEffect extends RequirementEffect {

    public AttacksIfAbleAttachedEffect(Duration duration, AttachmentType attachmentType) {
        super(duration);
        this.staticText = attachmentType.verb() + " creature attacks each combat if able";
    }

    public AttacksIfAbleAttachedEffect(final AttacksIfAbleAttachedEffect effect) {
        super(effect);
    }

    @Override
    public AttacksIfAbleAttachedEffect copy() {
        return new AttacksIfAbleAttachedEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment != null && attachment.getAttachedTo() != null
                && permanent.getId().equals(attachment.getAttachedTo());
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public boolean mustBlockAny(Game game) {
        return false;
    }
}
