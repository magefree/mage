
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Locale;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class MustBeBlockedByAllAttachedEffect extends RequirementEffect {

    public MustBeBlockedByAllAttachedEffect(AttachmentType attachmentType) {
        this(Duration.WhileOnBattlefield, attachmentType);
    }

    public MustBeBlockedByAllAttachedEffect(Duration duration, AttachmentType attachmentType) {
        super(duration);
        staticText = "All creatures able to block " + attachmentType.verb().toLowerCase(Locale.ENGLISH) + " creature do so";
    }

    protected MustBeBlockedByAllAttachedEffect(final MustBeBlockedByAllAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {
            Permanent attachedCreature = game.getPermanent(attachment.getAttachedTo());
            if (attachedCreature != null && attachedCreature.isAttacking()) {
                return permanent.canBlock(attachment.getAttachedTo(), game);
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {
            return attachment.getAttachedTo();
        }
        return null;
    }

    @Override
    public MustBeBlockedByAllAttachedEffect copy() {
        return new MustBeBlockedByAllAttachedEffect(this);
    }

}
