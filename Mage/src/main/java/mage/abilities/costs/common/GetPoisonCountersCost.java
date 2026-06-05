package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public class GetPoisonCountersCost extends CostImpl {

    private final int amount;

    public GetPoisonCountersCost(int amount) {
        this.amount = amount;
        this.text = "get " + CardUtil.numberToText(amount) + " poison counter" + (amount == 1 ? "" : "s");
    }

    protected GetPoisonCountersCost(final GetPoisonCountersCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getPlayer(controllerId) != null;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        paid = player != null && player.addCounters(CounterType.POISON.createInstance(amount), controllerId, source, game);
        return paid;
    }

    @Override
    public GetPoisonCountersCost copy() {
        return new GetPoisonCountersCost(this);
    }
}
