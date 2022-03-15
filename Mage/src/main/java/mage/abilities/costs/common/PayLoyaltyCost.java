package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PayLoyaltyCost extends CostImpl {

    private int amount;

    public PayLoyaltyCost(int amount) {
        setAmount(amount);
    }

    public PayLoyaltyCost(PayLoyaltyCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent planeswalker = game.getPermanent(source.getSourceId());
        if (planeswalker == null) {
            return false;
        }

        int loyaltyCost = amount;

        // apply cost modification
        if (ability instanceof LoyaltyAbility) {
            LoyaltyAbility copiedAbility = ((LoyaltyAbility) ability).copy();
            copiedAbility.adjustCosts(game);
            game.getContinuousEffects().costModification(copiedAbility, game);
            loyaltyCost = 0;
            for (Cost cost : copiedAbility.getCosts()) {
                if (cost instanceof PayLoyaltyCost) {
                    loyaltyCost += ((PayLoyaltyCost) cost).getAmount();
                }
            }
        }

        return planeswalker.getCounters(game).getCount(CounterType.LOYALTY) + loyaltyCost >= 0 && planeswalker.canLoyaltyBeUsed(game);
    }

    /**
     * Gatherer Ruling: 10/1/2005: Planeswalkers will enter the battlefield with
     * double the normal amount of loyalty counters. However, if you activate an
     * ability whose cost has you put loyalty counters on a planeswalker, the
     * number you put on isn't doubled. This is because those counters are put
     * on as a cost, not as an effect.
     */
    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent planeswalker = game.getPermanent(source.getSourceId());
        if (planeswalker != null && planeswalker.getCounters(game).getCount(CounterType.LOYALTY) + amount >= 0 && planeswalker.canLoyaltyBeUsed(game)) {
            if (amount > 0) {
                planeswalker.addCounters(CounterType.LOYALTY.createInstance(amount), source.getControllerId(), ability, game, false);
            } else if (amount < 0) {
                planeswalker.removeCounters(CounterType.LOYALTY.getName(), Math.abs(amount), source, game);
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;

        this.text = Integer.toString(this.amount);
        if (this.amount > 0) {
            this.text = '+' + this.text;
        }
    }
}
