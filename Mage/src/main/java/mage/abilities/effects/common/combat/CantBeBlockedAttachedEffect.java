
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class CantBeBlockedAttachedEffect extends RestrictionEffect {

    public CantBeBlockedAttachedEffect(AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield);
        this.staticText = attachmentType.verb() + " creature can't be blocked";
    }

    public CantBeBlockedAttachedEffect(CantBeBlockedAttachedEffect effect) {
        super(effect);
    }

    @Override
    public CantBeBlockedAttachedEffect copy() {
        return new CantBeBlockedAttachedEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment != null
                && attachment.isAttachedTo(permanent.getId());
    }
}
