
package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PayLoyaltyCost extends CostImpl {

    private final int amount;

    public PayLoyaltyCost(int amount) {
        this.amount = amount;
        this.text = Integer.toString(amount);
        if (amount > 0) {
            this.text = '+' + this.text;
        }
    }

    public PayLoyaltyCost(PayLoyaltyCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent planeswalker = game.getPermanent(sourceId);
        return planeswalker != null && planeswalker.getCounters(game).getCount(CounterType.LOYALTY) + amount >= 0 && planeswalker.canLoyaltyBeUsed(game);
    }

    /**
     * Gatherer Ruling: 10/1/2005: Planeswalkers will enter the battlefield with
     * double the normal amount of loyalty counters. However, if you activate an
     * ability whose cost has you put loyalty counters on a planeswalker, the
     * number you put on isn't doubled. This is because those counters are put
     * on as a cost, not as an effect.
     *
     */
    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent planeswalker = game.getPermanent(sourceId);
        if (planeswalker != null && planeswalker.getCounters(game).getCount(CounterType.LOYALTY) + amount >= 0 && planeswalker.canLoyaltyBeUsed(game)) {
            if (amount > 0) {
                planeswalker.addCounters(CounterType.LOYALTY.createInstance(amount), ability, game, false);
            } else if (amount < 0) {
                planeswalker.removeCounters(CounterType.LOYALTY.getName(), Math.abs(amount), game);
            }
            planeswalker.addLoyaltyUsed();
            this.paid = true;
        }
        return paid;
    }

    @Override
    public PayLoyaltyCost copy() {
        return new PayLoyaltyCost(this);
    }

}
