package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PayLifeCost extends CostImpl {

    private final DynamicValue amount;
    private int lifePaid = 0;

    public PayLifeCost(int amount) {
        this(StaticValue.get(amount), Integer.toString(amount) + " life");
    }

    public PayLifeCost(DynamicValue amount, String text) {
        this.amount = amount.copy();
        this.text = "pay " + text;
    }

    public PayLifeCost(PayLifeCost cost) {
        super(cost);
        this.amount = cost.amount.copy();
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        //118.4. If a cost or effect allows a player to pay an amount of life greater than 0,
        //the player may do so only if their life total is greater than or equal to the
        //amount of the payment. If a player pays life, the payment is subtracted from their
        //life total; in other words, the player loses that much life. (Players can always pay 0 life.)
        int lifeToPayAmount = amount.calculate(game, ability, null);
        // Paying 0 life is not considered paying any life.
        if (lifeToPayAmount > 0 && !game.getPlayer(controllerId).canPayLifeCost(ability)) {
            return false;
        }
        return game.getPlayer(controllerId).getLife() >= lifeToPayAmount || lifeToPayAmount == 0;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        int lifeToPayAmount = amount.calculate(game, ability, null);
        this.paid = CardUtil.tryPayLife(lifeToPayAmount, controller, source, game);
        this.lifePaid = lifeToPayAmount;
        return this.paid;
    }

    @Override
    public PayLifeCost copy() {
        return new PayLifeCost(this);
    }

    @Override
    public void clearPaid() {
        super.clearPaid();
        lifePaid = 0;
    }

    public int getLifePaid() {
        return lifePaid;
    }
}
