
package mage.abilities.effects.common.combat;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collections;
import java.util.List;

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
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).setMinBlockedBy(amount);
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {
            Permanent perm = game.getPermanent(attachment.getAttachedTo());
            return perm != null ? Collections.singletonList(perm) : Collections.emptyList();
        }
        return Collections.emptyList();
    }
}