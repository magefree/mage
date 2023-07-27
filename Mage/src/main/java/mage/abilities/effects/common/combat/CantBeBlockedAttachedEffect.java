package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class CantBeBlockedAttachedEffect extends EvasionEffect {

    public CantBeBlockedAttachedEffect(AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield);

        this.staticCantBeBlockedMessage = "can't be blocked";
        this.staticText =
            new StringBuilder(attachmentType.verb())
                .append(" creature ")
                .append(this.staticCantBeBlockedMessage)
                .toString();
    }

    private CantBeBlockedAttachedEffect(final CantBeBlockedAttachedEffect effect) {
        super(effect);
    }

    @Override
    public CantBeBlockedAttachedEffect copy() {
        return new CantBeBlockedAttachedEffect(this);
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAttachments().contains(source.getSourceId());
    }
}
