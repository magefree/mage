package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantAttackAloneAttachedEffect extends RestrictionEffect {

    public CantAttackAloneAttachedEffect(AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield);
        this.staticText = attachmentType.verb() + " creature can't attack alone";
    }

    public CantAttackAloneAttachedEffect(final CantAttackAloneAttachedEffect effect) {
        super(effect);
    }

    @Override
    public CantAttackAloneAttachedEffect copy() {
        return new CantAttackAloneAttachedEffect(this);
    }

    @Override
    public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game, boolean canUseChooseDialogs) {
        return numberOfAttackers > 1;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment != null && attachment.getAttachedTo() != null
                && permanent.getId().equals(attachment.getAttachedTo());
    }
}
