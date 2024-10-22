package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Grath
 * Costs which extend this class need to have targets chosen, and those targets must be chosen during 601.2b step.
 * 20241022 - This currently is only implemented when paying for Abilities, not Spells.
 */
public abstract class EarlyTargetCost extends CostImpl {

    protected EarlyTargetCost() {
        super();
    }

    protected EarlyTargetCost(final EarlyTargetCost cost) {
        super(cost);
    }

    @Override
    public abstract EarlyTargetCost copy();

    public abstract void chooseTarget(Game game, Ability source, Player controller);
}
