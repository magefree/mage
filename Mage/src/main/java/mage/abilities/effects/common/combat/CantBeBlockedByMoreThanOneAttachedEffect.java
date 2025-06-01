package mage.abilities.effects.common.combat;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author LevelX2, edited by Cguy7777
 */
public class CantBeBlockedByMoreThanOneAttachedEffect extends ContinuousEffectImpl {

    protected final int amount;
    protected final AttachmentType attachmentType;

    public CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType attachmentType) {
        this(attachmentType, 1);
    }

    public CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType attachmentType, int amount) {
        this(attachmentType, amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType attachmentType, int amount, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.attachmentType = attachmentType;
        staticText = attachmentType.verb() + " creature can't be blocked by more than " + CardUtil.numberToText(amount) + " creature" + (amount == 1 ? "" : "s");
    }

    private CantBeBlockedByMoreThanOneAttachedEffect(final CantBeBlockedByMoreThanOneAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            permanent.setMaxBlockedBy(amount);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null) {
            Permanent permanent = game.getPermanent(attachment.getAttachedTo());
            return permanent != null ? Collections.singletonList(permanent) : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public CantBeBlockedByMoreThanOneAttachedEffect copy() {
        return new CantBeBlockedByMoreThanOneAttachedEffect(this);
    }
}
