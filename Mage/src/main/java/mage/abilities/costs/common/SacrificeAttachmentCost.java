package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.costs.UseAttachedCost;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class SacrificeAttachmentCost extends UseAttachedCost implements SacrificeCost {

    public SacrificeAttachmentCost() {
        super();
    }

    public SacrificeAttachmentCost(final SacrificeAttachmentCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (mageObjectReference == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return paid;
        }
        for (UUID attachmentId : permanent.getAttachments()) {
            if (!this.mageObjectReference.refersTo(attachmentId, game)) {
                continue;
            }
            Permanent attachment = game.getPermanent(attachmentId);
            paid = attachment != null
                    && attachment.isControlledBy(controllerId)
                    && attachment.sacrifice(source, game);
            if (paid) {
                break;
            }
        }

        return paid;
    }

    @Override
    public SacrificeAttachmentCost copy() {
        return new SacrificeAttachmentCost(this);
    }

    @Override
    public String getText() {
        return "sacrifice " + this.name;
    }
}
