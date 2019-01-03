package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PayLifeCost extends CostImpl {

    private final DynamicValue amount;

    public PayLifeCost(int amount) {
        this.amount = new StaticValue(amount);
        this.text = "Pay " + Integer.toString(amount) + " life";
    }

    public PayLifeCost(DynamicValue amount, String text) {
        this.amount = amount.copy();
        this.text = "Pay " + text;
    }

    public PayLifeCost(PayLifeCost cost) {
        super(cost);
        this.amount = cost.amount.copy();
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        //118.4. If a cost or effect allows a player to pay an amount of life greater than 0,
        //the player may do so only if their life total is greater than or equal to the
        //amount of the payment. If a player pays life, the payment is subtracted from his or
        //her life total; in other words, the player loses that much life. (Players can always pay 0 life.)
        int lifeToPayAmount = amount.calculate(game, ability, null);
        // Paying 0 life is not considered paying any life.
        if (lifeToPayAmount > 0 && !game.getPlayer(controllerId).canPayLifeCost()) {
            return false;
        }
        return game.getPlayer(controllerId).getLife() >= lifeToPayAmount || lifeToPayAmount == 0;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        int lifeToPayAmount = amount.calculate(game, ability, null);
        this.paid = game.getPlayer(controllerId).loseLife(lifeToPayAmount, game, false) == lifeToPayAmount;
        return paid;
    }

    @Override
    public PayLifeCost copy() {
        return new PayLifeCost(this);
    }

}
