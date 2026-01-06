package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.BeholdType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class BeholdCost extends CostImpl {

    private final BeholdType beholdType;

    public BeholdCost(BeholdType beholdType) {
        super();
        this.beholdType = beholdType;
        this.text = "behold " + beholdType.getDescription();
    }

    private BeholdCost(final BeholdCost cost) {
        super(cost);
        this.beholdType = cost.beholdType;
    }

    @Override
    public BeholdCost copy() {
        return new BeholdCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return beholdType.canBehold(controllerId, game, source);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        paid = player != null && beholdType.doBehold(player, game, source) != null;
        return paid;
    }
}
