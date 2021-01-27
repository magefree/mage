
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class PutCountersSourceCost extends CostImpl {

    private final int amount;
    private final String name;
    private final Counter counter;

    public PutCountersSourceCost(Counter counter) {
        this.counter = counter.copy();
        this.amount = counter.getCount();
        this.name = counter.getName();
        this.text = new StringBuilder("Put ").append((amount == 1 ? "a" : CardUtil.numberToText(amount)))
                .append(' ').append(name).append(" counter").append((amount != 1 ? "s" : ""))
                .append(" on {this}").toString();

    }

    public PutCountersSourceCost(PutCountersSourceCost cost) {
        super(cost);
        this.counter = cost.counter;
        this.amount = cost.amount;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            this.paid = permanent.addCounters(counter, controllerId, ability, game, false);
        }
        return paid;
    }

    @Override
    public PutCountersSourceCost copy() {
        return new PutCountersSourceCost(this);
    }
}
