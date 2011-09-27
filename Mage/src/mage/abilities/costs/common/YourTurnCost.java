package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.game.Game;

import java.util.UUID;

/**
 * "Activate this ability only during your turn" cost
 **/
public class YourTurnCost extends CostImpl<YourTurnCost> {
    public YourTurnCost() {
        this.text = "Activate this ability only during your turn";
    }

    public YourTurnCost(final YourTurnCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return game.getActivePlayerId().equals(controllerId);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        return true;
    }

    @Override
    public YourTurnCost copy() {
        return new YourTurnCost(this);
    }
}
