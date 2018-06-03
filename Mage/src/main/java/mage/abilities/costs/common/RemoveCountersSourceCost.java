

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
 * @author BetaSteward_at_googlemail.com
 */
public class RemoveCountersSourceCost extends CostImpl {

    private final int amount;
    private final String name;

    public RemoveCountersSourceCost(Counter counter) {
        this.amount = counter.getCount();
        this.name = counter.getName();
        this.text = new StringBuilder("Remove ").append((amount == 1 ? "a" : CardUtil.numberToText(amount)))
                .append(' ').append(name).append(" counter").append((amount != 1 ? "s" : ""))
                .append(" from {this}").toString();

    }

    public RemoveCountersSourceCost(RemoveCountersSourceCost cost) {
        super(cost);
        this.amount = cost.amount;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        return permanent != null && permanent.getCounters(game).getCount(name) >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null && permanent.getCounters(game).getCount(name) >= amount) {
            permanent.removeCounters(name, amount, game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public RemoveCountersSourceCost copy() {
        return new RemoveCountersSourceCost(this);
    }
}
