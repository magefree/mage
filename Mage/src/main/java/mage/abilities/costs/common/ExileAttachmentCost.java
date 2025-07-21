package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.costs.UseAttachedCost;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class ExileAttachmentCost extends UseAttachedCost implements SacrificeCost {

    public ExileAttachmentCost() {
        super();
    }

    protected ExileAttachmentCost(final ExileAttachmentCost cost) {
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
                    && Optional
                    .ofNullable(controllerId)
                    .map(game::getPlayer)
                    .filter(player -> player.moveCards(attachment, Zone.EXILED, source, game))
                    .isPresent();
            if (paid) {
                break;
            }
        }

        return paid;
    }

    @Override
    public ExileAttachmentCost copy() {
        return new ExileAttachmentCost(this);
    }

    @Override
    public String getText() {
        return "exile " + this.name;
    }
}
