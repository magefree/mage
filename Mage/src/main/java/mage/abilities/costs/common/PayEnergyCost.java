
package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public class PayEnergyCost extends CostImpl {

    private final DynamicValue amount;

    public PayEnergyCost(int amount) {
        this(StaticValue.get(amount), makeText(amount));
    }

    public PayEnergyCost(DynamicValue amount, String text) {
        this.amount = amount;
        this.text = text;
    }

    public PayEnergyCost(PayEnergyCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        int energyToPayAmount = amount.calculate(game, ability, null);
        return player != null && player.getCountersCount(CounterType.ENERGY) >= energyToPayAmount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        int energyToPayAmount = amount.calculate(game, ability, null);
        if (player != null && player.getCountersCount(CounterType.ENERGY) >= energyToPayAmount) {
            player.loseCounters(CounterType.ENERGY.getName(), energyToPayAmount, source, game);
            paid = true;
        }
        return paid;
    }

    @Override
    public PayEnergyCost copy() {
        return new PayEnergyCost(this);
    }

    private static String makeText(int amount) {
        StringBuilder sb = new StringBuilder("pay ");
        if (amount < 6) {
            for (int i = 0; i < amount; i++) {
                sb.append("{E}");
            }
        } else {
            sb.append(CardUtil.numberToText(amount));
            sb.append(" {E}");
        }
        return sb.toString();
    }
}
