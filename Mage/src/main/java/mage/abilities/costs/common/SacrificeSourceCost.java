
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SacrificeSourceCost extends CostImpl {

    public SacrificeSourceCost() {
        this.text = "sacrifice {this}";
    }

    public SacrificeSourceCost(SacrificeSourceCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            paid = permanent.sacrifice(source, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && game.getPlayer(controllerId).canPaySacrificeCost(permanent, source, controllerId, game);
    }

    @Override
    public SacrificeSourceCost copy() {
        return new SacrificeSourceCost(this);
    }
}