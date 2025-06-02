
package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.AsThoughEffectType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

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
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            int stunCount = permanent.getCounters(game).getCount(CounterType.STUN);
            paid = permanent.untap(game);
            // 118.11 - if a stun counter replaces the untap, the cost has still been paid.
            // Fear of Sleep Paralysis ruling - if the stun counter can't be removed, the untap cost hasn't been paid.
            if (stunCount > 0) {
                paid = permanent.getCounters(game).getCount(CounterType.STUN) < stunCount;
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.isTapped() && (permanent.canTap(game) || !game.getContinuousEffects().asThough(source.getSourceId(), AsThoughEffectType.ACTIVATE_HASTE, ability, controllerId, game).isEmpty());
        }
        return false;
    }

    @Override
    public UntapSourceCost copy() {
        return new UntapSourceCost(this);
    }
}
