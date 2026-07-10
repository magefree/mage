package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.game.Game;
import mage.players.Player;

/**
 * @author muz
 */
public class DealDamageToControllerSourceCost extends CostImpl {

    private final DynamicValue amount;
    private final String sourceName;

    public DealDamageToControllerSourceCost(int amount) {
        this(StaticValue.get(amount), "{this}");
    }

    public DealDamageToControllerSourceCost(DynamicValue amount) {
        this(amount, "{this}");
    }

    public DealDamageToControllerSourceCost(DynamicValue amount, String sourceName) {
        this.amount = amount.copy();
        this.sourceName = sourceName;
        this.text = "have " + sourceName + " deal " + amount + " damage to you";
    }

    protected DealDamageToControllerSourceCost(final DealDamageToControllerSourceCost cost) {
        super(cost);
        this.amount = cost.amount.copy();
        this.sourceName = cost.sourceName;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getPlayer(controllerId) != null;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        int damageToDeal = amount.calculate(game, ability, null);
        int damageDealt = controller.damage(damageToDeal, source.getSourceId(), source, game, false, true);
        paid = damageToDeal == 0 || damageDealt > 0;
        return paid;
    }

    @Override
    public DealDamageToControllerSourceCost copy() {
        return new DealDamageToControllerSourceCost(this);
    }
}
