package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CommanderPlaysCount;
import mage.game.Game;

/**
 * @author JayDi85
 */
public class CommanderAdditionalCost extends DynamicValueGenericManaCost {

    /*
    903.8. A player may cast a commander they own from the command zone. A commander cast from the
    command zone costs an additional {2} for each previous time the player casting it has cast it from
    the command zone that game. This additional cost is informally known as the “commander tax.”
     */

    public CommanderAdditionalCost() {
        super(new CommanderPlaysCount(2), "{2} for each previous time the player casting it has cast it from the command zone");
    }

    public CommanderAdditionalCost(final CommanderAdditionalCost cost) {
        super(cost);
    }

    @Override
    public CommanderAdditionalCost copy() {
        return new CommanderAdditionalCost(this);
    }

    public boolean isEmptyPay(Ability ability, Game game) {
        return amount.calculate(game, ability, null) == 0;
    }
}