
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class CantBeBlockedByOneAttachedEffect extends EvasionEffect {

    protected int amount;
    protected AttachmentType attachmentType;

    public CantBeBlockedByOneAttachedEffect(AttachmentType attachmentType, int amount) {
        this(attachmentType, amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByOneAttachedEffect(AttachmentType attachmentType, int amount, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.attachmentType = attachmentType;
        this.staticCantBeBlockedMessage = "can't be blocked except by "
                + (CardUtil.numberToText(amount))
                + " or more creatures";
        staticText = attachmentType.verb() + " creature "
                + this.staticCantBeBlockedMessage;
    }

    protected CantBeBlockedByOneAttachedEffect(final CantBeBlockedByOneAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public CantBeBlockedByOneAttachedEffect copy() {
        return new CantBeBlockedByOneAttachedEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {
            if (permanent != null && permanent.getId().equals(attachment.getAttachedTo())) {
                permanent.setMinBlockedBy(amount);
                return true;
            }
        }
        return false;
    }
}