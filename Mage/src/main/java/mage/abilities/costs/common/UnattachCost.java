package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Galatolol
 */
public class UnattachCost extends CostImpl {

    protected UUID sourceEquipmentId;

    public UnattachCost(String name, UUID sourceId) {
        this.text = "Unattach " + name;
        this.sourceEquipmentId = sourceId;
    }

    public UnattachCost(final UnattachCost cost) {
        super(cost);
        this.sourceEquipmentId = cost.sourceEquipmentId;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            for (UUID attachmentId : permanent.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.getId().equals(sourceEquipmentId)) {
                    paid = permanent.removeAttachment(attachmentId, game);
                    if (paid) {
                        break;
                    }
                }
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            for (UUID attachmentId : permanent.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.getId().equals(sourceEquipmentId)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public UnattachCost copy() {
        return new UnattachCost(this);
    }
}
