package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class PutCountersSourceCost extends CostImpl {

    private final Counter counter;

    public PutCountersSourceCost(Counter counter) {
        this.counter = counter.copy();
        this.text = "put " + counter.getDescription() + " on {this}";
    }

    public PutCountersSourceCost(PutCountersSourceCost cost) {
        super(cost);
        this.counter = cost.counter;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(permanent -> permanent.canHaveCounterAdded(counter, game, source))
                .isPresent();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(counter, controllerId, ability, game, false);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public PutCountersSourceCost copy() {
        return new PutCountersSourceCost(this);
    }
}
