
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class BlocksIfAbleAttachedEffect extends RequirementEffect {

    public BlocksIfAbleAttachedEffect(Duration duration, AttachmentType attachmentType) {
        super(duration);
        this.staticText = attachmentType.verb() + " creature blocks each combat if able";
    }

    public BlocksIfAbleAttachedEffect(final BlocksIfAbleAttachedEffect effect) {
        super(effect);
    }

    @Override
    public BlocksIfAbleAttachedEffect copy() {
        return new BlocksIfAbleAttachedEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public boolean mustBlockAny(Game game) {
        return true;
    }
}
