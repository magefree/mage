
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.AsThoughEffectType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class UntapSourceCost extends CostImpl {

    public UntapSourceCost() {
        this.text = "{Q}";
    }

    public UntapSourceCost(UntapSourceCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            paid = permanent.untap(game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            return permanent.isTapped() && (permanent.canTap() || null != game.getContinuousEffects().asThough(sourceId, AsThoughEffectType.ACTIVATE_HASTE, ability, controllerId, game));
        }
        return false;
    }

    @Override
    public UntapSourceCost copy() {
        return new UntapSourceCost(this);
    }
}
