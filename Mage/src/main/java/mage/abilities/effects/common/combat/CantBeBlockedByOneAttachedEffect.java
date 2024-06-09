
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantBeBlockedByOneAttachedEffect extends ContinuousEffectImpl {

    protected int amount;
    protected AttachmentType attachmentType;

    public CantBeBlockedByOneAttachedEffect(AttachmentType attachmentType, int amount) {
        this(attachmentType, amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByOneAttachedEffect(AttachmentType attachmentType, int amount, Duration duration) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        this.attachmentType = attachmentType;
        staticText = attachmentType.verb() + " creature can't be blocked except by " + amount + " or more creatures";
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
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case RulesEffects:
                Permanent attachment = game.getPermanent(source.getSourceId());
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent perm = game.getPermanent(attachment.getAttachedTo());
                    if (perm != null) {
                        perm.setMinBlockedBy(amount);
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}