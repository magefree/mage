package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.MultiAmountType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RemoveCountersSourceCost extends CostImpl {

    private final int amount;
    private final String name;

    public RemoveCountersSourceCost() {
        this.amount = 1;
        this.name = "";
        this.text = "remove a counter from {this}";
    }

    public RemoveCountersSourceCost(int amount) {
        this.amount = amount;
        this.name = "";
        this.text = "remove " + CardUtil.numberToText(amount) + " counters from {this}";
    }

    public RemoveCountersSourceCost(Counter counter) {
        this.amount = counter.getCount();
        this.name = counter.getName();
        this.text = "remove " + counter.getDescription() + " from {this}";
    }

    private RemoveCountersSourceCost(RemoveCountersSourceCost cost) {
        super(cost);
        this.amount = cost.amount;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }

        if (this.name.isEmpty()) {
            // any counter
            return permanent
                    .getCounters(game)
                    .values()
                    .stream()
                    .mapToInt(Counter::getCount)
                    .sum() >= amount;
        } else {
            // specific counter
            return permanent.getCounters(game).getCount(name) >= amount;
        }
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return paid;
        }
        if (name.isEmpty()) {
            List<String> toChoose = new ArrayList<>(permanent.getCounters(game).keySet());
            if (toChoose.isEmpty()) {
                return paid;
            } else {
                List<Integer> counterList = player.getMultiAmount(Outcome.UnboostCreature, toChoose, 0, amount, amount, MultiAmountType.REMOVE_COUNTERS, game);
                for (int i = 0; i < toChoose.size(); i++) {
                    int amountToRemove = counterList.get(i);
                    if (amountToRemove > 0) {
                        permanent.removeCounters(toChoose.get(i), amountToRemove, source, game);
                    }
                }
                paid = true;
            }
        } else if (permanent.getCounters(game).getCount(name) >= amount){
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
