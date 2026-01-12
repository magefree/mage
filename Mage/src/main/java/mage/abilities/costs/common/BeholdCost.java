package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.BeholdType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class BeholdCost extends CostImpl {

    private final BeholdType beholdType;
    private final int amount;

    public BeholdCost(BeholdType beholdType) {
        this(beholdType, 1);
    }

    public BeholdCost(BeholdType beholdType, int amount) {
        super();
        this.beholdType = beholdType;
        this.amount = amount;
        this.text = "behold " + (
                amount > 1
                        ? CardUtil.numberToText(amount) + ' ' + beholdType.getSubType().getPluralName()
                        : beholdType.getDescription()
        );
    }

    private BeholdCost(final BeholdCost cost) {
        super(cost);
        this.beholdType = cost.beholdType;
        this.amount = cost.amount;
    }

    @Override
    public BeholdCost copy() {
        return new BeholdCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return beholdType.canBehold(controllerId, amount, game, source);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        paid = player != null && beholdType.doBehold(player, amount, game, source).size() == amount;
        return paid;
    }
}
