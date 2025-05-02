
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
 * @author BetaSteward_at_googlemail.com
 */
public class TapSourceCost extends CostImpl {

    public TapSourceCost() {
        this.text = "{T}";
    }

    public TapSourceCost(TapSourceCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            paid = permanent.tap(source, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return !permanent.isTapped()
                    && (permanent.canTap(game) || !game.getContinuousEffects().asThough(source.getSourceId(), AsThoughEffectType.ACTIVATE_HASTE, ability, controllerId, game).isEmpty());
        }
        return false;
    }

    @Override
    public TapSourceCost copy() {
        return new TapSourceCost(this);
    }
}
