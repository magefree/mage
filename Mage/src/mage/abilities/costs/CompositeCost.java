package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Targets;

import java.util.UUID;

public class CompositeCost implements Cost {
    private Cost firstCost;
    private Cost secondCost;
    private String description;

    public CompositeCost(Cost firstCost, Cost secondCost, String description) {
        this.firstCost = firstCost;
        this.secondCost = secondCost;
        this.description = description;
    }

    public CompositeCost(final CompositeCost cost) {
        this.firstCost = cost.firstCost.copy();
        this.secondCost = cost.secondCost.copy();
        this.description = cost.description;
    }

    @Override
    public UUID getId() {
        throw new RuntimeException("Not supported method");
    }

    @Override
    public String getText() {
        return description;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return firstCost.canPay(sourceId, controllerId, game) && secondCost.canPay(sourceId, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        return firstCost.pay(ability, game, sourceId, controllerId, noMana) && secondCost.pay(ability, game, sourceId, controllerId, noMana);
    }

    @Override
    public boolean isPaid() {
        return firstCost.isPaid() && secondCost.isPaid();
    }

    @Override
    public void clearPaid() {
        firstCost.clearPaid();
        secondCost.clearPaid();
    }

    @Override
    public void setPaid() {
        firstCost.setPaid();
        secondCost.setPaid();
    }

    @Override
    public Targets getTargets() {
        Targets result = new Targets();
        result.addAll(firstCost.getTargets());
        result.addAll(secondCost.getTargets());
        return result;
    }

    @Override
    public Cost copy() {
        return new CompositeCost(this);
    }
}
