
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
 * @author emerald000 (this is edited from payenergycost)
 * @author chessethewasp 
 */
public class PayResourceCost extends CostImpl {

    private final int amount;

    public PayResourceCost(int amount) {
        this.amount = amount;
        setText();
    }

    public PayResourceCost(PayResourceCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null && player.getCounters().getCount(CounterType.RESOURCE) >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getCounters().getCount(CounterType.RESOURCE) >= amount) {
            player.getCounters().removeCounter(CounterType.RESOURCE, amount);
            paid = true;
        }
        return paid;
    }

    @Override
    public PayResourceCost copy() {
        return new PayResourceCost(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("pay ");
        for (int i = 0; i < amount; i++) {
            sb.append("Resource");
        }
        this.text = sb.toString();
    }
}
