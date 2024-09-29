package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.UseAttachedCost;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class UnattachCost extends UseAttachedCost {

    public UnattachCost() {
        super();
    }

    protected UnattachCost(final UnattachCost cost) {
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
            paid = permanent.removeAttachment(attachmentId, source, game);
            if (paid) {
                break;
            }
        }

        return paid;
    }

    @Override
    public UnattachCost copy() {
        return new UnattachCost(this);
    }

    @Override
    public String getText() {
        return "unattach " + this.name;
    }
}
