package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Targets;

import java.util.Optional;
import java.util.UUID;

public class CompositeCost implements Cost {

    private final Cost firstCost;
    private final Cost secondCost;
    private String description;

    public CompositeCost(Cost firstCost, Cost secondCost, String description) {
        this.firstCost = firstCost;
        this.secondCost = secondCost;
        this.description = description;
    }

    protected CompositeCost(final CompositeCost cost) {
        this.firstCost = cost.firstCost.copy();
        this.secondCost = cost.secondCost.copy();
        this.description = cost.description;
    }

    @Override
    public UUID getId() {
        throw new RuntimeException("Not supported method");
    }

    @Override
    public CompositeCost setText(String text) {
        this.description = text;
        return this;
    }

    @Override
    public String getText() {
        return description;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return firstCost.canPay(ability, source, controllerId, game) && secondCost.canPay(ability, source, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana) {
        return pay(ability, game, source, controllerId, noMana, this);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        return firstCost.pay(ability, game, source, controllerId, noMana, costToPay)
                && secondCost.pay(ability, game, source, controllerId, noMana, costToPay);
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
        Targets res = new Targets();
        res.addAll(firstCost.getTargets());
        res.addAll(secondCost.getTargets());
        return res.withReadOnly();
    }

    @Override
    public CompositeCost copy() {
        return new CompositeCost(this);
    }
}
