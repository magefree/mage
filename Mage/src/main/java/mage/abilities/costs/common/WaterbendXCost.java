package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;

import java.util.UUID;

/**
 * TODO: Implement properly
 *
 * @author TheElk801
 */
public class WaterbendXCost extends CostImpl {

    public WaterbendXCost() {
        super();
        this.text = "waterbend {X}";
    }

    private WaterbendXCost(final WaterbendXCost cost) {
        super(cost);
    }

    @Override
    public WaterbendXCost copy() {
        return new WaterbendXCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        return false;
    }
}
