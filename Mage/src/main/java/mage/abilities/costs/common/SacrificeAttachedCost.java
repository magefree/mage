
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J (based on BetaSteward_at_googlemail.com)
 */
public class SacrificeAttachedCost extends CostImpl {

    public SacrificeAttachedCost() {
        this.text = "Sacrifice enchanted creature";
    }

    public SacrificeAttachedCost(SacrificeAttachedCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent attachment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent permanent = game.getPermanent(attachment.getAttachedTo());
        if (permanent != null) {
            paid = permanent.sacrifice(source, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent attachment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent permanent = game.getPermanent(attachment.getAttachedTo());
        return permanent != null && game.getPlayer(controllerId).canPaySacrificeCost(permanent, source, controllerId, game);
    }

    @Override
    public SacrificeAttachedCost copy() {
        return new SacrificeAttachedCost(this);
    }
}
