package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;

import java.util.UUID;

public abstract class CostImpl implements Cost {

    protected UUID id;
    protected String text;
    protected boolean paid;
    protected Targets targets;

    public CostImpl() {
        id = UUID.randomUUID();
        paid = false;
        targets = new Targets();
    }

    public CostImpl(final CostImpl cost) {
        this.id = cost.id;
        this.text = cost.text;
        this.paid = cost.paid;
        this.targets = cost.targets.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana) {
        return pay(ability, game, source, controllerId, noMana, this);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Cost setText(String text) {
        this.text = text;
        return this;
    }

    public void addTarget(Target target) {
        if (target != null) {
            this.targets.add(target);
        }
    }

    @Override
    public Targets getTargets() {
        return this.targets;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public void clearPaid() {
        paid = false;
        targets.clearChosen();
    }

    @Override
    public void setPaid() {
        paid = true;
    }

    @Override
    public UUID getId() {
        return this.id;
    }
}
