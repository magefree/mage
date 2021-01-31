package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RemoveCountersSourceCost extends CostImpl {

    private final int amount;
    private final String name;

    public RemoveCountersSourceCost(Counter counter) {
        this.amount = counter.getCount();
        this.name = counter.getName();
        this.text = new StringBuilder("remove ").append((amount == 1 ? CounterType.findArticle(counter.getName()) : CardUtil.numberToText(amount)))
                .append(' ').append(name).append(" counter").append((amount != 1 ? "s" : ""))
                .append(" from {this}").toString();

    }

    public RemoveCountersSourceCost(RemoveCountersSourceCost cost) {
        super(cost);
        this.amount = cost.amount;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(name) >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getCounters(game).getCount(name) >= amount) {
            permanent.removeCounters(name, amount, source, game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public RemoveCountersSourceCost copy() {
        return new RemoveCountersSourceCost(this);
    }
}
