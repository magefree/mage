
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class PayEnergyCost extends CostImpl {

    private final int amount;

    public PayEnergyCost(int amount) {
        this.amount = amount;
        setText();
    }

    public PayEnergyCost(PayEnergyCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null && player.getCounters().getCount(CounterType.ENERGY) >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getCounters().getCount(CounterType.ENERGY) >= amount) {
            player.getCounters().removeCounter(CounterType.ENERGY, amount);
            paid = true;
        }
        return paid;
    }

    @Override
    public PayEnergyCost copy() {
        return new PayEnergyCost(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("pay ");
        for (int i = 0; i < amount; i++) {
            sb.append("{E}");
        }
        this.text = sb.toString();
    }
}
